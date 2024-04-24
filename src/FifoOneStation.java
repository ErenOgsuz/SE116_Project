public class FifoOneStation extends Station {
    public void addTask(Task task){
        getTargetTasks().add(task);
        task.setDuration(calculateDuration(task));
    }
    public void calculateStartTime(Task task){}
    public void displayTheState(){}
    public void nextTask(){}
    public void displayExecutingTasks(){}
}
