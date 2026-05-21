package emergent_lld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

public class ModelArbiter {

    // Sorted by priority (lowest int = highest priority)
    private final PriorityQueue<IModel> models;

    private static final double CANARY_TRAFFIC_PERCENT = 0.05;

    public ModelArbiter() {
        this.models = new PriorityQueue<>((a, b) -> a.getPriority() - b.getPriority());
        registerDefaults();
    }

    private void registerDefaults() {
        register(new ClaudeModel());
        register(new OpenAIModel());
        register(new GeminiModel());
    }

    public void register(IModel model) {
        this.models.add(model);
    }

    /**
     * Returns the model to serve this request.
     * Healthy models are preferred by priority; degraded models receive canary traffic.
     */
    public IModel selectModel() {
        List<IModel> sorted = new ArrayList<>(models);
        sorted.sort(Comparator.comparingInt(IModel::getPriority));

        // Collect degraded models (unhealthy, higher priority than the first healthy one)
        // and find the first healthy model to be the primary.
        List<IModel> degraded = new ArrayList<>();
        IModel primary = null;
        for (IModel model : sorted) {
            if (model.isHealthy()) {
                primary = model;
                break;
            }
            degraded.add(model);
        }

        // All models degraded — each gets a 5% canary slot; anything outside is rejected (null).
        if (primary == null) {
            double roll = new Random().nextDouble();
            double cursor = 0.0;
            for (IModel model : sorted) {
                cursor += CANARY_TRAFFIC_PERCENT;
                if (roll < cursor) {
                    return model;
                }
            }
            return null; // ~85% of requests rejected when all 3 models are degraded
        }

        // Each degraded model absorbs CANARY_TRAFFIC_PERCENT of the roll.
        // The remainder goes to the primary healthy model.
        // e.g. claude(down), openai(up): [0, 0.05) → claude, [0.05, 1.0) → openai
        double roll = new Random().nextDouble();
        double cursor = 0.0;
        for (IModel model : degraded) {
            cursor += CANARY_TRAFFIC_PERCENT;
            if (roll < cursor) {
                return model;
            }
        }
        return primary;
    }

    public List<IModel> getAllModels() {
        return new ArrayList<>(this.models);
    }
}