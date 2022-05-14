class Position {
    private double latitude, longitude;

    Position(double x, double y) {
        this.latitude = x;
        this.longitude = y;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}