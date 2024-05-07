import java.util.Collections;
import java.util.Comparator;

public class EventFlow {
    public static void eventFlow(){
        boolean allJobsNotFinished=true;
        int finishedJobs=0;
        Station station;
        double starTime;
        double deadLine;
        double jobStartTime;
        JobType jobType;
        Task task;

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

            if(nextEvent.getEventType().equals("JobStarting")) {
                System.out.println(nextEvent.getJob().getJobId() + " has started!");
                System.out.println("First task getting started");
                // select the first task's station..
                TaskScheduler.findSuitableStation(nextEvent.getJobType().getTasks().getFirst()).pickTask(nextEvent.getEventStartTime());
                // the station's currentTask, desks for that event rearrange -> and the executing task will create a new event
                /*for(Station s : Main.stationsTypes){
                    if(!s.getTargetTasks().isEmpty()){
                        s.pickTask(nextEvent.getEventStartTime()); // This will create new Task events.
                    }
                }*/

            }else if(nextEvent.getEventType().equals("JobFinished")) {
                System.out.println(nextEvent.getJob().getJobId() + " has finished!");
                finishedJobs++;
            }else if(nextEvent.getEventType().equals("TaskStarting")) {
                System.out.println(nextEvent.getTask().getTaskTypeID() + " of " + nextEvent.getTask().getJob().getJobId() + " has started!");
                nextEvent.getTask().setStateExecuting();
                // give required info about first event at the list
                // req 6: display the state of the station
                nextEvent.getTask().getStation().displayState();
            }else if(nextEvent.getEventType().equals("TaskFinished")){
                System.out.println(nextEvent.getTask().getTaskTypeID() + " has finished!");
                // the task's state and the executing task for the job of the task will rearrange
                nextEvent.getTask().setStateDone();
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
                // find a station for that task
                // if there is no task for mentioned job add 1 to finishedJob and
                // req 5: display the resulting state of the job. Like "it ended 1 minute before the deadline or after 2 minutes.."
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

        // display all jobs states: req 9
    }
}
