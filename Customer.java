
public class Customer extends User {
    private String phone;
    private String address;

    public Customer() {}

    public Customer(String username, String password, String phone, String address) {
        super(username, password);
        this.phone = phone;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
