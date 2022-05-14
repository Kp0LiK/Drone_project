class Order {
    private Position position;
    private String status = "In queue";

    Order(Position position) {
        this.position = position;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    public void getPosition() {
        return this.position;
    }
}