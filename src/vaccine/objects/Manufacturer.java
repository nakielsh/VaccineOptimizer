package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Manufacturer extends Company {

    private int id;
    private String name;
    private int daily_production;
    private List<Connection> connectionList = new ArrayList<>();
    private double vamFactor;

    public Manufacturer(int id, String name, int daily_production) {
        this.id = id;
        this.name = name;
        this.daily_production = daily_production;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDaily_production() {
        return daily_production;
    }

    public void addConnection(Manufacturer manufacturer, Pharmacy pharmacy, int quantity, double price) {
        connectionList.add(new Connection(manufacturer, pharmacy, quantity, price));
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public double getVamFactor() {
        return vamFactor;
    }

    public void setVamFactor(double vamFactor) {
        this.vamFactor = vamFactor;
    }
}
