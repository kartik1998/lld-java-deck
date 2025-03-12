package ATM;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DispenseChain chain = DispenseFactory.getRegularDispenseChain();

        List<Integer> retval = chain.dispenseCurrency(2100);
        System.out.println(retval);
    }
}
