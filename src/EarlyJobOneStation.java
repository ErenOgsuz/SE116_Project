import java.util.ArrayList;

public class EarlyJobOneStation extends Station {

    public EarlyJobOneStation(String stationID, ArrayList<Task> tasks) {
        super(stationID,tasks);
    }

    public boolean pickTask(double startTime){
        if (getCurrentTasks().isEmpty()){
            getCurrentTasks().add(getTargetTasks().getFirst());
            getTargetTasks().removeFirst();
            displayState();
        }
        return true;
    }

    public void calculateStartTime(Task task) {
        double starttime = 0;
        for (int i = 0; i < task.getStation().getTasks().get(i).getDuration(); i++) {
            starttime += task.getStation().getTasks().get(i).getDuration();
        }
        System.out.println("Start time for the job is: " + starttime);
    }

    public void displayTheState(){
        System.out.println("The state of the station is: " + super.getState());
    }

    public void nextTask(EarlyJobOneStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }

    public void displayExecutingTasks(EarlyJobOneStation ss){
        System.out.println("Tasks being executed at fifo one job station:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }
}
