package vaccine.calculations;

import vaccine.objects.Company;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VAM implements IVAM {
    //public ConfigurationIO configurationIO = new ConfigurationIO();

    private List<Pharmacy> pharmacyList;
    private List<Manufacturer> manufacturerList;

    public VAM(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList) {
        this.pharmacyList = pharmacyList;
        this.manufacturerList = manufacturerList;
    }

    @Override
    public void minimizeCost() {
        for (int i = 0; i < pharmacyList.size(); i++) {
            calculateVAMFactor();
            adjustPossibleQuantity(findGreatestVAMFactor());
        }

    }

    @Override
    public void calculateVAMFactor() {
        List<Double> prices = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacyList) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getQuantity() == 0 && getUnrealisedTransaction(pharmacy) > 0) {
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
            for (Pharmacy pharmacy : pharmacyList) {
                for (Connection connection : pharmacy.getConnectionList()) {
                    if (connection.getManufacturer().equals(manufacturer)) {
                        if (connection.getQuantity() == 0 && getUnrealisedTransaction(pharmacy) > 0) {
                            prices.add(connection.getPrice());
                        }
                    }
                }
            }
            int n = prices.size();
            Collections.sort(prices);
            if (prices.size() > 1)
                manufacturer.setVamFactor(prices.get(1) - prices.get(0));
            else if (prices.size() == 1)
                manufacturer.setVamFactor(0);
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
            if (pharmacy.getVamFactor() >= max && getUnrealisedTransaction(pharmacy) > 0) {
                max = pharmacy.getVamFactor();
                withMaxVam = pharmacy;
            }
        }
        for (Manufacturer manufacturer : manufacturerList) {
            if (manufacturer.getVamFactor() > max) {
                max = manufacturer.getVamFactor();
                withMaxVam = manufacturer;
            }
        }

        return withMaxVam;
    }

    @Override
    public void adjustPossibleQuantity(Company company) {
        Pharmacy calculatedPharmacy = company.getConnectionList().get(0).getPharmacy();
        Connection actualConnection = company.getConnectionList().get(0);
        double minPrice = company.getConnectionList().get(0).getPrice();
        double maxPrice = 0;


        for (Pharmacy pharmacy : pharmacyList) {
            if (getUnrealisedTransaction(pharmacy) > 0) {
                for (Connection connection : pharmacy.getConnectionList()) {
                    if (connection.getManufacturer().equals(company) || connection.getPharmacy().equals(company)) {
                        if (connection.getPrice() > maxPrice)
                            maxPrice = connection.getPrice();
                    }
                }
            }
        }

        for (Pharmacy pharmacy : pharmacyList) {
            if (getUnrealisedTransaction(pharmacy) > 0) {
                for (Connection connection : pharmacy.getConnectionList()) {
                    if (connection.getManufacturer().equals(company) || connection.getPharmacy().equals(company)) {
                        if (connection.getPrice() <= minPrice && connection.getMaxQuantity() > 0) {
                            minPrice = connection.getPrice();
                            calculatedPharmacy = connection.getPharmacy();
                            actualConnection = connection;
                        }
                    }
                }
            }
        }
        minPrice = maxPrice;

        while (getUnrealisedTransaction(calculatedPharmacy) > 0) {

            for (Connection connection : calculatedPharmacy.getConnectionList()) {
                if (connection.getPrice() <= minPrice && connection.getMaxQuantity() > 0 && connection.getQuantity() == 0 && getUnrealisedTransaction(calculatedPharmacy) > 0 && getUnrealisedSell(connection.getManufacturer()) > 0) {
                    minPrice = connection.getPrice();
                    actualConnection = connection;
                }
            }
            minPrice = maxPrice;

            List<Integer> doNotExceed = new ArrayList<>();
            doNotExceed.add(calculatedPharmacy.getNeed());
            doNotExceed.add(actualConnection.getMaxQuantity());
            doNotExceed.add(getUnrealisedTransaction(calculatedPharmacy));
            doNotExceed.add(getUnrealisedSell(actualConnection.getManufacturer()));
            Collections.sort(doNotExceed);

            actualConnection.setQuantity(doNotExceed.get(0));
        }

        for (Pharmacy pharmacy : pharmacyList) {
            pharmacy.setVamFactor(0);
        }
        for (Manufacturer manufacturer : manufacturerList) {
            manufacturer.setVamFactor(0);
        }

    }

    public int getUnrealisedTransaction(Pharmacy pharmacy) {
        int toBuy = pharmacy.getNeed();
        for (Connection connection : pharmacy.getConnectionList()) {
            toBuy -= connection.getQuantity();
        }
        return toBuy;
    }

    public int getUnrealisedSell(Manufacturer manufacturer) {
        int vaccRest = manufacturer.getDaily_production();
        for (Pharmacy pharmacy : pharmacyList) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getManufacturer().equals(manufacturer)) {
                    vaccRest -= connection.getQuantity();
                }
            }
        }
        return vaccRest;
    }

    @Override
    public void generateConfigurationToFile() {

    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }
}
