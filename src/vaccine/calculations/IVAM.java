package vaccine.calculations;

import vaccine.objects.Company;

public interface IVAM {

    void minimizeCost();

    void calculateVAMFactor();

    Object findGreatestVAMFactor();

    void adjustPossibleQuantity(Company company);


}
