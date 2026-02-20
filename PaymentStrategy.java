
public interface PaymentStrategy {
    PaymentResult pay(double amount);

    String getName();
}
