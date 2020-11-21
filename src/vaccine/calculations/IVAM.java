package vaccine.calculations;

import vaccine.objects.Company;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.List;

public interface IVAM {

    public void minimizeCost();

    public void calculateVAMFactor();

    public Object findGreatestVAMFactor();

    public void adjustPossibleQuantity(Company company);

    public void generateConfigurationToFile();
}
