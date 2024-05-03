import java.util.ArrayList;

public abstract class Station {
    private String stationID;
    private int maxCapacity;
    private String state; // it will change in TaskSchedular.
    private boolean isFull;
    private int speed;
    private ArrayList<Task> tasksCanDo;
    private ArrayList<Task> targetTasks;
    private ArrayList<Task> currentTasks;
    private int currenttaskno; // it is for executing tasks
    //private boolean[] desks;// capacity of the station is the size.

    //for Stations which can handle multiple tasks
    public Station(String stationID, ArrayList<Task> tasksCanDo,int capacity){
        this.stationID = stationID;
        this.tasksCanDo = tasksCanDo;
        this.speed = 1;
        maxCapacity = capacity;
        //boolean[] desks= new boolean[capacity];
    }

    //for stations which can handle one task
    public Station(String stationID, ArrayList<Task> tasksCanDo){
        this.stationID = stationID;
        this.tasksCanDo = tasksCanDo;
        this.speed=1;
        maxCapacity=1;
        boolean[] desks= new boolean[1];
    }

    public Station() {
    }

    public void setCurrenttask(int currenttask) {
        this.currenttaskno = currenttask;
    }
    public int getCurrenttask() {
        return currenttaskno;
    }
    public String getStationID() {return stationID;}
    public void setState(String state) {this.state = state;}
    public String getState() {return state;}
    public int getSpeed() {return speed;}
    public ArrayList<Task> getTasks() {return tasksCanDo;}
    public ArrayList<Task> getTargetTasks() {return targetTasks;}
    public void setTargetTasks(ArrayList<Task> targetTasks) {this.targetTasks = targetTasks;}
    /*public boolean[] getDesks() {return desks;}
    public void setDesks(boolean[] desks) {this.desks = desks;}
*/
    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<Task> getCurrentTasks() {
        return currentTasks;
    }
    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }


    public void addTask(Task task,Job job,JobType jobType) {
        // set the task to the job to create event
        task.setJob(job);
        // set the task to the jobType to create event
        task.setJobType(jobType);
        // Add the task to the station's task list
        targetTasks.add(task);
        // Set the station of the task to this station
        task.setStation(this);
        // calculate duration to set the task for an event
        calculateDuration(task);
        // Update the state of the station
        if (currenttaskno >= maxCapacity) {
            isFull = false; // Station is full
        } else {
            isFull = true; // Station still has capacity
        }
    }

    public void displayState(){
        if(targetTasks.isEmpty()){
            System.out.println("Station is idle");
        }else{
            for (Task s : currentTasks){
                System.out.println("Task " + s.getTaskTypeID() + "is running");
            }
            for (Task s : targetTasks){
                System.out.print("Task " + s.getTaskTypeID() + ", ");
            }
            System.out.print("are waiting in line");
        }
    }

    public abstract boolean pickTask(double startTime);

    public boolean canExecuteTaskType(String taskType) {
        for (Task task : tasksCanDo) {
            if (task.getTaskTypeID().equals(taskType)) {
                return true; // Station has at least one task of the given type
            }
        }
        return false; // Station does not have any task of the given type
    }

    //calculateDuration is to select the fastest station.
    public void calculateDuration(Task task){
        task.setDuration(task.getSize()/getSpeed());
    }
}
