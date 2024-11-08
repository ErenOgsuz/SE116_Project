import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class TaskScheduler {
    public static Station findSuitableStation(Task task,double currentTime) {
        List<Station> suitableStations = new ArrayList<>();

        System.out.println("\nSearching Station To Execute:");

        for (Station station : Main.stationsTypes) {
            if (station.canExecuteTaskType(task.getTaskTypeID())) {
                suitableStations.add(station);
            }
        }

        //ArrayList<Double> possibleTimes= new ArrayList<Double>();

        Station correctStation=suitableStations.get(0);

        double finishTime=correctStation.calculateFinishTime(task,currentTime);

        for(Station station: suitableStations){
            if(station.calculateFinishTime(task,currentTime)<finishTime){
                finishTime=station.calculateFinishTime(task,currentTime);
                correctStation=station;
            }
        }

        System.out.println("The "+task.getTaskTypeID()+" of "+task.getJob().getJobId()+" ("+task.getJob().getJobType().getJobTypeID()+") will approximately finish at "+finishTime+".");
        task.setStation(correctStation); // Here, we set the task's station
        correctStation.addTask(task); // Add task to stations targetTasks

        System.out.println("The "+correctStation.getStationID()+" station will execute : "+ task.getTaskTypeID()+" of "+task.getJob().getJobId());
        return correctStation;
    }
}
