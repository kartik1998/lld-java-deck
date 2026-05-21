package emergent_lld;

public class OpenAIModel extends ModelBase {

    private static final String NAME = "openai";
    private static final int PRIORITY = 2;

    public OpenAIModel() {
        super(5, 5);
    }

    @Override
    public String getName() { return NAME; }

    @Override
    public int getPriority() { return PRIORITY; }
}