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

        for (Pharmacy pharmacy : configurationIO.getPharmacyList()) {
            System.out.println("ID: " + pharmacy.getId() +
                    ", NAME: " + pharmacy.getName() +
                    ", NEED: " + pharmacy.getNeed() +
                    "\n");
            for (Connection connection : pharmacy.getConnectionList()) {
                System.out.println("\tMANUFACTURER: " + connection.getManufacturer().getName() +
                        ", PHARMACY: " + connection.getPharmacy().getName() +
                        ", QUANTITY: " + connection.getQuantity() +
                        ", PRICE: " + connection.getPrice() +
                        "\n");
            }
        }
    }
}
