package vaccine;

import vaccine.calculations.VAM;
import vaccine.file.ConfigurationIO;
import vaccine.objects.Connection;
import vaccine.objects.Pharmacy;

import java.io.IOException;
import java.util.Scanner;

///Users/hubertnakielski/Repozytoria/2020Z_AISD_proj_ind_GR1_gr19/src/vaccine/file/przykład_danych.txt
public class Main {
    public static void main(String[] args) throws IOException {
        ConfigurationIO configurationIO = new ConfigurationIO();
        System.out.println("Enter file path: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        configurationIO.loadFromFile(path);
        VAM vam = new VAM(configurationIO.getPharmacyList(), configurationIO.getManufacturerList());
        vam.minimizeCost();

        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            System.out.println("ID: " + pharmacy.getId() +
                    ", NAME: " + pharmacy.getName() +
                    ", NEED: " + pharmacy.getNeed() +
                    ", VAM: " + pharmacy.getVamFactor() +
                    "\n");
            for (Connection connection : pharmacy.getConnectionList()) {
                System.out.println("\tMANUFACTURER: " + connection.getManufacturer().getName() +
                        ", PHARMACY: " + connection.getPharmacy().getName() +
                        ", MAX QUANTITY: " + connection.getMaxQuantity() +
                        ", QUANTITY: " + connection.getQuantity() +
                        ", PRICE: " + connection.getPrice() +
                        "\n");
            }
        }
        System.out.println(vam.findGreatestVAMFactor().getName());
    }
}
