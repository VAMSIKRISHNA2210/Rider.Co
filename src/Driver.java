class Driver {
    String id;
    int x, y;
    boolean isAvailable;

    public Driver(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isAvailable = true;
    }

    public double distanceTo(Rider rider) {
        return Math.sqrt(Math.pow(this.x - rider.x, 2) + Math.pow(this.y - rider.y, 2));
    }
}

