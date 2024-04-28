import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JobReader {
    public static void readJobs(){
        ArrayList<JobType> jobs = new ArrayList<JobType>();
        ArrayList<Task> jobTasks = new ArrayList<Task>();

        String workFlowFilePath = "WorkFlow.txt"; // The path of work flow file

        int lineCount = 1;  // indicates the line read.
        boolean isThereJob = false; // checks if any job is given
        boolean isThereTask = false; // checks if any task is given for the job

        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath))) {
            String line = "";

            while (true) { // Read line by line
                line = br.readLine();

                if(line.contains("JOBTYPES")){
                    int jobTypesIndex = line.indexOf("JOBTYPES");
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, jobTypesIndex, openingParenthesisIndex,0);
                    lineCount++;
                    continue;
                } else if (line.contains("J")) { // If line has J in it, it will start to create a Job object
                    isThereJob = true;
                    String[] parts =line.split(" ");

                    for(int i = 0; i< parts.length; i++){ // Clean the Strings from empty space and parentheses
                        parts[i] = parts[i].trim();
                        if(parts[i].contains(")")){
                            parts[i] = parts[i].substring(0,parts[i].length()-1);
                        }
                        if(parts[i].contains("(")){
                            parts[i] = parts[i].substring(1);
                        }
                    }
                    // Looks for every String in the line
                    for(int i=0; i< parts.length;i++) {
                        if (parts[i].contains("J")) { // Is job given
                            if (parts[i].charAt(0) == 'J') { // Is jobID written correctly
                                for(int j = 0; j<parts.length; j++){
                                    if(parts[j].contains("T")){ // Looks for the tasks
                                        isThereTask = true;
                                        boolean taskExist = false;
                                        Task taskToAdd = new Task();
                                        for(Task task : Main.taskTypes){ // Checks if task exist
                                            if(task.getTaskTypeID().equals(parts[j])){
                                                taskExist = true;

                                                if(j < parts.length-1 && !parts[j+1].contains("T")){
                                                    if(task.getSize() == 0.0){ // Is task size given before
                                                        if(Double.parseDouble(parts[j+1]) == 0.0) {
                                                            noDefaultSize(lineCount, task.getTaskTypeID());
                                                        }else if(Double.parseDouble(parts[j+1]) >= 0){
                                                            taskToAdd = new Task(parts[j]);
                                                            taskToAdd.setSize(Double.parseDouble(parts[j+1]));
                                                        }else{
                                                            incorrectDefaultSize(lineCount);
                                                        }
                                                    }else if(task.getSize() != Double.parseDouble(parts[j+1])) { // Is task size and new task same
                                                        if(Double.parseDouble(parts[j+1]) >= 0){
                                                            taskToAdd = new Task(parts[j]);
                                                            taskToAdd.setSize(Double.parseDouble(parts[j + 1]));
                                                        }else if(Double.parseDouble(parts[j+1]) == 0.0){
                                                            taskToAdd = task;
                                                        }else if(Double.parseDouble(parts[j+1]) < 0){
                                                            incorrectDefaultSize(lineCount);
                                                        }
                                                    }else{ // Add task to Job
                                                        taskToAdd = task;
                                                    }
                                                }else{
                                                    if(task.getSize() == 0.0){
                                                        noDefaultSize(lineCount, task.getTaskTypeID());
                                                    }else{
                                                        taskToAdd = task;
                                                    }
                                                }
                                            }
                                        }
                                        if(taskExist){
                                            jobTasks.add(taskToAdd);
                                        }else{
                                            nonDeclaredTask(lineCount,parts[j]);
                                        }
                                    }
                                }
                                if(isThereTask){ // If there is a task add it to Job
                                    boolean jobExist = false;
                                    for(JobType job : Main.jobTypes){ // Is this jobID given before
                                        if(job.getJobID().equals(parts[i])){
                                            jobExist = true;
                                            break;
                                        }
                                    }
                                    if(!jobExist){
                                        Main.jobTypes.add(new JobType(parts[i],new ArrayList<Task>(jobTasks)));
                                        jobTasks.clear();
                                    }else {
                                        alreadyDeclaredJob(lineCount,parts[i]);
                                    }
                                }else{
                                    noTask(lineCount); // Throws exception if there is no Task
                                }
                                isThereTask = false;
                            }else{
                                jobIDisWrong(lineCount); // Throws exception if JobID is not right
                            }
                        }
                    }
                }

                lineCount++;

                if (line.contains("STATIONS")) {
                    break;
                }
            }

            if(!isThereJob){ // If no job given in JOBTYPE list
                noJob();
            }

            for (JobType job : Main.jobTypes) { // Prints jobs with their tasks
                System.out.print(job.getJobID() + " ");
                for(Task task : job.getTasks()){
                    System.out.print(task.getTaskTypeID() + " ");
                }
                System.out.println();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // All the Exceptions written below
    public static void controlBracket(int lineCount,int controlIndex,int bracetIndex,int bracketType) throws  Exception {
        if (bracketType == 0) {
            if (bracetIndex > controlIndex) {
                throw new Exception("Line " + lineCount + ": There is no '(' at correct place. ");
            } else if (bracetIndex == -1) {
                throw new Exception("Line " + lineCount + ": There is no '(' at correct place. ");
            }

        }
    }
    public static void alreadyDeclaredJob(int lineCount, String jobID) throws Exception{
        throw new Exception("Line " + lineCount + ": " + jobID + ": already declared.");
    }
    public static void nonDeclaredTask(int lineCount, String taskID) throws Exception{
        throw new Exception("Line " + lineCount + ": " + taskID + " is not one of the declared task types.");
    }
    public static void noDefaultSize(int lineCount, String taskID) throws Exception{
        throw new Exception("Line " + lineCount + ": " + taskID + " has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job.");
    }
    public static void incorrectDefaultSize(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": There is an invalid default size.");
    }
    public static void noTask(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": There is no task given for job.");
    }
    public static void noJob() throws Exception{
        throw new Exception("No job is given for JOBTYPE.");
    }
    public static void jobIDisWrong(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": JobID is written wrong, write \"J\" first then give number");
    }
}
