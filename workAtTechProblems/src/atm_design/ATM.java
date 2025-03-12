package atm_design;

import java.util.List;

public class ATM {
    DispenseChain chain = DispenseFactory.getRegularDispenseChain();

    public List<Integer> withdraw(Integer amount) {
        // TODO: add a check here to verify that withdrawal is possible or not
        return chain.dispenseCurrency(amount);
    }
}
