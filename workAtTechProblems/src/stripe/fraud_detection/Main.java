package stripe.fraud_detection;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        FraudDetectionHandler handler = new FraudDetectionHandler(
                Set.of("approved", "invalid_pin", "expired_card"),
                Set.of("do_not_honor", "stolen_card", "lost_card"),
                Map.of(5411, 0.2, 3000, 0.15),
                Map.of("acct_1", 5411, "acct_2", 3000),
                0
        );

        handler.recordTransactions(List.of(
                "CHARGE,ch_1,acct_1,100,approved",
                "CHARGE,ch_2,acct_1,50,stolen_card",
                "CHARGE,ch_3,acct_1,50,stolen_card",
                "CHARGE,ch_4,acct_1,50,stolen_card",
                "CHARGE,ch_5,acct_1,50,stolen_card"
        ));

        handler.dispute("ch_2");
        handler.dispute("ch_3");
        handler.dispute("ch_4");
        System.out.println(handler.getFraudAccounts());
    }
}
