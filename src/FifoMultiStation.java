import java.util.ArrayList;

public class FifoMultiStation extends Station{

    public FifoMultiStation(String stationID, ArrayList<Task> tasks,int capacity){
        super(stationID,tasks,capacity);
    }

    public boolean pickTask(double startTime){
        if (!getTargetTasks().isEmpty()){
            if(getCurrenttask()<getMaxCapacity()) {
                Task newTask = getTargetTasks().getFirst();
                getCurrentTasks().add(newTask);
                setCurrenttask(getCurrenttask()+1);
                if (!getTargetTasks().isEmpty()) {
                    for (Task task : getCurrentTasks()) {
                        ArrayList<Task> newCurrentTasks = (ArrayList<Task>) getCurrentTasks().subList(1, getCurrentTasks().size()); //create a new arrayList for taking new task
                        setCurrentTasks(newCurrentTasks);
                    }
                }
                newTask.setStartTime(startTime);
                newTask.setFinishTime(startTime+ newTask.getDuration());
                Main.events.add(new Event(newTask.getJob(), newTask.getJobType(), newTask, newTask.getStation(), newTask.getFinishTime(), newTask.getStarTime()));
                newTask.setStateExecuting();
                displayState();
                return true;
            }else{
                return false;
            }
        } return false;
    }

    public void calculateStartTime(Task task) {
        double starttime = 0;
        for (int i = 0; i < task.getStation().getTasks().get(i).getDuration(); i++) {
            starttime += task.getStation().getTasks().get(i).getDuration();
        }
        System.out.println("Start time for the job is: " + starttime);
    }

/*
    public void addTask(Task task){
        getTargetTasks().add(task);
        calculateDuration(task);
    }

    public void setTargetTasks(ArrayList<Task> targetTasks) {setTargetTasks(targetTasks);}

    public void displayTheState(){
        System.out.println("The state of the station is: " + super.getState());
    }

    public void nextTask(FifoMultiStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }

    public void displayExecutingTasks(FifoMultiStation ss){
        System.out.println("Tasks being executed at fifo multi station:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }

 */
}
