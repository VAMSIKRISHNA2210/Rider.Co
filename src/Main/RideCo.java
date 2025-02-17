package Main;

import Models.Driver;
import Services.RideService;

import java.util.List;
import java.util.Scanner;

/// Main class to run the ride application.
public class RideCo {
    private static final RideService rideService = new RideService(); // Instance of RideService

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        // Process commands until there are no more lines
        while (scanner.hasNextLine()) {
            String[] command = scanner.nextLine().split(" ", 4);
            switch (command[0]) {
                case "ADD_DRIVER":
                    rideService.addDriver(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "ADD_RIDER":
                    rideService.addRider(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "MATCH":
                    List<Driver> matchedDrivers = rideService.findNearestDrivers(command[1]);
                    if (matchedDrivers.isEmpty()) {
                        System.out.println("NO_DRIVERS_AVAILABLE");
                    } else {
                        System.out.print("DRIVERS_MATCHED");
                        for (Driver driver : matchedDrivers) {
                            System.out.print(" " + driver.id);
                        }
                        System.out.println();
                    }
                    break;
                case "PREFER_DRIVER":
                    rideService.preferDriver(command[1], command[2]);
                    break;
                case "START_RIDE":
                    rideService.startRide(command[1], Integer.parseInt(command[2]), command[3]);
                    break;
                case "STOP_RIDE":
                    rideService.stopRide(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]), Integer.parseInt(command[4]));
                    break;
                case "BILL":
                    rideService.generateBill(command[1]);
                    break;
                default:
                    System.out.println("INVALID_COMMAND");
            }
        }

        scanner.close(); // Close the scanner
    }
}
