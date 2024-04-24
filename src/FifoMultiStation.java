import java.time.LocalTime;

public class FifoMultiStation extends Station{
    public void addTask(Task task){
        getTargetTasks().add(task);
        task.setDuration(calculateDuration(task));
    }
    public LocalTime calculateStartTimePossability(Task task){
        long minuteForStart=0L;
        for(Task task1: getTargetTasks()){
            minuteForStart+=(long)task1.getDuration();
        }
        LocalTime currentTime = LocalTime.now();
        LocalTime startTime = currentTime.plusMinutes(minuteForStart);
        task.setStartTime(startTime);
        return currentTime;
    }
    public void displayTheState(){}
    public void nextTask(){}
    public void displayExecutingTasks(){}
}
