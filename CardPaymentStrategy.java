
public class CardPaymentStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String expiry;
    private final String cvv;

    public CardPaymentStrategy(String cardNumber, String expiry, String cvv) {
        this.cardNumber = cardNumber;
        this.expiry = expiry;
        this.cvv = cvv;
    }

    @Override
    public PaymentResult pay(double amount) {
        if (!isValidCard()) {
            return new PaymentResult(false, "Invalid card details.");
        }
        return new PaymentResult(true, "Card payment authorized.");
    }

    @Override
    public String getName() {
        return "Card";
    }

    private boolean isValidCard() {
        return cardNumber != null && cardNumber.trim().length() >= 12
            && expiry != null && expiry.matches("\\d{2}/\\d{2}")
            && cvv != null && cvv.matches("\\d{3,4}");
    }
}
