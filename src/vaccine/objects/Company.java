package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Company {
    protected int id;
    protected String name;
    protected List<Connection> connectionList = new ArrayList<>();
    protected double vamFactor;

    public Company(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
