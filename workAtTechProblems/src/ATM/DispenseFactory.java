package ATM;

public class DispenseFactory {
    public static DispenseChain getRegularDispenseChain() {
        MoneyDispenseChain twoThousand = new MoneyDispenseChain(2000);
        MoneyDispenseChain fiveHundred = new MoneyDispenseChain(500);
        MoneyDispenseChain twoHundred = new MoneyDispenseChain(200);
        MoneyDispenseChain oneHundred = new MoneyDispenseChain(100);

        twoThousand.setNextDispenseChain(fiveHundred);
        fiveHundred.setNextDispenseChain(twoHundred);
        fiveHundred.setNextDispenseChain(oneHundred);
        return twoThousand;
    }
}
