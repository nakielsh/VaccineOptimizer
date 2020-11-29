package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy extends Company {

    private final int need;
    private int bought = 0;
    private final List<Connection> connectionList = new ArrayList<>();

    public Pharmacy(int id, String name, int need) {
        super(id, name);
        this.need = need;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getNeed() {
        return need;
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


    public void addBought(int curBought) {
        bought += curBought;
    }

    public int leftToLoad() {
        return need - bought;
    }
}
