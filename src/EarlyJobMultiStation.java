import java.util.ArrayList;

public class EarlyJobMultiStation extends Station{

    public EarlyJobMultiStation(String stationID, ArrayList<Task> tasks,int capacity) {
        super(stationID,tasks,capacity);
    }

    public void pickTask(){
        if (getCurrentTasks().size() < getMaxCapacity()){
            getCurrentTasks().add(getTargetTasks().getFirst());
            getTargetTasks().removeFirst();
            displayState();
        }
    }

    public void displayTheState(){
        System.out.println("The state of the station is: " + super.getState());
    }

    public void nextTask(EarlyJobMultiStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }

    public void displayExecutingTasks(EarlyJobMultiStation ss){
        System.out.println("Tasks being executed at early job multi station:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }
}
