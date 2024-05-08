import java.util.ArrayList;

public class EarlyJobOneStation extends Station {

    public EarlyJobOneStation(String stationID, ArrayList<Task> tasks) {
        super(stationID,tasks);
    }

    // addTask method is overrided because it adds tasks sorted. it allows us to expand the program with different station types
    public void addTask(Task task){
        // Add the task to the station's task list
       if(!getTargetTasks().isEmpty()){
           for(int i=0;i<=getTargetTasks().size();++i){
               if(getTargetTasks().get(i).getJob().getDeadline()>task.getJob().getDeadline()){
                   this.getTargetTasks().add(i,task);
               }
           }
       }else{
           this.getTargetTasks().add(task);
       }

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
                Main.events.add(new Event(newTask, newTask.getFinishTime(), "TaskFinished"));
                newTask.setStateExecuting();
                //displayState();
                return true;
            }else{
                return false;
            }
        } return false;
    }

    // calculateStartTime is for find the optimal station
    public double calculateFinishTime(Task task,double currentTime) {
        double startTime=0.0;

        if(getCurrentTaskNo()!=0) {
            startTime = currentTime + getCurrentTasks().getFirst().getFinishTime() - currentTime;
        }
        if(!getTargetTasks().isEmpty()){
            for (int i = 0; i < getTargetTasks().size(); i++) {
                if(getTargetTasks().get(i).getJob().getDeadline()<task.getJob().getDeadline()) {
                    startTime += calculateOptimalDuration(this.getTargetTasks().get(i));
                }else{
                    break;
                }
            }
        }
        startTime+=calculateDuration(task);

        return startTime;
    }
}
