package vaccine;

import vaccine.calculations.VAM;
import vaccine.file.ConfigurationIO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurationIO configurationIO = new ConfigurationIO();
        long start;
        long stop;

        System.out.println("Enter file path: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        start = System.nanoTime();
        vam.minimizeCost();
        stop = System.nanoTime();
        configurationIO.saveToFile(vam.getPharmacyList());
        System.out.println("Time: " + ((stop - start)));
    }
}
