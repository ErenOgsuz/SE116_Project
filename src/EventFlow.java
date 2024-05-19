import java.util.Collections;
import java.util.Comparator;

public class EventFlow {
    public static void eventFlow(){
        boolean allJobsNotFinished=true;
        int finishedJobs=0;
        double currentTime=0.0;

        for(Job job: Main.jobs){
            // set the state of job
            job.setState(currentTime);
        }

        // create the events to trigger start the jobs..
        for(Job job :Main.jobs){
            //Job job1 = new Job(job.getJobId(),job.getJobType(),job.getStartTime(),job.getDuration());
            Main.events.add(new Event(job,job.getStartTime(),"JobStarting"));
        }

        Main.events.add(new Event(0,"Simulation Start"));

        Collections.sort(Main.events, new Comparator<Event>() {
            @Override
            public int compare(Event e1, Event e2) {
                // İki double değeri karşılaştırıyoruz
                return Double.compare(e1.getDeadLine(), e2.getDeadLine());
            }
        });

        Event nextEvent;
        int eventCount=0;

        do{
            System.out.println("...................Event Block....................");
            nextEvent=Main.events.get(eventCount);

            currentTime=nextEvent.getDeadLine();
            // user will know current time
            System.out.println("Time is: "+currentTime+ ", EventType: "+ nextEvent.getEventType());

            // if The nextEvent is triggering the job to start
            if(nextEvent.getEventType().equals("Simulation Start")){


            }else if(nextEvent.getEventType().equals("JobStarting")) {

                System.out.println(nextEvent.getJob().getJobId() + " has started!");
                System.out.println("First task of "+nextEvent.getJob().getJobId()+"("+nextEvent.getJobType().getJobTypeID()+")"+" getting started");

                // set the state of job
                nextEvent.getJob().setState(currentTime);

                System.out.println("The "+nextEvent.getJob().getJobType().getTasks().getFirst().getTaskTypeID()+" of "+nextEvent.getJob().getJobType().getTasks().getFirst().getJob().getJobId()+" ("+nextEvent.getJobType().getTasks().getFirst().getJobType().getJobTypeID()+") will find a new station.");
                // select the first task's station and the station's currentTask, desks for that event rearrange
                TaskScheduler.findSuitableStation(nextEvent.getJob().getJobType().getTasks().getFirst(), nextEvent.getDeadLine());


            // if The nextEvent is triggering the job to finish
            }else if(nextEvent.getEventType().equals("JobFinished")) {

                System.out.println(nextEvent.getJob().getJobId() + " has finished!");

                // set the state of job
                nextEvent.getJob().setState(currentTime);
                nextEvent.getJob().setFinishTime(currentTime);

                if(nextEvent.getJob().getDelayTime() > 0) {
                    System.out.println("Job ID: " + nextEvent.getJob().getJobId() + ", Tardiness: " + (nextEvent.getJob().getDelayTime()) + " time late");
                }else{
                    System.out.println("Job ID: " + nextEvent.getJob().getJobId() + ", Tardiness: " + (-1 * (nextEvent.getJob().getDelayTime())) + " time early");
                }

                finishedJobs++;

            //if the nextEvent is trigerring the task to start
            }else if(nextEvent.getEventType().equals("TaskStarting")) {

                System.out.println(nextEvent.getTask().getTaskTypeID() + " of " + nextEvent.getTask().getJob().getJobId() + " has started!");

                // set state of the task which is starting
                nextEvent.getTask().setStateExecuting();

             // if the nextEvent is trigerring the task to finish
            }else if(nextEvent.getEventType().equals("TaskFinished")){

                System.out.println(nextEvent.getTask().getTaskTypeID() +"("+nextEvent.getTask().getJobType().getJobTypeID()+"-"+nextEvent.getTask().getJob().getJobId()+")"+ " has finished!");

                // the task's state and the executing task for the job of the task will rearrange
                nextEvent.getTask().setStateDone();

                // set the current task index for the job which the task finished
                nextEvent.getTask().getJob().getJobType().increaseTaskIndex();

                // the station's currentTask, desks for that event rearrange -> and the executing task will create a new event
                nextEvent.getStation().getCurrentTasks().remove(nextEvent.getTask());
                nextEvent.getStation().setCurrentTaskNo(nextEvent.getStation().getCurrentTaskNo()-1);
                // req 6: display the state of the station
                nextEvent.getStation().displayState();

                // and select the following task for the completed task's job.
                if(nextEvent.getTask().getJobType().getTaskIndex()<nextEvent.getTask().getJobType().getTasks().size()){
                    TaskScheduler.findSuitableStation(nextEvent.getTask().getJobType().getTasks().get(nextEvent.getTask().getJobType().getTaskIndex()), nextEvent.getDeadLine());
                }else{
                    Main.events.add(new Event(nextEvent.getTask().getJob(),nextEvent.getDeadLine(),"JobFinished"));
                }
            }

            // create new events on station subClasses with pickTask method.
            for(Station s : Main.stationsTypes) {
                if (s.getMaxCapacity() != s.getCurrentTaskNo()) {
                    if (!s.getTargetTasks().isEmpty()) {
                        s.pickTask(currentTime); // This will create new Task events.
                    }
                }
            }

            // sort  the event list again
            Collections.sort(Main.events, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    // İki double değeri karşılaştırıyoruz
                    return Double.compare(e1.getDeadLine(), e2.getDeadLine());
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

            System.out.println("\nStates of Stations:");
            for(Station station1:Main.stationsTypes){
                station1.displayState();
            }

            System.out.println("..................................................\n");

        }while(allJobsNotFinished && eventCount < Main.events.size());
    }
}
