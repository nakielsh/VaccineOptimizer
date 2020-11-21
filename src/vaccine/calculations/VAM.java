package vaccine.calculations;

import vaccine.file.ConfigurationIO;
import vaccine.objects.Company;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VAM implements IVAM {
    public ConfigurationIO configurationIO = new ConfigurationIO();

    private List<Pharmacy> pharmacyList;
    private List<Manufacturer> manufacturerList;

    public VAM(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList) {
        this.pharmacyList = pharmacyList;
        this.manufacturerList = manufacturerList;
    }

    @Override
    public void minimizeCost() {
        for( Pharmacy pharmacy: pharmacyList) {
            calculateVAMFactor();
            adjustPossibleQuantity(findGreatestVAMFactor());
        }

    }

    @Override
    public void calculateVAMFactor() {
        List<Double> prices = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacyList) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if(connection.getQuantity() == 0 ){
                    prices.add(connection.getPrice());
                }
            }
            int n = prices.size();
            Collections.sort(prices);
            if (prices.size() > 1)
                pharmacy.setVamFactor(prices.get(1) - prices.get(0));
            else if (prices.size() == 1)
                pharmacy.setVamFactor(prices.get(0));
            else
                pharmacy.setVamFactor(0);
            prices.clear();
        }
        for (Manufacturer manufacturer : manufacturerList) {
            for (Connection connection : manufacturer.getConnectionList()) {
                if( connection.getQuantity() == 0) {
                    prices.add(connection.getPrice());
                }
            }
            int n = prices.size();
            Collections.sort(prices);
            if (prices.size() > 1)
                manufacturer.setVamFactor(prices.get(1) - prices.get(0));
            else if (prices.size() == 1)
                manufacturer.setVamFactor(prices.get(0));
            else
                manufacturer.setVamFactor(0);

            prices.clear();
        }
    }


    @Override
    public Company findGreatestVAMFactor() {
        Company withMaxVam = null;
        double max = 0;
        for (Pharmacy pharmacy : pharmacyList) {
            if (pharmacy.getVamFactor() >= max) {
                max = pharmacy.getVamFactor();
                withMaxVam = pharmacy;
                assert withMaxVam instanceof Pharmacy;
            }
        }
        for (Manufacturer manufacturer : manufacturerList) {
            if (manufacturer.getVamFactor() >= max) {
                max = manufacturer.getVamFactor();
                withMaxVam = manufacturer;
                assert withMaxVam instanceof Manufacturer;
            }
        }

        return withMaxVam;
    }

    @Override
    public void adjustPossibleQuantity(Company company) {
        Pharmacy calculatedPharmacy = company.getConnectionList().get(0).getPharmacy();
        Connection actualConnection= company.getConnectionList().get(0);
        double minPrice = company.getConnectionList().get(0).getPrice();
        for( Connection connection1: company.getConnectionList()) {

            for (Connection connection : company.getConnectionList()) {
                if (connection.getPrice() <= minPrice && connection.getQuantity() > 0) {
                    minPrice = connection.getPrice();
                    calculatedPharmacy = connection.getPharmacy();
                    actualConnection = connection;
                }
            }

            List<Integer> doNotExceed = new ArrayList<>();
            doNotExceed.add(calculatedPharmacy.getNeed());
            doNotExceed.add(actualConnection.getMaxQuantity());
            doNotExceed.add(getUnrealisedTransaction(calculatedPharmacy));
            doNotExceed.add(getUnrealisedSell(actualConnection.getManufacturer()));
            Collections.sort(doNotExceed);

            connection1.setQuantity(doNotExceed.get(0));
        }

    }

    public int getUnrealisedTransaction(Pharmacy pharmacy) {
        int toBuy =pharmacy.getNeed();
        for (Connection connection : pharmacy.getConnectionList()) {
            toBuy -= connection.getQuantity();
        }
        return toBuy;
    }

    public int getUnrealisedSell(Manufacturer manufacturer) {
        int vaccRest = manufacturer.getDaily_production();
        for (Connection connection : manufacturer.getConnectionList()) {
            vaccRest -= connection.getQuantity();
        }
        return vaccRest;
    }

    @Override
    public void generateConfigurationToFile() {

    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }
}
