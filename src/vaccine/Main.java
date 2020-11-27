package vaccine;

import vaccine.calculations.VAM;
import vaccine.file.ConfigurationIO;
import vaccine.objects.Pharmacy;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        ConfigurationIO configurationIO = new ConfigurationIO();
        long comparisonEnd;
        long comparisonStart;
        long time;

        System.out.println("Enter file path: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();

        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        comparisonStart = System.nanoTime();
        vam.minimizeCost();
        comparisonEnd = System.nanoTime();
        time = (comparisonEnd - comparisonStart);
        configurationIO.saveToFile(vam.getPharmacyList());
        System.out.println("Time: " + time);

        int need = 0;
        int got = 0;
        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            need += pharmacy.getNeed();
            got += pharmacy.getNeed() - pharmacy.leftToLoad();
        }

        System.out.println("Need: " + need);
        System.out.println("Got: " + got);

    }
}
