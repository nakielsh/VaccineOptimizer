package vaccine.objects;

import java.util.List;

public class Manufacturer {

    private int id;
    private String name;
    private int daily_production;
    private List<Connection> connectionList;
    private int vamFactor;

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

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public int getVamFactor() {
        return vamFactor;
    }

    public void setVamFactor(int vamFactor) {
        this.vamFactor = vamFactor;
    }
}
