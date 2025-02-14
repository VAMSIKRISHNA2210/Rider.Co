package Main;

import Services.RideService;

import java.util.*;

/**
 * Main class to run the ride-sharing application.
 */
public class RideCo {
    private static final RideService rideService = new RideService(); // Instance of RideService

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        // Process commands until there are no more lines
        while (scanner.hasNextLine()) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "ADD_DRIVER":
                    rideService.addDriver(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "ADD_RIDER":
                    rideService.addRider(command[1], Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                    break;
                case "MATCH":
                    List<String> matchedDrivers = rideService.findNearestDrivers(command[1])
                            .stream()
                            .map(d -> d.id)
                            .toList();
                    if (matchedDrivers.isEmpty()) {
                        System.out.println("NO_DRIVERS_AVAILABLE");
                    } else {
                        System.out.println("DRIVERS_MATCHED " + String.join(" ", matchedDrivers));
                    }
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

        scanner.close();
    }
}