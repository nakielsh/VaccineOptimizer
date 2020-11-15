package vaccine.objects;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy {
    private int id;
    private String name;
    private int need;
    private List<Connection> connectionList = new ArrayList<>();

    public Pharmacy(int id, String name, int need) {
        this.id = id;
        this.name = name;
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

    public void addConnection(Manufacturer manufacturer, int quantity, double price){
        connectionList.add(new Connection(manufacturer, quantity, price));
    }

}
