package atm_design;

import java.util.ArrayList;
import java.util.List;

public abstract class DispenseChain {
    protected Integer denomination;
    protected DispenseChain nextDispenseChain;

    public DispenseChain(Integer denomination) {
        this.denomination = denomination;
    }

    public List<Integer> dispenseCurrency(Integer amount) {
        Integer rem = amount;
        List<Integer> retval = new ArrayList<>();
        while (rem >= denomination) {
            rem -= denomination;
            retval.add(denomination);
        }
        if (rem > 0 && this.nextDispenseChain != null) {
            List<Integer> nextRet = this.nextDispenseChain.dispenseCurrency(rem);
            retval.addAll(nextRet);
        } else if(rem > 0) {
            throw new RuntimeException("Cannot be dispensed remaining amount is rem=" + rem);
        }
        return retval;
    }

    public void setNextDispenseChain(DispenseChain dispenseChain) {
        this.nextDispenseChain = dispenseChain;
    }
}
