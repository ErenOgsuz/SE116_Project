import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JobReader {
    public static void readJobs(){
        ArrayList<Job> jobs = new ArrayList<Job>();
        ArrayList<Task> jobTasks = new ArrayList<Task>();

        String workFlowFilePath = "WorkFlow.txt"; // The path of work flow file

        int lineCount = 1;  // indicates the line read.
        int jobCount = 0;
        boolean isThereJob = false;
        boolean isThereTask = false;

        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath));) {
            String line = "";

            while (true) {
                line = br.readLine();

                if(line.contains("JOBTYPES")){
                    int jobTypesIndex = line.indexOf("JOBTYPES");
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, jobTypesIndex, openingParenthesisIndex,0);
                    lineCount++;
                    continue;
                } else if (line.contains("J")) {
                    isThereJob = true;
                    String[] parts =line.split(" ");

                    for(int i = 0; i< parts.length; i++){
                        parts[i] = parts[i].trim();
                        if(parts[i].contains(")")){
                            parts[i] = parts[i].substring(0,parts[i].length()-1);
                        }else if(parts[i].contains("(")){
                            parts[i] = parts[i].substring(1);
                        }
                    }

                    for(int i=0; i< parts.length;i++) {
                        if (parts[i].contains("J")) {
                            if (parts[i].charAt(0) == 'J') {
                                for(String part : parts){
                                    if(part.contains("T")){
                                        isThereTask = true;
                                        boolean taskExist = false;
                                        Task taskToAdd = new Task();
                                        for(Task task : Main.taskTypes){
                                            if(task.getTaskTypeID().equals(part)){
                                                taskExist = true;
                                                taskToAdd = task;
                                            }
                                        }
                                        if(taskExist){
                                            jobTasks.add(taskToAdd);
                                        }else{
                                            nonDeclaredTask(lineCount,part);
                                        }
                                    }
                                }
                                if(isThereTask){
                                    boolean jobExist = false;
                                    for(Job job : jobs){
                                        if(job.getJobID()==parts[i]){
                                            jobExist = true;
                                        }
                                    }
                                    if(!jobExist){
                                        jobs.add(new Job(parts[i],new ArrayList<Task>(jobTasks)));
                                        jobTasks.clear();
                                        jobCount++;
                                    }else {
                                        alreadyDeclaredJob(lineCount,parts[i]);
                                    }
                                }else{
                                    noTask(lineCount);
                                }
                            }else{
                                System.out.println("JobID is not written correctly!");
                            }
                        }
                    }
                }

                lineCount++;

                if (line.contains("STATIONS")) {
                    break;
                }
            }



            for (Job job : jobs) {
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
}
