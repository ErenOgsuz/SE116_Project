import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobFileReader {
    public static void readJobsFromFile(String filepath) throws Exception{
        Set<String> jobIds = new HashSet<>();
        int lineNumber = 1;

        BufferedReader br = new BufferedReader(new FileReader(filepath));
            // Step 2: Read the file line by line
            String line;
            while ((line = br.readLine()) != null) {
                // Step 3: Split each line into tokens
                String[] tokens = line.split("\\s+");

                // Step 4: Check for syntax errors
                if (tokens.length != 4) {
                    syntaxError(lineNumber, tokens);
                }

                JobType jobType= new JobType();
                // Step 5: Extract job ID, job type ID, start time, and duration
                String jobId = tokens[0];
                boolean valid=false;
                for (JobType jobTypes: Main.jobTypes) {
                    if (jobTypes.getJobTypeID().equals(tokens[1])) {
                        ArrayList<Task> tasks =new ArrayList<Task>();
                        for(Task task1:jobTypes.getTasks() ){
                            tasks.add(new Task(task1.getTaskTypeID(),task1.getSize(),task1.getPlusMinus()));
                        }
                        jobType = new JobType(jobTypes.getJobTypeID(),tasks);
                        valid =true;
                    }
                }
                if(!valid){
                    invalidJobType(lineNumber,tokens[1]);
                }

                int startTime;
                int duration;

                // Step 6: Check for semantic errors
                if (!jobIds.add(jobId)) {
                    duplicateJobID(lineNumber,jobId);
                }

                startTime = Integer.parseInt(tokens[2]);

                if (startTime < 0) {
                    negativeStartTime(lineNumber,startTime);
                }

                duration = Integer.parseInt(tokens[3]);

                if (duration < 0) {
                    negativeDuration(lineNumber,duration);
                }

                // Step 7: Create a Job object
                Job job = new Job(jobId, jobType, startTime, duration);
                for(Task t : job.getJobType().getTasks()){
                    t.setJob(job);
                    t.setJobType(job.getJobType());
                }

                // Step 8: Calculate the job's deadline
                int deadline = startTime + duration;
                job.setDeadline(deadline);
                System.out.println("Job ID: " + jobId + ", JobType ID: " + jobType.getJobTypeID() + ", StartTime:"+startTime+ ", Deadline:" + deadline);

                // Step 9: Add the Job object to the list
                Main.jobs.add(job);

                lineNumber++;
            }
            System.out.println(".................................................\n");
    }

    public static void syntaxError(int lineCount, String[] tokens) throws Exception{
        throw new Exception("Syntax error on line " +  lineCount + ": expected 4 values, found " +  tokens.length);
    }
    public static void duplicateJobID(int lineCount, String jobId) throws Exception{
        throw new Exception("Semantic error on line " +  lineCount + ": duplicate job ID " +  jobId);
    }
    public static void negativeStartTime(int lineCount, int startTime) throws Exception{
        throw new Exception("Semantic error on line " +  lineCount + ": negative start time " +  startTime);
    }
    public static void negativeDuration(int lineCount, int duration) throws Exception{
        throw new Exception("Semantic error on line " +  lineCount + ": negative duration " +  duration);
    }

    public static void invalidJobType(int lineCount, String jobType) throws Exception{
        throw new Exception("Line: "+lineCount+" The "+ jobType+" is not a valid name.");
    }
}
