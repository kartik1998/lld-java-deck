package stripe.payment_card_validation_system;

public class Main {
    public static void main(String[] args) {
        PaymentCardValidator paymentCardValidator = new PaymentCardValidator();
//        System.out.println(paymentCardValidator.validateAndFetch("4532015112830366"));
//        System.out.println(paymentCardValidator.validateAndFetch("4242424242424243"));
//        System.out.println(paymentCardValidator.validateAndFetch("5482334509943"));


//        System.out.println(paymentCardValidator.fetchRedactedCards("4242424242424*42"));
//        System.out.println(paymentCardValidator.fetchRedactedCards("3*8282246310005"));
//        System.out.println(paymentCardValidator.fetchRedactedCards("**42424242424242"));

        paymentCardValidator.handleCorruptedCard("4344555566660004?");
    }
}
