import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobFileReader {
    public static List<Job> readJobsFromFile(String filename) {
        List<Job> jobList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Step 2: Read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // Step 3: Split each line into tokens
                String[] tokens = line.split("\\s+");

                // Step 4: Extract job ID, job type ID, start time, and duration
                if (tokens.length == 4) {
                    String jobId = tokens[0];
                    String jobTypeId = tokens[1];
                    int startTime = Integer.parseInt(tokens[2]);
                    int duration = Integer.parseInt(tokens[3]);

                    // Step 5: Create a Job object
                    Job job = new Job(jobId, jobTypeId, startTime, duration);

                    // Step 6: Calculate the job's deadline
                    int deadline = startTime + duration;
                    System.out.println("Job ID: " + jobId + ", Deadline: " + deadline);

                    // Step 7: Add the Job object to the list
                    jobList.add(job);

                } else {
                    System.err.println("Invalid line format: " + line);
                    // Handle the error appropriately
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            // Handle the error appropriately
        }

        // Return the list of Job objects
        return jobList;
    }

    public static void main(String[] args) {
        // Step 1: Open the job file for reading using BufferedReader
        String filename = "jobs.txt"; // Example filename, replace with your actual filename

        // Step 8: Call the readJobsFromFile method to get the list of Job objects
        List<Job> jobList = readJobsFromFile(filename);

        // Do something with the jobList, such as printing its size
        System.out.println("Number of jobs: " + jobList.size());
    }
}