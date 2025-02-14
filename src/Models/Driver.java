package Models;

/**
 * Represents a Driver in the ride-sharing application.
 */
public class Driver {
    public String id; // Unique identifier for the driver
    public int x, y; // Driver's current coordinates
    public boolean isAvailable; // Availability status of the driver

    /**
     * Constructor to initialize a Driver object.
     *
     * @param id Unique identifier for the driver
     * @param x  X-coordinate of the driver's location
     * @param y  Y-coordinate of the driver's location
     */
    public Driver(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isAvailable = true; // Driver is available upon creation
    }

    /**
     * Calculates the distance from this driver to a given rider.
     *
     * @param rider The rider to calculate the distance to
     * @return The distance to the rider, rounded to two decimal places
     */
    public double distanceTo(Rider rider) {
        double distance = Math.sqrt(Math.pow(this.x - rider.x, 2) + Math.pow(this.y - rider.y, 2));
        return Math.round(distance * 100.0) / 100.0; // Fix floating-point precision
    }
}