import java.util.ArrayList;

public class FifoOneStation extends Station {

    public FifoOneStation(String stationID, ArrayList<Task> tasks) {
        super(stationID, tasks);
    }

    // addTask method is overrided because it adds tasks sorted. it allows us to expand the program with different station types
    public void addTask(Task task){
        // Add the task to the station's task list
        this.getTargetTasks().add(task);
    }

    // pickTask method is to pick a task from targetTask for that stations, if exists.
    // and it creates an event for scheduling.
    // ıt return boolean to say the station take another task.
    // ıt take double startTime, it comes from the ended task's finishTime
    public boolean pickTask(double startTime){
        if (!getTargetTasks().isEmpty()){
            if(getCurrentTaskNo()<1) {
                Task newTask = getTargetTasks().get(0);
                getCurrentTasks().add(newTask);
                setCurrentTaskNo(getCurrentTaskNo()+1);
                getTargetTasks().remove(0);
                // calculate duration to set the task for an event
                newTask.setDuration(calculateDuration(newTask));
                newTask.setStartTime(startTime);
                newTask.setFinishTime(startTime+ newTask.getDuration());
                Main.events.add(new Event(newTask,  newTask.getStarTime(),"TaskStarting"));
                Main.events.add(new Event(newTask,  newTask.getFinishTime(), "TaskFinished"));
                newTask.setStateExecuting();
                setSumDuration(getSumDuration()+newTask.getDuration());
                //displayState();
                return true;
            }else{
                return false;
            }
        } return false;
    }

    // calculateStartTime is for find the optimal station
    public double calculateFinishTime(Task task, double currentTime) {
        double startTime=currentTime;

        if(getCurrentTaskNo()!=0) {
            startTime += getCurrentTasks().get(0).getFinishTime() - currentTime;
        }
        if(!getTargetTasks().isEmpty()) {
            for (int i = 0; i < getTargetTasks().size(); i++) {
                startTime += calculateOptimalDuration(this.getTargetTasks().get(i));
            }
        }
        startTime+=calculateOptimalDuration(task);

        return startTime;
    }

}