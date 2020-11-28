package vaccine;

import vaccine.calculations.VAM;
import vaccine.file.ConfigurationIO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurationIO configurationIO = new ConfigurationIO();

        System.out.println("Enter file path: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();
        configurationIO.saveToFile(vam.getPharmacyList());
    }
}
