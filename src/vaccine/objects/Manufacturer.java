package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Manufacturer extends Company {

    private final int daily_production;
    private final List<Connection> connectionList = new ArrayList<>();
    private double vamFactor;
    private int sold = 0;

    public Manufacturer(int id, String name, int daily_production) {
        super(id, name);
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


    public void addSold(int curSold) {
        sold += curSold;
    }

    public int leftToSell() {
        return daily_production - sold;
    }
}

