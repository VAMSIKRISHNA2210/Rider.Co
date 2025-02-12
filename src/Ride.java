class Ride {
    String rideId;
    Rider rider;
    Driver driver;
    boolean isActive;
    int startX, startY;
    int endX, endY;
    int duration;

    public Ride(String rideId, Rider rider, Driver driver) {
        this.rideId = rideId;
        this.rider = rider;
        this.driver = driver;
        this.startX = rider.x;
        this.startY = rider.y;
        this.isActive = true;
    }

    public void endRide(int endX, int endY, int duration) {
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.isActive = false;
    }
}

