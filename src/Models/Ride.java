package Models;

/**
 * Represents a Ride in the ride-sharing application.
 */
public class Ride {
    public String rideId; // Unique identifier for the ride
    public Rider rider; // The rider associated with this ride
    public Driver driver; // The driver associated with this ride
    public boolean isActive; // Status of the ride (active or completed)
    public int startX, startY; // Starting coordinates of the ride
    public int endX, endY; // Ending coordinates of the ride
    public int duration; // Duration of the ride in minutes

    /**
     * Constructor to initialize a Ride object.
     *
     * @param rideId Unique identifier for the ride
     * @param rider  The rider associated with this ride
     * @param driver The driver associated with this ride
     */
    public Ride(String rideId, Rider rider, Driver driver) {
        this.rideId = rideId;
        this.rider = rider;
        this.driver = driver;
        this.startX = rider.x; // Set starting coordinates to rider's location
        this.startY = rider.y;
        this.isActive = true; // Ride is active upon creation
    }

    /**
     * Ends the ride and sets the ending coordinates and duration.
     *
     * @param endX    Ending X-coordinate of the ride
     * @param endY    Ending Y-coordinate of the ride
     * @param duration Duration of the ride in minutes
     */
    public void endRide(int endX, int endY, int duration) {
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.isActive = false; // Mark the ride as completed
    }
}

