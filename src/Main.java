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
        String jobFilePath;
        String workFlowFilePath;

        do {

            while (true) {
                System.out.print("Enter job file name: ");
                jobFilePath = sc.next(); // The path of Job file

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

            while (true) {
                System.out.print("Enter workflow file name: ");
                workFlowFilePath = sc.next(); // The path of work flow file

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

            try {
                System.out.println(".....................Tasks........................");
                TaskReader.readTasks(workFlowFilePath);
                System.out.println("....................JobTypes......................");
                JobReader.readJobs(workFlowFilePath);
                System.out.println("....................Stations......................");
                StationReader.readStations(workFlowFilePath);
                System.out.println("......................Jobs........................");
                JobFileReader.readJobsFromFile(jobFilePath);
                EventFlow.eventFlow();
                ReportGenerator.calculateAverageJobTardinessAndUtilization();

            }catch(Exception e){
                System.out.println("An Error Occured");
                taskTypes.clear();
                jobTypes.clear();
                stationsTypes.clear();
                jobs.clear();
                events.clear();
            }finally {
                System.out.println("Please write \"exit\" if you want to close program");
                System.out.println("If you do not want to exit write anything else");
            }

            String resume = sc.next();
            if(resume.equals("exit")){
                break;
            }else{
                continue;
            }

        }while(true);

    }
}