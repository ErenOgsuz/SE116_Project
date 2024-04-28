import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobFileReader {
    public static List<Job> readJobsFromFile(String filename) {
        List<Job> jobList = new ArrayList<>();
        Set<String> jobIds = new HashSet<>();
        int lineNumber = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Step 2: Read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // Step 3: Split each line into tokens
                String[] tokens = line.split("\\s+");

                // Step 4: Check for syntax errors
                if (tokens.length != 4) {
                    System.err.printf("Syntax error on line %d: expected 4 values, found %d%n", lineNumber, tokens.length);
                    continue;
                }

                // Step 5: Extract job ID, job type ID, start time, and duration
                String jobId = tokens[0];
                String jobTypeId = tokens[1];
                int startTime;
                int duration;

                // Step 6: Check for semantic errors
                if (!jobIds.add(jobId)) {
                    System.err.printf("Semantic error on line %d: duplicate job ID '%s'%n", lineNumber, jobId);
                    continue;
                }

                try {
                    startTime = Integer.parseInt(tokens[2]);
                } catch (NumberFormatException e) {
                    System.err.printf("Semantic error on line %d: invalid start time '%s'%n", lineNumber, tokens[2]);
                    continue;
                }

                if (startTime < 0) {
                    System.err.printf("Semantic error on line %d: negative start time '%d'%n", lineNumber, startTime);
                    continue;
                }

                try {
                    duration = Integer.parseInt(tokens[3]);
                } catch (NumberFormatException e) {
                    System.err.printf("Semantic error on line %d: invalid duration '%s'%n", lineNumber, tokens[3]);
                    continue;
                }

                if (duration < 0) {
                    System.err.printf("Semantic error on line %d: negative duration '%d'%n", lineNumber, duration);
                    continue;
                }

                // Step 7: Create a Job object
                Job job = new Job(jobId, jobTypeId, startTime, duration);

                // Step 8: Calculate the job's deadline
                int deadline = startTime + duration;
                System.out.println("Job ID: " + jobId + ", Deadline: " + deadline);

                // Step 9: Add the Job object to the list
                jobList.add(job);

                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            // Handle the error appropriately
        }

        // Return the list of Job objects
        return jobList;
    }
}