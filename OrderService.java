import java.util.*;

class OrderService {
    private ArrayDeque<Order> orderQueue = new ArrayDeque<Order>();
    private ArrayList<Order> finished = new ArrayList<Order>();
    private ArrayList<Drone> availableDrones = new ArrayList<Order>(); 

    OrderService() {}

    public void createOrder(Position position) {
        orderQueue.add(new Order(position));
    }

    public void addDrone(Drone drone) {
        availableDrones.push(drone);
    }

    public void process() {
        //start our service in new thread for parallel computing
        new Thread(() -> {
            while(true) {
                //if there is a new order in queue, calculate which drone will be the fastest one if any available
                if (orderQueue.size() > 0) {
                    Order currentOrder = orderQueue.pop();
                    Position orderPosition = currentOrder.getPosition()

                    int fastestTime = Double.POSITIVE_INFINITY;
                    Drone fastestDrone = null;
                    for (Drone drone: availableDrones) {
                        int requiredTime = drone.calculateRequiredTime(orderPosition);
                        if (requiredTime < fastestTime) {
                            fastestTime = requiredTime;
                            fastestDrone = drone;
                        }
                    }

                    if (fastestDrone != null) {
                        //remove fastest drone from available drones
                        availableDrones.remove(drone);
                        new Thread(() -> {
                            //drone delivers order
                            Thread.sleep(fastestTime);
                            //finish order and add drone to available drones
                            finished.add(currentOrder);
                            availableDrones.add(fastestDrone);
                        }).start()
                    }
                }
            }
        }).start()
    }
}