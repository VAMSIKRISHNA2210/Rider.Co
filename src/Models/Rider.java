package Models;

/**
 * Represents a Rider in the ride-sharing application.
 */
public class Rider {
    public String id; // Unique identifier for the rider
    public int x, y; // Rider's current coordinates

    /**
     * Constructor to initialize a Rider object.
     *
     * @param id Unique identifier for the rider
     * @param x  X-coordinate of the rider's location
     * @param y  Y-coordinate of the rider's location
     */
    public Rider(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
}