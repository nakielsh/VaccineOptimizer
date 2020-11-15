package vaccine.calculations;

import vaccine.file.ConfigurationIO;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.List;

public class VAM implements IVAM {
    public ConfigurationIO configurationIO = new ConfigurationIO();

    private List<Pharmacy> pharmacyList;
    private List<Manufacturer> manufacturerList;
    private List<Connection> connectionList;

    public VAM(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList) {
        this.pharmacyList = pharmacyList;
        this.manufacturerList = manufacturerList;
    }

    @Override
    public List<Connection> minimizeCost(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList) {
        return null;
    }

    @Override
    public void calculateVAMFactor() {

    }

    @Override
    public Object findGreatestVAMFactor() {
        return null;
    }

    @Override
    public int adjustPossibleQuantity(Pharmacy pharmacy, Manufacturer manufacturer) {
        return 0;
    }

    @Override
    public void generateConfigurationToFile() {

    }
}
