import java.util.ArrayList;
import java.util.Random;

public abstract class Station {
    private String stationID;
    private int maxCapacity;
    private String state; // it will change in TaskSchedular.
    private boolean isFull;
    private int speed;
    private ArrayList<Task> tasksCanDo; // station can handle these tasks
    private ArrayList<Task> targetTasks = new ArrayList<Task>(); //station took these tasks but they wait for executing tasks
    private ArrayList<Task> currentTasks = new ArrayList<Task>(); // station execute these tasks
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
        //boolean[] desks= new boolean[1];
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
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public ArrayList<Task> getCurrentTasks() {
        return currentTasks;
    }
    public void setCurrentTasks(ArrayList<Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    // addTask for assign the task to station and set the task's job,jobType to create event when it started the executing
    public void addTask(Task task,Job job,JobType jobType) {
        // set the task to the job to create event
        task.setJob(job);
        // set the task to the jobType to create event
        task.setJobType(jobType);
        // Set the station of the task to this station
        task.setStation(this);
        // Update the state of the station
        if (currenttaskno >= maxCapacity) {
            isFull = false; // Station is full
        } else {
            isFull = true; // Station still has capacity
        }
        this.targetTasks.add(task);
        this.currenttaskno++;
        task.setStation(this);
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
    public abstract double calculateStartTime(Task task, double currentTime);

    // to check the taskType can handle the taskType
    public boolean canExecuteTaskType(String taskType) {
        for (Task task : tasksCanDo) {
            if (task.getTaskTypeID().equals(taskType)) {
                return true; // Station has at least one task of the given type
            }
        }
        return false; // Station does not have any task of the given type
    }

    //calculateDuration is to select the fastest station.
    public double calculateDuration(Task task){
        Random random=new Random();
        double minSpeed= task.getSize()/getSpeed()-task.getSize()/getSpeed()*task.getPlusMinus();
        double maxSpeed=  task.getSize()/getSpeed()+task.getSize()/getSpeed()*task.getPlusMinus();
        return minSpeed+(maxSpeed-minSpeed)*random.nextDouble();
    }
    public double calculateOptimalDuration(Task task){
        return task.getSize()/getSpeed();
    }
}
