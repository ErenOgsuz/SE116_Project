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

    public static Station findSuitableStation(Task task,double currentTime) {
        List<Station> suitableStations = new ArrayList<>();

        for (Station station : Main.stationsTypes) {
            if (station.canExecuteTaskType(task.getTaskTypeID())) {
                suitableStations.add(station);
            }
        }

        ArrayList<Double> possibleTimes= new ArrayList<Double>();

        Station correctStation=suitableStations.getFirst();
        double finishTime=correctStation.calculateFinishTime(task,currentTime);

        for(Station station: suitableStations){
            //System.out.println(station.getStationID());
           // System.out.println(finishTime);
           // System.out.println(station.calculateFinishTime(task,currentTime));
            if(station.calculateFinishTime(task,currentTime)<finishTime){
                finishTime=station.calculateFinishTime(task,currentTime);
                correctStation=station;
                System.out.println(finishTime+" it is for "+ correctStation.getStationID());
            }
        }
        task.setStation(correctStation); // Here, we set the task's station
        correctStation.addTask(task); // Add task to stations targetTasks

        System.out.println("The "+correctStation.getStationID()+" station will execute : "+ task.getTaskTypeID()+" of "+task.getJob().getJobId());
        return correctStation;
    }
}
