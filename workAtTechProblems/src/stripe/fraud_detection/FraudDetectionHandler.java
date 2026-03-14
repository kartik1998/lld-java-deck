package stripe.fraud_detection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class FraudDetectionHandler {
    class Account {
        public final String id;
        public final int mccCode;
        List<String> transactions = new ArrayList<>();
        boolean isFraudulent = false;

        Account(String id, int mccCode) {
            this.id = id;
            this.mccCode = mccCode;
        }

        public boolean isFraudulent() {
            if (transactions.size() < minimumTransactions) {
                return false;
            }

            if (isFraudulent) return true;

            int fraudCount = 0;
            for (String transaction : transactions) {
                String code = transaction.split(",")[4];
                if (fraudCodes.contains(code.toLowerCase())) {
                    fraudCount++;
                }
            }
            double threshold = mccThresholdMap.get(mccCode);
            if ((double) fraudCount / (double) transactions.size() > threshold) {
                this.isFraudulent = true;
            }
            return isFraudulent;
        }
    }

    private final Set<String> validCodes;
    private final Set<String> fraudCodes;
    private final Map<Integer, Double> mccThresholdMap;
    private final Map<String, Integer> accountToMccMap;
    private final int minimumTransactions;
    private final Map<String, Account> accountMap = new HashMap<>();

    public FraudDetectionHandler(Set<String> validCodes,
                                 Set<String> fraudCodes,
                                 Map<Integer, Double> mccThresholdMap,
                                 Map<String, Integer> accountToMccMap, int minimumTransactions) {
        this.validCodes = validCodes;
        this.fraudCodes = fraudCodes;
        this.mccThresholdMap = mccThresholdMap;
        this.accountToMccMap = accountToMccMap;
        this.minimumTransactions = minimumTransactions;

        for (String account : accountToMccMap.keySet()) {
            accountMap.put(account, new Account(account, accountToMccMap.get(account)));
        }
    }

    public void recordTransactions(List<String> transactions) {
        for (String transaction : transactions) {
            String arr[] = transaction.split(",");
            String account = arr[2];
            accountMap.get(account).transactions.add(transaction);
        }
    }

    public String getFraudAccounts() {
        List<String> res = new ArrayList<>();
        for (Account account : accountMap.values()) {
            if (account.isFraudulent()) {
                res.add(account.id);
            }
        }
        Collections.sort(res);
        return res.stream().collect(Collectors.joining(","));
    }
}
