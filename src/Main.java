import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    protected static ArrayList<Task> taskTypes =new ArrayList<Task>();
    protected static ArrayList<JobType> jobTypes =new ArrayList<JobType>();
    protected static ArrayList<Station> stationsTypes = new ArrayList<Station>();
    protected static ArrayList<Job> jobs = new ArrayList<Job>();
    protected static ArrayList<Event> events= new ArrayList<Event>();

    public static void main(String[] args) {
        String jobFilePath = "JobFile.txt"; // The path of Job file
        String workFlowFilePath = "WorkFlow.txt"; // The path of work flow file

        Path jobFilePathObject = Paths.get(jobFilePath);
        Path workFlowFilePathObject = Paths.get(workFlowFilePath);

        if (Files.exists(jobFilePathObject)) {
            System.out.println("The job file exist.");
            if (Files.isReadable(jobFilePathObject)) {
                System.out.println("The job file accessible.");
            } else {
                System.out.println("The job file does not accessible.");
            }
        } else {
            System.out.println("The job file does not exist.");
        }

        if (Files.exists(workFlowFilePathObject)) {
            System.out.println("The WorkFlow file exist.");
            if (Files.isReadable(workFlowFilePathObject)) {
                System.out.println("The WorkFlow file accessible.");
            } else {
                System.out.println("The WorkFlow file does not accessible.");
            }
        } else {
            System.out.println("The WorkFlow file does not exist.");
        }

        TaskReader.readTasks();
        JobReader.readJobs();
        StationReader.readStations();
        JobFileReader.readJobsFromFile();
        //EventFlow.eventFlow();
        //EventFlow.eventFlow(); //Commented for now
        /*JobType J1 = new JobType("J1", taskTypes);
        JobType J2 = new JobType("J2", taskTypes);
        JobType J3 = new JobType("J3", taskTypes);

        jobs.add(new Job("Job1", J1, 1, 4));
        jobs.add(new Job("Job1", J2, 5, 5));
        jobs.add(new Job("Job1", J3, 10, 6));

        for (Job j: jobs) {
            if (j.getState().equals("Waiting..")) {
                System.out.println("Job " + j.getJobId() + " is waiting to be executed.");
                if (jobs.indexOf(j) - 1 >= 0) System.out.println("Previous job " + jobs.get(jobs.indexOf(j) - 1).getJobId() + " is waiting to be executed.");
            }
            else if (j.getState().equals("Executing..")) {
                System.out.println("Job " + j.getJobId() + "is being executed.");
            }
            System.out.println("Job ID: " + j.getJobId() + ", Deadline: " + j.getDeadline());
            System.out.println("Deadline deviation: " + j.getDelayTime());
            j.getExecutingTask().setStateDone();
            System.out.println(j.getState());
        }*/
    }
}