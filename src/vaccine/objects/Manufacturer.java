package vaccine.objects;

public class Manufacturer {

    private int id;
    private String name;
    private int daily_production;

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
}
