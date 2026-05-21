package emergent_lld;

public class RejectModel extends ModelBase {
    public RejectModel(int successBaseline, int errorBaseline) {
        super(successBaseline, errorBaseline);
    }

    @Override
    public String getName() {
        return "REJECT MODEL";
    }

    @Override
    public int getPriority() {
        return 1000;
    }
}
