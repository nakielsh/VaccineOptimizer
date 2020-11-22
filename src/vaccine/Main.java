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
        double sum = 0;
        String format = "%-25s%s%n";

        for (Pharmacy pharmacy : vam.getPharmacyList()) {
            for (Connection connection : pharmacy.getConnectionList()) {
                if (connection.getQuantity() > 0) {
                    sum += connection.getQuantity() * connection.getPrice();
                    System.out.printf(format, connection.getManufacturer().getName(), "-> " + pharmacy.getName() +
                            " [Koszt = " + connection.getQuantity() + " * " + connection.getPrice() + " = " +
                            connection.getQuantity() * connection.getPrice() + " zł]");
                }
            }
        }
        System.out.println("\nOpłaty całkowite: " + sum + " zł");

    }
}
