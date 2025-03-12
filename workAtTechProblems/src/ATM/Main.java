package ATM;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        List<Integer> retval = atm.withdraw(1010);
        System.out.println(retval);
    }
}
