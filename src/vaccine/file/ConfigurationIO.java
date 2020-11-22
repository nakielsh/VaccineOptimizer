package vaccine.file;

import vaccine.exceptions.*;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ConfigurationIO {
    String path = "/Users/hubertnakielski/Repozytoria/2020Z_AISD_proj_ind_GR1_gr19/src/vaccine/file/przykład_danych.txt";
    List<Manufacturer> manufacturerList = new ArrayList<>();
    List<Pharmacy> pharmacyList = new ArrayList<>();

    public void loadFromFile(String path) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));

            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                if (isManufacturersInfo(line)) {
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        if (line.isBlank())
                            line = reader.readLine();
                        if (line.equals(null) || isPharmaciesInfo(line) || isConnectionsInfo(line)) break;


                        parseManufacturersLine(line);
                    }
                }

                if (isPharmaciesInfo(line)) {
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        if (line.isBlank())
                            line = reader.readLine();
                        if (line.equals(null) || isManufacturersInfo(line) || isConnectionsInfo(line)) break;


                        parsePharmaciesLine(line);
                    }
                }

                if (isConnectionsInfo(line)) {
                    while (true) {
                        line = reader.readLine();
                        if (line == null) break;
                        if (line.isBlank())
                            line = reader.readLine();
                        if (line.equals(null) || isManufacturersInfo(line) || isPharmaciesInfo(line)) break;


                        parseConnectionsLine(line);
                    }
                } else throw new FileStructureException("Błędny nagłówek");
            }
            reader.close();
        } catch (FileStructureException e) {
            e.printStackTrace();
        }
        checkGeneratedObjects();

    }

    private boolean isPharmaciesInfo(String line) {
        return line.startsWith("#") && line.contains("Apteki (id | nazwa | dzienne zapotrzebowanie)");
    }

    private boolean isManufacturersInfo(String line) {
        return line.startsWith("#") && line.contains("Producenci szczepionek (id | nazwa | dzienna produkcja)");
    }

    private boolean isConnectionsInfo(String line) {
        return line.startsWith("#") && line.contains("Połączenia producentów i aptek (id producenta | id apteki |" +
                " dzienna maksymalna liczba dostarczanych szczepionek | koszt szczepionki [zł] )");
    }

    private void parseManufacturersLine(String line) {
        try {
            String[] args = line.split(" \\| ");
            if (args.length != 3)
                throw new FileStructureException("Wrong arguments separation or wrong arguments " +
                        "number in Manufacturers header");

            Manufacturer temp = new Manufacturer(parseInt(args[0]), args[1], parseInt(args[2]));
            manufacturerList.add(temp);
        } catch (FileStructureException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parsePharmaciesLine(String line) {
        try {
            String[] args = line.split(" \\| ");
            if (args.length != 3)
                throw new FileStructureException("Wrong arguments separation or wrong arguments " +
                        "number in Pharmacies header");
            Pharmacy temp = new Pharmacy(parseInt(args[0]), args[1], parseInt(args[2]));
            pharmacyList.add(temp);
        } catch (FileStructureException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parseConnectionsLine(String line) {
        try {
            String[] args = line.split(" \\| ");
            if (args.length != 4)
                throw new FileStructureException("Wrong arguments separation or wrong arguments " +
                        "number in Connections header");
            for (Pharmacy pharmacy : pharmacyList) {
                if (pharmacy.getId() == parseInt(args[1])) {

                    for (Manufacturer manufacturer : manufacturerList)
                        if (manufacturer.getId() == parseInt(args[0])) {
                            pharmacy.addConnection(manufacturer, pharmacy, parseInt(args[2]), parseDouble(args[3]));
                            manufacturer.addConnection(manufacturer, pharmacy, parseInt(args[2]), parseDouble(args[3]));
                        }
                }
            }
        } catch (FileStructureException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void checkGeneratedObjects() {
        try {
            List<String> pharmNames = new ArrayList<>();
            List<String> manNames = new ArrayList<>();
            List<Integer> pharmId = new ArrayList<>();
            List<Integer> manId = new ArrayList<>();
            for (Pharmacy pharmacy : pharmacyList) {
                if (pharmNames.contains(pharmacy.getName()))
                    throw new ExistingNameException("Same pharmacies " + pharmacy.getName());
                pharmNames.add(pharmacy.getName());
            }
            for (Manufacturer manufacturer : manufacturerList) {
                if (manNames.contains(manufacturer.getName()))
                    throw new ExistingNameException("Same manufacturers " + manufacturer.getName());
                manNames.add(manufacturer.getName());
            }
            for (Pharmacy pharmacy : pharmacyList) {
                if (pharmId.contains(pharmacy.getId()))
                    throw new ExistingIdException("Same pharmacies id's " + pharmacy.getId());
                pharmId.add(pharmacy.getId());
            }
            for (Manufacturer manufacturer : manufacturerList) {
                if (manId.contains(manufacturer.getId()))
                    throw new ExistingIdException("Same manufacturers id's " + manufacturer.getId());
                manId.add(manufacturer.getId());
            }
            int countConnections = 0;
            for (Pharmacy pharmacy : pharmacyList) {
                for (Connection connection : pharmacy.getConnectionList()) {
                    countConnections++;
                }
            }

            if (countConnections != pharmacyList.size() * manufacturerList.size())
                throw new WrongConnectionNumberException("There are " + countConnections +
                        " connections, while should be " + pharmacyList.size() * manufacturerList.size());

            if (isHigherNeedThanProduction())
                throw new HigherNeedThanProductionException("There is a higher daily need than daily production");

            if(checkPossibleToCreate() != null)
                throw new ConfigurationImpossibleToCreateException("Pharmacy need is greater than its sum of max " +
                        "quantities for every manufacturer. Pharmacy: " + checkPossibleToCreate().getName());

        } catch (ExistingNameException | HigherNeedThanProductionException | WrongConnectionNumberException | ExistingIdException | ConfigurationImpossibleToCreateException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private boolean isHigherNeedThanProduction() {
        int pharmNeed = 0;
        int manProd = 0;
        for (Pharmacy pharmacy : pharmacyList) {
            pharmNeed += pharmacy.getNeed();
        }
        for (Manufacturer manufacturer : manufacturerList) {
            manProd += manufacturer.getDaily_production();
        }
        return pharmNeed > manProd;
    }

    private Pharmacy checkPossibleToCreate(){
        for(Pharmacy pharmacy: pharmacyList){
            int pharmMaxQuantity = 0;
            for(Connection connection: pharmacy.getConnectionList()){
                pharmMaxQuantity += connection.getMaxQuantity();
            }
            if (pharmacy.getNeed() > pharmMaxQuantity) return pharmacy;
        }
        return null;
    }

    public void saveToFile(List<Pharmacy> pharmacyList) {
    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }
}
