package vaccine.objects;

public class Connection {

    Manufacturer manufacturer;
    Pharmacy pharmacy;
    int quantity;
    double price;

    public Connection(Manufacturer manufacturer, Pharmacy pharmacy, int quantity, double price) {
        this.manufacturer = manufacturer;
        this.pharmacy = pharmacy;
        this.quantity = quantity;
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
