package emergent_lld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ModelBase implements IModel {

    protected final int successBaseline;
    protected final int errorBaseline;

    protected boolean healthy = true;
    protected int consecutiveFailures = 0;
    protected int consecutiveSuccesses = 0;
    List<Boolean> list = Arrays.asList(true, true, true, true, false, false, false, false);
    int idx = 0;

    public ModelBase(int successBaseline, int errorBaseline) {
        this.successBaseline = 2;
        this.errorBaseline = 2;
    }

    @Override
    public boolean isHealthy() {
        return healthy;
    }

    @Override
    public LLMResponse handlePrompt(String prompt) {
        boolean success = list.get(idx % list.size());
        idx++;
        if(success) {
            consecutiveSuccesses++;
            consecutiveFailures = 0;
            if(consecutiveSuccesses > successBaseline) {
                this.healthy = true;
            }
            return LLMResponse.success(String.format("LLMRESPONSE: %s", prompt), getName());
        } else {
            consecutiveFailures++;
            consecutiveSuccesses = 0;
            if(consecutiveFailures > errorBaseline) {
                this.healthy = false;
            }
            return LLMResponse.failure(String.format("LLMRESPONSE: %s", prompt), getName());
        }
    }

    @Override
    public void toggleSuccess() {
        this.healthy = !this.healthy;
    }
}