package vaccine;

import vaccine.calculations.VAM;
import vaccine.file.ConfigurationIO;
import vaccine.objects.Connection;
import vaccine.objects.Manufacturer;
import vaccine.objects.Pharmacy;

import java.io.IOException;
import java.util.Scanner;

///Users/hubertnakielski/Repozytoria/2020Z_AISD_proj_ind_GR1_gr19/src/vaccine/file/przykład_danych.txt
public class Main {
    public static void main(String[] args) throws IOException {
        ConfigurationIO configurationIO = new ConfigurationIO();
        long comparisonEnd;
        long comparisonStart;
        long time = 0;

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
            int sum = 0;
            need += pharmacy.getNeed();
            System.out.println(pharmacy.getName() + " (Need): " + pharmacy.getNeed());
            for (Connection connection : pharmacy.getConnectionList()) {
                sum += connection.getQuantity();
                got += connection.getQuantity();
            }
            System.out.println("\t" + sum );
        }

        System.out.println("Need: " + need);
        System.out.println("Got: " + got);

        for(Manufacturer manufacturer: vam.getManfacturerList()){
            int sum = 0;
            System.out.println(manufacturer.getName() + " (production): " + manufacturer.getDaily_production());
            for(Connection connection: manufacturer.getConnectionList()){
                sum+= connection.getQuantity();
            }
            System.out.println("\t" + sum );
        }


    }
}
