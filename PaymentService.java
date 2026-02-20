
public class PaymentService {
    public PaymentStrategy cash() {
        return new CashPaymentStrategy();
    }

    public PaymentStrategy card(String number, String expiry, String cvv) {
        return new CardPaymentStrategy(number, expiry, cvv);
    }
}
