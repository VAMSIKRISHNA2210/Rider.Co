import java.util.*;

public class RideHailingApp {
    private static final RideService rideService = new RideService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
