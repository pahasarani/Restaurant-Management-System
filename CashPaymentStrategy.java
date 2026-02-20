
public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentResult pay(double amount) {
        return new PaymentResult(true, "Cash on delivery selected.");
    }

    @Override
    public String getName() {
        return "Cash";
    }
}
