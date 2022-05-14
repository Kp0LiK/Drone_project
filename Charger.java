class Charger {
    private Position position;
    private int numberOfSlots;
    private int busySlots = 0;

    private Drone drone;

    Charger(Position position, int numberOfSlots) {
        this.position = position;
        this.numberOfSlots = numberOfSlots;
    }

    public Charger setDrone(Drone targetDrone) {
        drone = targetDrone;
        return this;
    }

    public void useSlot() {
        if (this.numberOfSlots > this.busySlots) {
            this.busySlots++;
        } else {
            System.out.println("There is no available slot.");
        }
    }

    public void freeSlot() {
        busySlots--;
    }

    public boolean isAvailableSlotExists() {
        return this.busySlots < this.numberOfSlots;
    }

    public Position getPosition() {
        return this.position;
    }
}