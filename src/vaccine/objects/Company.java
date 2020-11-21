package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private int id;
    private String name;
    private List<Connection> connectionList = new ArrayList<>();
    private double vamFactor;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void addConnection(Manufacturer manufacturer, Pharmacy pharmacy, int quantity, double price) {
        connectionList.add(new Connection(manufacturer, pharmacy, quantity, price));
    }

    public double getVamFactor() {
        return vamFactor;
    }

    public void setVamFactor(double vamFactor) {
        this.vamFactor = vamFactor;
    }
}
