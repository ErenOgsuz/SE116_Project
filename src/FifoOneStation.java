import java.util.ArrayList;

public class FifoOneStation extends Station {

    public FifoOneStation(String stationID, ArrayList<Task> tasks) {
        super(stationID, tasks);
    }
    // pickTask method is to pick an task from targetTask for that stations, if exists.
    // and it create an event for scheduling.
    // ıt return boolean to say the station take another task.
    // ıt take double startTime, it comes from the ended task's finishTime
    public boolean pickTask(double startTime){
        if (!getTargetTasks().isEmpty()){
            if(getCurrenttask()<1) {
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

    // addTask method is to add new task to targetTask. the added task will wait for execute.
    /* there is asame class at station
    public void addTask(Task task,Job job,JobType jobType){
        task.setJob(job);
        task.setJobType(jobType);
        getTargetTasks().add(task);
        calculateDuration(task);
        super.setCurrenttask(getCurrenttask()+1);
    }*/

    //  the start time should set when it picked..

    public void calculateStartTime(Task task) {
        double starttime = 0;
        for (int i = 0; i < task.getStation().getTasks().get(i).getDuration(); i++) {
            starttime += task.getStation().getTasks().get(i).getDuration();
        }
        System.out.println("Start time for the job is: " + starttime);
    }

    // disPLayTheState for req 5 but there is same method at the Station class
    /*public void displayTheState(){
        if(!getCurrentTasks().isEmpty()) {
            System.out.print("The executing task is");
            for (Task task : getCurrentTasks()) {
                System.out.println(" " + task);
            }
            System.out.println(".");
        }
        if(!getTargetTasks().isEmpty()) {
            System.out.println("These tasks are waiting to execute:");
            for (Task task : getTargetTasks()) {
                System.out.println(" " + task);
            }
        }
        System.out.println("The state of the station is: " + super.getState());
    }



    public void nextTask(FifoOneStation s, int currenttask){
        if (currenttask < s.getTasks().size() - 1) {
            currenttask++;
            s.setCurrenttask(currenttask);
            ArrayList<Task> t = new ArrayList<>(s.getTasks());
            String nextTask = t.get(currenttask).getTaskTypeID();
        } else {
            System.out.println("All the tasks are completed.");
        }
    }


    //there is same method at the station class
    public void displayExecutingTasks(FifoOneStation ss){
        System.out.println("Tasks being executed at fifo one job station:");
        for (Task s : ss.getTasks()) {
            String stationId = s.getStation().getStationID();
            int x = ss.getCurrenttask();
            System.out.println("Station" + stationId + ": " + x);
        }
    }

     */
}
