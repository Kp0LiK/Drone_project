class Drone {
    private int battery = 100;
    private Position position;
    private boolean isAvailable = true;
    private static final DRONE_SPEED = 15;//meters by seconds
    private static final DRONE_DISTANCE_PER_ONE_PERCENT_OF_BATTERY = 150;//meters
    private static final DRONE_CHARGE_IN_ONE_SECOND = 1;//percent
    private ArrayList<Charger> chargers;

    Drone(Position position, ArrayList<Charger> chargers) {
        this.position = position;
        this.chargers = chargers;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void decreasePower(int n) {
        this.battery-=n;
    }

    //distance between two coordinates
    private double distance(Position pos1, Position pos2) {
        double lat1 = pos1.getLatitude();
        double lon1 = pos1.getLongitude();
        double lat2 = pos2.getLatitude();
        double lon2 = pos2.getLongitude();
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * 
                        Math.sin(deg2rad(lat2)) + 
                        Math.cos(deg2rad(lat1)) * 
                        Math.cos(deg2rad(lat2)) * 
                        Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344*1000;
        return dist;
    }
    //this function converts decimal degrees to radian
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    //  This function converts radians to decimal degrees
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    //how many power do drone need to go this distance
    private int calculateRequiredPower(double dist) {
        return Math.ceil(dist/150);
    }

    public int calculateRequiredTime(Position pos) {
        //chech whether we need to charge drone
        double droneAndOrderDistance = distance(pos, this.position);
        int needPower = calculateRequiredPower(droneAndOrderDistance);

        boolean needCharge = needPower > this.battery;

        if (!needCharge) {
            return Math.ceil(droneAndOrderDistance)/DRONE_SPEED;
        } else {
            //if need charging find optimal charger
            int nearestChargeAndOrderDistance = Double.POSITIVE_INFINITY;
            Charger nearestChargeAndOrder = null;
            for (Charger charger: this.chargers) {
                int dis = distance(this.position, charger.getPosition())+distance(charger.getPosition(), pos);
                if (dis < nearestChargeAndOrderDistance) {
                    nearestChargeAndOrder = dis;
                    nearestChargeAndOrder = charger;
                }
            }
            return Math.ceil(nearestChargeAndOrderDistance/DRONE_SPEED);
        }
    }
}