package vaccine.file;

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
            }

        }
        reader.close();

    }

    private boolean isPharmaciesInfo(String line) {
        return line.startsWith("#") && line.contains("Apteki");
    }

    private boolean isManufacturersInfo(String line) {
        return line.startsWith("#") && line.contains("Producenci szczepionek");
    }

    private boolean isConnectionsInfo(String line) {
        return line.startsWith("#") && line.contains("Połączenia producentów i aptek");
    }

    private void parseManufacturersLine(String line) {
        String[] args = line.split(" \\| ");
        Manufacturer temp = new Manufacturer(parseInt(args[0]), args[1], parseInt(args[2]));
        manufacturerList.add(temp);
    }

    private void parsePharmaciesLine(String line) {
        String[] args = line.split(" \\| ");
        Pharmacy temp = new Pharmacy(parseInt(args[0]), args[1], parseInt(args[2]));
        pharmacyList.add(temp);
    }

    private void parseConnectionsLine(String line) {
        String[] args = line.split(" \\| ");
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

    public void saveToFile(List<Pharmacy> pharmacyList) {

    }

    public List<Pharmacy> getPharmacyList() {
        return pharmacyList;
    }

    public List<Manufacturer> getManufacturerList() {
        return manufacturerList;
    }
}
