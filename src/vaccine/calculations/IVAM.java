package vaccine.calculations;

import vaccine.objects.Company;

public interface IVAM {

    public void minimizeCost();

    public void calculateVAMFactor();

    public Object findGreatestVAMFactor();

    public void adjustPossibleQuantity(Company company);

    public void generateConfigurationToFile();
}
