import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    protected static ArrayList<Task> taskTypes =new ArrayList<Task>();

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

        //TaskReader.readTasks();
        //JobReader.readJobs();
        StationReader.readStations();
    }

}