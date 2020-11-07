package vaccine.objects;

public class Pharmacy {
    private int id;
    private String name;
    private int need;

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
}
