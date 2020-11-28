package vaccine.calculations;

import vaccine.objects.Company;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VAM {

    private List<Pharmacy> pharmacyList;
    private List<Manufacturer> manufacturerList;

    public VAM(List<Pharmacy> pharmacyList, List<Manufacturer> manufacturerList) {
        this.pharmacyList = pharmacyList;
        this.manufacturerList = manufacturerList;
    }

    public void minimizeCost() {

        for (int i = 0; i < pharmacyList.size(); i++) {
            calculateVAMFactor();
            adjustPossibleQuantity(findGreatestVAMFactor());
        }
    }

    public void calculateVAMFactor() {
        List<Double> prices = new ArrayList<>();

        for (Pharmacy pharmacy : pharmacyList) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getQuantity() == 0 && pharmacy.leftToLoad() > 0) {
                    prices.add(connection.getPrice());
                }
            }
            int n = prices.size();
            Collections.sort(prices);
            if (n > 1)
                pharmacy.setVamFactor(prices.get(1) - prices.get(0));
            else if (n == 1)
                pharmacy.setVamFactor(prices.get(0));
            else
                pharmacy.setVamFactor(0);
            prices.clear();
        }

        for (Manufacturer manufacturer : manufacturerList) {
            for (Connection connection : manufacturer.getConnectionList()) {
                if (connection.getQuantity() == 0 && connection.getPharmacy().leftToLoad() > 0) {
                    prices.add(connection.getPrice());
                }
            }
            int n = prices.size();
            Collections.sort(prices);
            if (n > 1)
                manufacturer.setVamFactor(prices.get(1) - prices.get(0));
            else if (n == 1)
                manufacturer.setVamFactor(0);
            else
                manufacturer.setVamFactor(0);

            prices.clear();
        }
    }

    public Company findGreatestVAMFactor() {
        Company withMaxVam = null;
        double max = 0;
        for (Pharmacy pharmacy : pharmacyList) {
            if (pharmacy.getVamFactor() >= max && pharmacy.leftToLoad() > 0) {
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

    public void adjustPossibleQuantity(Company company) {
        Pharmacy calculatedPharmacy = null;
        Connection actualConnection = null;
        double minPrice = 1000000000;


        for (Pharmacy pharmacy : pharmacyList) {
            if (pharmacy.leftToLoad() > 0) {
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

        if (calculatedPharmacy == null)
            return;

        while (calculatedPharmacy.leftToLoad() > 0) {

            minPrice = 1000000000;
            for (Connection connection : calculatedPharmacy.getConnectionList()) {
                if (connection.getPrice() <= minPrice && connection.getMaxQuantity() > 0 && connection.getQuantity() == 0
                        && calculatedPharmacy.leftToLoad() > 0 && connection.getManufacturer().leftToSell() > 0) {
                    minPrice = connection.getPrice();
                    actualConnection = connection;
                }
            }

            List<Integer> doNotExceed = new ArrayList<>();
            doNotExceed.add(calculatedPharmacy.getNeed());
            doNotExceed.add(actualConnection.getMaxQuantity());
            doNotExceed.add(calculatedPharmacy.leftToLoad());
            doNotExceed.add(actualConnection.getManufacturer().leftToSell());
            Collections.sort(doNotExceed);

            actualConnection.setQuantity(doNotExceed.get(0));
            calculatedPharmacy.addBought(doNotExceed.get(0));
            actualConnection.getManufacturer().addSold(doNotExceed.get(0));
        }
    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }
}
