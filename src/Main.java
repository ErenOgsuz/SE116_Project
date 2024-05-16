import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    protected static ArrayList<Task> taskTypes = new ArrayList<Task>();
    protected static ArrayList<JobType> jobTypes = new ArrayList<JobType>();
    protected static ArrayList<Station> stationsTypes = new ArrayList<Station>();
    protected static ArrayList<Job> jobs = new ArrayList<Job>();
    protected static ArrayList<Event> events = new ArrayList<Event>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("Enter job file name: ");
            String jobFilePath = sc.next(); // The path of Job file

            Path jobFilePathObject = Paths.get(jobFilePath);

            if (Files.exists(jobFilePathObject)) {
                System.out.println("The job file exist.");
                if (Files.isReadable(jobFilePathObject)) {
                    System.out.println("The job file accessible.");
                    break;
                } else {
                    System.out.println("The job file does not accessible.");
                }
            } else {
                System.out.println("The job file does not exist.");
            }
        }

        while(true){
            System.out.print("Enter workflow file name: ");
            String workFlowFilePath = sc.next(); // The path of work flow file

            Path workFlowFilePathObject = Paths.get(workFlowFilePath);

            if (Files.exists(workFlowFilePathObject)) {
                System.out.println("The WorkFlow file exist.");
                if (Files.isReadable(workFlowFilePathObject)) {
                    System.out.println("The WorkFlow file accessible.");
                    break;
                } else {
                    System.out.println("The WorkFlow file does not accessible.");
                }
            } else {
                System.out.println("The WorkFlow file does not exist.");
            }
        }

        TaskReader.readTasks();
        JobReader.readJobs();
        StationReader.readStations();
        JobFileReader.readJobsFromFile();
        EventFlow.eventFlow();
        ReportGenerator.calculateAverageJobTardinessAndUtilization();

    }
}