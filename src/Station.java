import java.util.ArrayList;

public class Station {
    private String stationID;
    private int maxCapacity;
    private boolean state;
    private int speed;
    private ArrayList<Task> tasks;
    private ArrayList<Task> targetTasks;
    private int currenttaskno;

    public Station(String stationID, ArrayList<Task> tasks,int capacity){
        this.stationID = stationID;
        this.tasks = tasks;
        this.speed = 1;
        maxCapacity = capacity;
        boolean[] desks= new boolean[capacity];

    }

    public void addTask(Task task) {
        // Add the task to the station's task list
        tasks.add(task);
        // Set the station of the task to this station
        task.setStation(this);
        // Update the current task count in the station
        currenttaskno++;
        // Update the state of the station
        if (currenttaskno >= maxCapacity) {
            state = false; // Station is full
        } else {
            state = true; // Station still has capacity
        }
    }


    public boolean canExecuteTaskType(String taskType) {
        for (Task task : tasks) {
            if (task.getTaskTypeID().equals(taskType)) {
                return true; // Station has at least one task of the given type
            }
        }
        return false; // Station does not have any task of the given type
    }


    public void setCurrenttask(int currenttask) {
        this.currenttaskno = currenttask;
    }
    public int getCurrenttask() {
        return currenttaskno;
    }
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
    public boolean getState() {
        return state;
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
