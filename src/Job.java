import java.time.LocalTime;
import java.time.Duration;

public class Job {
    private final String jobId;
    private final JobType jobTypeId; // "Has-a"jobType
    private final LocalTime startTime;
    private final Duration duration;
    private final LocalTime deadline;
    private String state;
    private Task executingTask;
    private Task waitingToExecute;
    private Duration delayTime;

    public Job(String jobId, JobType jobTypeId, int startTime, int duration) {
        this.jobId = jobId;
        this.jobTypeId = jobTypeId;

        this.startTime = LocalTime.of(startTime / 60, startTime % 60);

        int minute = duration / 60; // calculate minutes
        int second = duration % 60; // calculate seconds

        Duration durationToAdd = Duration.ofMinutes(minute).plusSeconds(second);

        this.duration = durationToAdd;
        this.deadline = this.startTime.plus(durationToAdd);
        this.state="Waiting..";
    }

    // Getter methods
    public String getJobId() {
        return jobId;
    }

    public JobType getJobTypeId() {
        return jobTypeId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalTime getDeadline(){
        return deadline;
    }

    public void setState(LocalTime time) {
        if(time.isBefore(getStartTime())){
            this.state="Waiting..";
        }else{
            this.state="Executing..";
        }
        int countDone=0;
        for(Task task:jobTypeId.getTasks()){
            if(task.getState().contains("Done.")){
                countDone++;
            }
        }
        if(countDone==jobTypeId.getTasks().size()){
            state="Done.";
        }
    }

    public String getState(){
        return state;
    }

    public void setExecutingTask(){
        for(Task task:jobTypeId.getTasks()){
            if(task.getState().contains("Executing..")){
                executingTask=task;
                break;
            }
        }
    }
    public Task getExecutingTask(){
        return executingTask;
    }

    public void setWaitingToExecute(){
        int size = jobTypeId.getTasks().size();
        for (int i = 0; i < size; i++) {
            Task task = jobTypeId.getTasks().get(i);
            if (task.getState().contains("Executing..")) {
                waitingToExecute =jobTypeId.getTasks().get(i+1) ;
                break;
            }
        }
    }

    public Task getWaitingToExecute(){
        return waitingToExecute;
    }

    public Duration getDelayTime(){
        delayTime = Duration.between(getStartTime(), jobTypeId.getTasks().getLast().getFinishTime());
        return delayTime;
    }

}
