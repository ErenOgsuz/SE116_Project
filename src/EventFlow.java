import java.util.Collections;
import java.util.Comparator;

public class EventFlow {
    public static void eventFlow(){
        boolean allJobsNotFinished=true;
        int finishedJobs=0;
        double currentTime=0.0;
        Station station;
        double starTime;
        double deadLine;
        double jobStartTime;
        JobType jobType;
        Task task;

        for(Job job: Main.jobs){
            // set the state of job
            job.setState(currentTime);
        }

        // create the events to trigger start the jobs..
        for(Job job :Main.jobs){
            //Job job1 = new Job(job.getJobId(),job.getJobType(),job.getStartTime(),job.getDuration());
            Main.events.add(new Event(job,job.getJobType(),job.getStartTime(),"JobStarting"));
        }

        /*for(Event e : Main.events){
            System.out.println(e.getJob().getJobId());
        }*/

        Collections.sort(Main.events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // İki double değeri karşılaştırıyoruz
                return Double.compare(e1.getEventStartTime(), e2.getEventStartTime());
            }
        });

        /*for(Event e : Main.events){
            System.out.println(e.getJob().getJobId());
        }*/

        Event nextEvent;
        int eventCount=0;

        do{
            nextEvent=Main.events.get(eventCount);

            currentTime=nextEvent.getEventStartTime();
            // user will know current time
            System.out.println("Time is: "+currentTime);

            // if The nextEvent is triggering the job to start
            if(nextEvent.getEventType().equals("JobStarting")) {

                System.out.println(nextEvent.getJob().getJobId() + " has started!");
                System.out.println("First task getting started");

                // set the state of job
                nextEvent.getJob().setState(currentTime);

                // select the first task's station and the station's currentTask, desks for that event rearrange
                TaskScheduler.findSuitableStation(nextEvent.getJobType().getTasks().getFirst(),nextEvent.getEventStartTime());

            // if The nextEvent is triggering the job to finish
            }else if(nextEvent.getEventType().equals("JobFinished")) {

                System.out.println(nextEvent.getJob().getJobId() + " has finished!");
                // set the state of job
                nextEvent.getJob().setState(currentTime);
                finishedJobs++;

            //if the nextEvent is trigerring the task to start
            }else if(nextEvent.getEventType().equals("TaskStarting")) {

                System.out.println(nextEvent.getTask().getTaskTypeID() + " of " + nextEvent.getTask().getJob().getJobId() + " has started!");

                // set state of the task which is starting
                nextEvent.getTask().setStateExecuting();
                // req 6: display the state of the station
                nextEvent.getTask().getStation().displayState();

             // if the nextEvent is trigerring the task to finish
            }else if(nextEvent.getEventType().equals("TaskFinished")){

                System.out.println(nextEvent.getTask().getTaskTypeID() + " has finished!");
                // the task's state and the executing task for the job of the task will rearrange
                nextEvent.getTask().setStateDone();
                // set the current task index for the job which the task finished
                nextEvent.getTask().getJob().getJobType().increaseTaskIndex();
                if(nextEvent.getTask().getJob().getJobType().getTaskIndex() >= nextEvent.getTask().getJob().getJobType().getTasks().size()){
                    Main.events.add(new Event(nextEvent.getTask().getJob(),nextEvent.getTask().getJob().getJobType(),nextEvent.getEventStartTime(),"JobFinished"));
                }

                // the station's currentTask, desks for that event rearrange -> and the executing task will create a new event
                nextEvent.getStation().getCurrentTasks().remove(nextEvent.getTask());
                nextEvent.getStation().setCurrentTaskNo(nextEvent.getStation().getCurrentTaskNo()-1);
                // req 6: display the state of the station
                nextEvent.getStation().displayState();
                // req 6: find a new task for the station
                nextEvent.getStation().pickTask(nextEvent.getEventStartTime());
                // and select the following task for the completed task's job.
                if(nextEvent.getTask().getJobType().getTaskIndex()<=nextEvent.getTask().getJobType().getTasks().size()){
                    TaskScheduler.findSuitableStation(nextEvent.getTask().getJobType().getTasks().get(nextEvent.getTask().getJobType().getTaskIndex()), nextEvent.getEventStartTime());
                }
                // find a station for that task
                // if there is no task for mentioned job add 1 to finishedJob and
                // req 5: display the resulting state of the job. Like "it ended 1 minute before the deadline or after 2 minutes.."
            }

            // create new events on station subClasses with pickTask method.
            for(Station s : Main.stationsTypes) {
                if (s.getMaxCapacity() != s.getCurrentTaskNo()) {
                    for(Task t:s.getTargetTasks()){
                        System.out.println("++++");
                        System.out.println("++"+t.getTaskTypeID()+" ");
                    }
                    if (!s.getTargetTasks().isEmpty()) {
                        System.out.println("...");
                        s.pickTask(nextEvent.getEventStartTime()); // This will create new Task events.
                    }
                }
            }

            // sort  the event list again
            Collections.sort(Main.events, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    // İki double değeri karşılaştırıyoruz
                    return Double.compare(e1.getEventStartTime(), e2.getEventStartTime());
                }
            });

            /*for(Event e : Main.events){
                System.out.println(e.getEventType() + " " + e.getEventStartTime());
            }*/

            // rearrange the nextEvent
            eventCount++;


            // if the finishedJobs equal to Main.jobs.size(), theAllJobsFinished will false.
            if(finishedJobs== Main.jobs.size()){
                allJobsNotFinished=false;
            }

        }while(allJobsNotFinished && eventCount < Main.events.size());
    }
}
