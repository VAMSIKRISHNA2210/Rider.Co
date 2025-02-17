package Models;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public String id; // Unique identifier for the driver
    public int x, y; // Driver's coordinates
    public boolean isAvailable; // Availability status of the driver
    private final List<Integer> ratings; // List to store driver ratings

    public Driver(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isAvailable = true; // Driver is available upon creation
        this.ratings = new ArrayList<>();
    }

    // Adds a rating to the driver if it's within the valid range
    public void addRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            ratings.add(rating);
        }
    }

    // Calculates the average rating of the driver
    public double getAverageRating() {
        if (ratings.isEmpty()) return 0;
        double sum = ratings.stream().mapToInt(Integer::intValue).sum();
        return Math.round((sum / ratings.size()) * 10.0) / 10.0; // Round to 1 decimal
    }

    // Calculates the distance from the driver to a rider
    public double distanceTo(Rider rider) {
        double distance = Math.sqrt(Math.pow(this.x - rider.x, 2) + Math.pow(this.y - rider.y, 2));
        return Math.round(distance * 100.0) / 100.0; // Round to 2 decimal places
    }
}