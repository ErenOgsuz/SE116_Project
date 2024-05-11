import java.util.ArrayList;
import java.util.Random;

public abstract class Station {
    private String stationID;
    private int maxCapacity;
    private int speed;
    private ArrayList<Task> tasksCanDo; // station can handle these tasks
    private ArrayList<Task> targetTasks; //station took these tasks but they wait for executing tasks
    private ArrayList<Task> currentTasks; // station execute these tasks
    private int currenttaskno = 0; // it is for executing tasks
    private double sumDuration;
    private static Random random = new Random();

    //for Stations which can handle multiple tasks
    public Station(String stationID, ArrayList<Task> tasksCanDo,int capacity){
        this.stationID = stationID;
        this.tasksCanDo = tasksCanDo;
        this.speed = 1;
        maxCapacity = capacity;
        this.targetTasks = new ArrayList<Task>();
        this.currentTasks = new ArrayList<Task>();
        //boolean[] desks= new boolean[capacity];
    }

    //for stations which can handle one task
    public Station(String stationID, ArrayList<Task> tasksCanDo){
        this.stationID = stationID;
        this.tasksCanDo = tasksCanDo;
        this.speed=1;
        maxCapacity=1;
        this.targetTasks = new ArrayList<Task>();
        this.currentTasks = new ArrayList<Task>();
        //boolean[] desks= new boolean[1];
    }

    public Station() {
    }
    public void setCurrentTaskNo(int currenttask) {
        this.currenttaskno = currenttask;
    }
    public int getCurrentTaskNo() {
        return currenttaskno;
    }
    public String getStationID() {return stationID;}
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
    public double getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(double sumDuration) {
        this.sumDuration = sumDuration;
    }

    // addTask for assign the task to station and set the task's job,jobType to create event when it started the executing
    public abstract void addTask(Task task) ;

    public void displayState(){
        if(currentTasks.isEmpty()){
            System.out.println(getStationID()+":Station is idle");
        }else{
            for (Task s : currentTasks){
                System.out.println("Task " + s.getTaskTypeID() +"("+s.getJobType().getJobTypeID()+"-"+s.getJob().getJobId()+ ") is running in " + this.stationID);
            }
            if(!targetTasks.isEmpty()){
                for (Task s : targetTasks){
                    System.out.print("Task " + s.getTaskTypeID() + ", ");
                }
                System.out.print("are waiting in line in " + this.stationID +"\n");
            }
        }
    }

    public abstract boolean pickTask(double startTime);
    public abstract double calculateFinishTime(Task task, double currentTime);

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
        Task currentTask=task;
        for(Task task1: getTasks()){
            if(task1.getTaskTypeID().equals(task.getTaskTypeID())){
                currentTask=task1;
                break;
            }
        }
        double minSpeed= task.getSize()/getSpeed()-task.getSize()/getSpeed()*currentTask.getPlusMinus();
        double maxSpeed=  task.getSize()/getSpeed()+task.getSize()/getSpeed()*currentTask.getPlusMinus();
        System.out.println(minSpeed+" "+maxSpeed);
        return minSpeed+(maxSpeed-minSpeed)*random.nextDouble();
    }
    public double calculateOptimalDuration(Task task){
        return task.getSize()/getSpeed();
    }
}
