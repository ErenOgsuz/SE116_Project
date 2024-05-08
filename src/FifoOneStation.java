import java.util.ArrayList;

public class FifoOneStation extends Station {

    public FifoOneStation(String stationID, ArrayList<Task> tasks) {
        super(stationID, tasks);
    }

    // addTask method is overrided because it adds tasks sorted. it allows us to expand the program with different station types
    public void addTask(Task task){
        // Add the task to the station's task list
        ArrayList<Task> existingTargetTasks = new ArrayList<Task>();
        existingTargetTasks =this.getTargetTasks();
        existingTargetTasks.add(task);
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
                Main.events.add(new Event(newTask,  newTask.getStarTime(),"TaskStarting"));
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
        double startTime=0.0;

        if(getCurrentTaskNo()!=0) {
            startTime = currentTime + getCurrentTasks().getFirst().getFinishTime() - currentTime;
        }

        for (int i = 0; i < getTargetTasks().size(); i++) {
            startTime += calculateOptimalDuration(this.getTargetTasks().get(i));
        }

        startTime+=calculateOptimalDuration(task);

        return startTime;
    }

}