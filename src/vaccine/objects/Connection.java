package vaccine.objects;

public class Connection {

    Manufacturer manufacturer;
    int quantity;
    double price;

    public Connection(Manufacturer manufacturer, int quantity, double price) {
        this.manufacturer = manufacturer;
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
}
