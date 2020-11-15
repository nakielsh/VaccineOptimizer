package vaccine.calculations;

import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.List;

public interface IVAM {

    public List<Connection> minimizeCost(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList);

    public void calculateVAMFactor();

    public Object findGreatestVAMFactor();

    public int adjustPossibleQuantity(Pharmacy pharmacy, Manufacturer manufacturer);

    public void generateConfigurationToFile();
}
