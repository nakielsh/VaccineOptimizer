package vaccine.file;

import vaccine.exceptions.*;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class ConfigurationIO {

    List<Manufacturer> manufacturerList = new ArrayList<>();
    List<Pharmacy> pharmacyList = new ArrayList<>();

    public void loadFromFile(String path) throws Exception {
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
                    if (isPharmaciesInfo(line) || isConnectionsInfo(line)) break;


                    parseManufacturersLine(line);
                }
            }

            if (line == null) break;
            if (isPharmaciesInfo(line)) {
                while (true) {
                    line = reader.readLine();
                    if (line == null) break;
                    if (line.isBlank())
                        line = reader.readLine();
                    if (isManufacturersInfo(line) || isConnectionsInfo(line)) break;


                    parsePharmaciesLine(line);
                }
            }

            if (line == null) break;
            if (isConnectionsInfo(line)) {
                while (true) {
                    line = reader.readLine();
                    if (line == null) break;
                    if (line.isBlank())
                        line = reader.readLine();
                    if (isManufacturersInfo(line) || isPharmaciesInfo(line)) break;


                    parseConnectionsLine(line);
                }
            } else throw new FileStructureException("Wrong header");
        }
        reader.close();

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

    private void parseManufacturersLine(String line) throws FileStructureException {
        String[] args = line.split(" \\| ");
        if (args.length != 3)
            throw new FileStructureException("Wrong arguments separation or wrong arguments " +
                    "number in Manufacturers header");

        Manufacturer temp = new Manufacturer(parseInt(args[0]), args[1], parseInt(args[2]));
        manufacturerList.add(temp);

    }

    private void parsePharmaciesLine(String line) throws FileStructureException {
        String[] args = line.split(" \\| ");
        if (args.length != 3)
            throw new FileStructureException("Wrong arguments separation or wrong arguments " +
                    "number in Pharmacies header");
        Pharmacy temp = new Pharmacy(parseInt(args[0]), args[1], parseInt(args[2]));
        pharmacyList.add(temp);
    }

    private void parseConnectionsLine(String line) throws FileStructureException {
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
    }

    private void checkGeneratedObjects() throws Exception {
        List<String> pharmNames = new ArrayList<>();
        List<String> manNames = new ArrayList<>();
        List<Integer> pharmId = new ArrayList<>();
        List<Integer> manId = new ArrayList<>();

        int countConnections = 0;


        for (Pharmacy pharmacy : pharmacyList) {
            if (pharmNames.contains(pharmacy.getName()))
                throw new ExistingNameException("Same pharmacies " + pharmacy.getName());

            pharmNames.add(pharmacy.getName());

            if (pharmId.contains(pharmacy.getId()))
                throw new ExistingIdException("Same pharmacies id's " + pharmacy.getId());

            pharmId.add(pharmacy.getId());

            if (pharmacy.getId() < 0)
                throw new FileStructureException("ID of pharmacy: " + pharmacy.getName() + " is less than 0");

            if (pharmacy.getNeed() < 0)
                throw new FileStructureException("Need of pharmacy: " + pharmacy.getName() + " is less than 0");

            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getMaxQuantity() < 0)
                    throw new FileStructureException("MaxQuantity for connection: " + pharmacy.getName() + " and " +
                            connection.getManufacturer() + " is less than 0");

                if (connection.getPrice() < 0)
                    throw new FileStructureException("Price for connection: " + pharmacy.getName() + " and " +
                            connection.getManufacturer() + " is less than 0");

                countConnections++;
            }
        }
        for (Manufacturer manufacturer : manufacturerList) {
            if (manNames.contains(manufacturer.getName()))
                throw new ExistingNameException("Same manufacturers " + manufacturer.getName());

            manNames.add(manufacturer.getName());

            if (manId.contains(manufacturer.getId()))
                throw new ExistingIdException("Same manufacturers id's " + manufacturer.getId());

            if (manufacturer.getId() < 0)
                throw new FileStructureException("ID of manufacturer: " + manufacturer.getName() + " is less than 0");

            if (manufacturer.getDaily_production() < 0)
                throw new FileStructureException("Daily production of manufacturer: " + manufacturer.getName() + " is less than 0");

            manId.add(manufacturer.getId());
        }


        if (countConnections != pharmacyList.size() * manufacturerList.size())
            throw new WrongConnectionNumberException("There are " + countConnections +
                    " connections, while should be " + pharmacyList.size() * manufacturerList.size());

        if (isHigherNeedThanProduction())
            throw new HigherNeedThanProductionException("There is a higher daily need than daily production");

        if (checkPossibleToCreate() != null)
            throw new ConfigurationImpossibleToCreateException("Pharmacy need is greater than its sum of max " +
                    "quantities for every manufacturer. Pharmacy: " + Objects.requireNonNull(checkPossibleToCreate()).getName());


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


    private Pharmacy checkPossibleToCreate() {
        for (Pharmacy pharmacy : pharmacyList) {
            int pharmMaxQuantity = 0;
            for (Connection connection : pharmacy.getConnectionList()) {
                pharmMaxQuantity += connection.getMaxQuantity();
            }
            if (pharmacy.getNeed() > pharmMaxQuantity) return pharmacy;
        }
        return null;
    }

    public void saveToFile(List<Pharmacy> pharmacyList) throws FileNotFoundException {
        double sum = 0;
        String format = "%-25s%s%n";
        File file = new File("../2020Z_AISD_proj_ind_GR1_gr19/src/vaccine/result/result.txt");
        PrintWriter writer = new PrintWriter(file);


        for (Pharmacy pharmacy : pharmacyList) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getQuantity() > 0) {
                    sum += connection.getQuantity() * connection.getPrice();
                    writer.printf(format, connection.getManufacturer().getName(), "-> " + pharmacy.getName() +
                            " [Koszt = " + connection.getQuantity() + " * " + connection.getPrice() + " = " +
                            String.format("%.2f", connection.getQuantity() * connection.getPrice()) + " zł]");
                }
            }
        }
        writer.println("\nOpłaty całkowite: " + String.format("%.2f", sum) + " zł");
        writer.close();
        System.out.println("The output is saved to: " + file.getAbsolutePath());

    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }
}
