import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class TaskScheduler {
    private List<JobType> jobTypes;
    private List<Station> stations;
    private int roundRobinIndex = 0; // For round-robin station selection
    private Random random = new Random();

    public TaskScheduler(List<JobType> jobTypes, List<Station> stations) {
        this.jobTypes = jobTypes;
        this.stations = stations;
    }

    public void handleCompletedTask(Task completedTask) {
        JobType jobType = findJobTypeByTask(completedTask);
        Task nextTask = findNextTaskInJobType(jobType, completedTask);
        if (nextTask == null) {
            System.out.println("No more tasks in job " + jobType.getJobID());
            return;
        }

        Station suitableStation = findSuitableStation(nextTask);

        if (suitableStation != null) {
            suitableStation.addTask(nextTask);
            nextTask.setStation(suitableStation);
            nextTask.setStateExecuting();
        } else {
            System.out.println("No suitable station found for task " + nextTask.getTaskTypeID());
        }
    }

    private JobType findJobTypeByTask(Task task) {
        for (JobType jobType : jobTypes) {
            if (jobType.getTasks().contains(task)) {
                return jobType;
            }
        }
        return null;
    }

    private Task findNextTaskInJobType(JobType jobType, Task completedTask) {
        List<Task> tasks = jobType.getTasks();
        int index = tasks.indexOf(completedTask);
        if (index < tasks.size() - 1) {
            return tasks.get(index + 1);
        } else {
            return null;
        }
    }

    private Station findSuitableStation(Task task) {
        List<Station> suitableStations = new ArrayList<>();

        for (Station station : stations) {
            if (station.canExecuteTaskType(task.getTaskTypeID())) {
                suitableStations.add(station);
            }
        }

        if (suitableStations.isEmpty()) {
            return null;
        }

        // Choose a station based on workload or other criteria
        // Here, we randomly select a suitable station
        // You can replace this with more sophisticated logic
        return chooseStationRandomly(suitableStations);
    }

    private Station chooseStationRandomly(List<Station> stations) {
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
    }
}
