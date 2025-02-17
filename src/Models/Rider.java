package Models;

import java.util.HashSet;
import java.util.Set;

public class Rider {
    public String id; // Unique identifier for the rider
    public int x, y; // Rider's coordinates
    private final Set<String> preferredDrivers; // Set of preferred driver IDs

    public Rider(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.preferredDrivers = new HashSet<>();
    }

    // Adds a driver to the rider's preferred list
    public void addPreferredDriver(String driverId) {
        this.preferredDrivers.add(driverId);
    }

    // Checks if a driver is in the rider's preferred list
    public boolean isPreferredDriver(String driverId) {
        return preferredDrivers.contains(driverId);
    }
}
