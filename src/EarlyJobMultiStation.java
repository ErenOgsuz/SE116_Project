import java.util.ArrayList;
import java.util.Arrays;

public class EarlyJobMultiStation extends Station{

    public EarlyJobMultiStation(String stationID, ArrayList<Task> tasks,int capacity) {
        super(stationID,tasks,capacity);
    }

    // addTask method is overrided because it adds tasks sorted. it allows us to expand the program with different station types
    public void addTask(Task task){
        // Add the task to the station's task list
        ArrayList<Task> existingTargetTasks = new ArrayList<Task>();
        existingTargetTasks =this.getTargetTasks();
        for(int i=0;i<getTargetTasks().size();++i){
            if(getTargetTasks().get(i).getJob().getDeadline()>task.getJob().getDeadline()){
                existingTargetTasks.add(i,task);
            }
        }
        System.out.println("add çalışıyor"+existingTargetTasks.getFirst().getTaskTypeID());
        this.setTargetTasks(existingTargetTasks);
        if (getCurrentTaskNo() >= getMaxCapacity()) {
            setFull(false);// Station is full
        } else {
            setFull(true); // Station still has capacity
        }
    }

    // pickTask method is to pick a task from targetTask for that stations, if exists.
    // and it creates an event for scheduling.
    // ıt return boolean to say the station take another task.
    // ıt take double startTime, it comes from the ended task's finishTime
    public boolean pickTask(double startTime){
        if (!getTargetTasks().isEmpty()){
            if(getCurrentTaskNo()<1) {
                Task newTask = getTargetTasks().getFirst();
                getCurrentTasks().add(newTask);
                setCurrentTaskNo(getCurrentTaskNo()+1);
                getTargetTasks().removeFirst();
                /*if (!getTargetTasks().isEmpty()) {
                    ArrayList<Task> newCurrentTasks = (ArrayList<Task>) getCurrentTasks().subList(1, getCurrentTasks().size()); //create a new arrayList for taking new task
                    setCurrentTasks(newCurrentTasks);
                }*/
                // calculate duration to set the task for an event
                newTask.setDuration(calculateDuration(newTask));
                newTask.setStartTime(startTime);
                newTask.setFinishTime(startTime+ newTask.getDuration());
                Main.events.add(new Event(newTask, newTask.getStarTime(),"TaskStarting"));
                Main.events.add(new Event(newTask,  newTask.getFinishTime(), "TaskFinished"));
                newTask.setStateExecuting();
                //displayState();
                return true;
            }else{
                return false;
            }
        } return false;
    }

    // calculateStartTime is for find the optimal station
    public double calculateFinishTime(Task task, double currentTime) {

        double[] hasTimeToFinish = new double[getMaxCapacity()]; // for desks(based on capacity) of stations

        // to know how much time is left
        if(getCurrentTaskNo()!=0) {
            for (int i = 0; i <= getCurrentTaskNo(); ++i) {
                hasTimeToFinish[i] = getCurrentTasks().get(i).getFinishTime() - currentTime;
            }
        }

        // sort the left times to add times of targetTasks
        Arrays.sort(hasTimeToFinish);

        // we can add times of targetTasks task by task because it works like fifo
        int i=0;
        while(i < getTargetTasks().size()) {
            if(getTargetTasks().get(i).getJob().getDeadline()<task.getJob().getDeadline()) {
                for (int j = 0; j < hasTimeToFinish.length; j++) {
                    hasTimeToFinish[j] += calculateOptimalDuration(task.getStation().getTasks().get(i));
                    i++;
                }
            }else{
                break;
            }
        }

        // again sort to know fast desk and how much time left for it
        Arrays.sort(hasTimeToFinish);

        hasTimeToFinish[0]+=calculateOptimalDuration(task);

        // Returns how much time is left from the first idle table if it will be added now.
        return hasTimeToFinish[0];
    }
}
