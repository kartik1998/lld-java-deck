package emergent_lld;

public class GeminiModel extends ModelBase {

    private static final String NAME = "gemini";
    private static final int PRIORITY = 3;

    public GeminiModel() {
        super(5, 5);
    }

    @Override
    public String getName() { return NAME; }

    @Override
    public int getPriority() { return PRIORITY; }
}