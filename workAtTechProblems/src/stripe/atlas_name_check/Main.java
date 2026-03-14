package stripe.atlas_name_check;

public class Main {
    public static void main(String[] args) {
        StripeManager manager = new StripeManager();
        manager.registerAttempt(123, "Llama, Inc.");
        manager.registerAttempt(456, "The Llama Inc.");
        manager.registerAttempt(789, "Alpaca Corp.");

        manager.reclaim(456, "Llama, Inc.");
        manager.reclaim(123, "Llama, Inc.");
        manager.registerAttempt(456, "The Llama Inc.");
    }
}
