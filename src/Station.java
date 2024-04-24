import java.util.ArrayList;

public class Station {
    private String stationID;
    private int maxCapacity;
    private boolean state;
    private int speed;
    private ArrayList<Task> tasks;
    private ArrayList<Task> targetTasks;
    public boolean[] desks;// capacity of the station is the size.
    //for stations which can handle one task
    public Station(String stationID, ArrayList<Task> tasks){
        this.stationID = stationID;
        this.tasks = tasks;
        this.speed=1;
        maxCapacity=1;
        boolean[] desks= new boolean[1];
    }
    //for Stations which can handle multiple tasks
    public Station(String stationID, ArrayList<Task> tasks,int capacity){
        this.stationID = stationID;
        this.tasks = tasks;
        this.speed=1;
        maxCapacity=capacity;
        boolean[] desks= new boolean[capacity];

    }

    public Station() {
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public ArrayList<Task> getTargetTasks() {return targetTasks;}
    public void setTargetTasks(ArrayList<Task> targetTasks) {this.targetTasks = targetTasks;}
    public boolean[] getDesks() {return desks;}

    public void setDesks(boolean[] desks) {this.desks = desks;}

    //calculateDuration is to select the fastest station.
    public double calculateDuration(Task task){
        double duration=task.getSize()/getSpeed();
        return duration;
    }
}
