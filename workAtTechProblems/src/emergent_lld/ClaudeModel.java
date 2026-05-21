package emergent_lld;

public class ClaudeModel extends ModelBase {

    private static final String NAME = "claude";
    private static final int PRIORITY = 1;

    public ClaudeModel() {
        super(5, 5);
    }

    @Override
    public String getName() { return NAME; }

    @Override
    public int getPriority() { return PRIORITY; }
}