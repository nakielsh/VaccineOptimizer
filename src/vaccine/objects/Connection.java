package vaccine.objects;

public class Connection {

    Manufacturer manufacturer;
    Pharmacy pharmacy;
    int maxQuantity;
    double price;
    int quantity;

    public Connection(Manufacturer manufacturer, Pharmacy pharmacy, int maxQuantity, double price) {
        this.manufacturer = manufacturer;
        this.pharmacy = pharmacy;
        this.maxQuantity = maxQuantity;
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getMaxQuantity() {
        return maxQuantity;
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
