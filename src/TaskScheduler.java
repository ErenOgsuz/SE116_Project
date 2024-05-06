import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class TaskScheduler {
    private Task task;
    private List<JobType> jobTypes;
    private List<Station> stations;
    private int roundRobinIndex = 0; // For round-robin station selection
    private static Random random = new Random();

    public TaskScheduler(Task task) {
        this.task=task;
    }

    public static void findSuitableStation(Task task) {
        List<Station> suitableStations = new ArrayList<>();

        for (Station station : Main.stationsTypes) {
            if (station.canExecuteTaskType(task.getTaskTypeID())) {
                suitableStations.add(station);
            }
        }

        Station station = (chooseStationRandomly(suitableStations));
        task.setStation(station); // Here, we randomly select a suitable station
        station.addTask(task); // Add task to stations targetTasks

        System.out.println("The suitable station is founded.");

    }

    private static Station chooseStationRandomly(List<Station> stations) {
        // Round-robin station selection
        // Uncomment this block if you want to use round-robin
        /*
        int index = roundRobinIndex % stations.size();
        roundRobinIndex++;
        return stations.get(index);
        */

        // Random station selection
        int index = random.nextInt(stations.size());
        return stations.get(index);
        // if the chosen station is not working like fifo set the times of tasks and rearrange the event times.
        //
    }
}
