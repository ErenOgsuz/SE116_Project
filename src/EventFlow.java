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
        Job job;
        JobType jobType;
        Task task;

        // create the events to trigger start the jobs..
        for(Job job1 :Main.jobs){
            job=Main.jobs.getFirst();
            jobStartTime=job.getStartTime();
            jobType=job.getJobTypeId();
            Main.events.add(new Event(job,jobType,jobStartTime));
        }

        Collections.sort(Main.events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // İki double değeri karşılaştırıyoruz
                return Double.compare(e1.getEventStartTime(), e2.getEventStartTime());
            }
        });

        Event nextEvent=Main.events.getFirst();
        int eventCount=1;

        do{
            if(nextEvent.getEventType().equals("JobStarting")) {
                // select the first task's station..
                TaskScheduler.findSuitableStation(nextEvent.getJobType().getTasks().getFirst());
                // the station's currentTask, desks for that event rearrange -> and the executing task will create a new event
            }
            if(nextEvent.getEventType().equals("TaskStarting")) {
                // give required info about first event at the list
                // the task's state and the executing task for the job of the task will rearrange
                // the station's currentTask, desks for that event rearrange -> and the executing task will create a new event
                //req 6: display the state of the station
                // req 6: find a new task for the station
                // and select the following task for the completed task's job.
                // find a station for that task ?
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
            // rearrange the nextEvent
            eventCount++;
            nextEvent=Main.events.get(eventCount);

            // if the finishedJobs equal to Main.jobs.size(), theAllJobsFinished will false.
            if(finishedJobs== Main.jobs.size()){
                allJobsNotFinished=false;
            }

        }while(allJobsNotFinished);

        // display all jobs states: req 9
    }
}
