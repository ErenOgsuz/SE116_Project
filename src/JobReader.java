import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class JobReader {
    public static void readJobs(String filePath) throws Exception {
        ArrayList<JobType> jobs = new ArrayList<JobType>();
        ArrayList<Task> jobTasks = new ArrayList<Task>();

        int lineCount = 1;  // indicates the line read.
        String allLine = "";
        boolean isThereJob = false; // checks if any job is given
        boolean isThereTask = false; // checks if any task is given for the job

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";

        do {
            line = br.readLine();
            if (!line.contains("JOBTYPES")) {
                lineCount++;
            } else {
                break;
            }
        } while (true);

        while (true) { // Read line by line
            line = br.readLine();

            if (line.contains("STATIONS")) {
                break;
            }

            String[] parts = line.split(" ");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
                allLine = allLine.concat(parts[i]);
            }

            if (line.contains("JOBTYPES")) {
                if (parts.length == 1) {
                    allLine = allLine.concat(line);
                    lineCount++;
                    continue;
                }
            } else if (parts[0].matches("^\\([a-zA-Z][a-zA-Z0-9_]*$")) { // If line has J in it, it will start to create a Job object
                isThereJob = true;

                for (int i = 0; i < parts.length; i++) { // Clean the Strings from empty space and parentheses
                    parts[i] = parts[i].trim();
                    if (parts[i].contains(")")) {
                        parts[i] = parts[i].substring(0, parts[i].length() - 1);
                    }
                    if (parts[i].contains("(")) {
                        parts[i] = parts[i].substring(1);
                    }
                }

                // Looks for every String in the line
                if (parts[0].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) { // Is job given
                    for (int j = 1; j < parts.length; j++) {
                        if (parts[j].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) { // Looks for the tasks
                            isThereTask = true;
                            boolean taskExist = false;
                            Task taskToAdd = new Task();
                            for (Task task : Main.taskTypes) { // Checks if task exist
                                if (task.getTaskTypeID().equals(parts[j])) {
                                    taskExist = true;

                                    if (j < parts.length - 1 && !parts[j + 1].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                                        if (task.getSize() == 0.0) { // Is task size given before
                                            if (Double.parseDouble(parts[j + 1]) == 0.0) {
                                                noDefaultSize(lineCount, task.getTaskTypeID());
                                            } else if (Double.parseDouble(parts[j + 1]) >= 0) {
                                                taskToAdd = new Task(parts[j]);
                                                taskToAdd.setSize(Double.parseDouble(parts[j + 1]));
                                            } else {
                                                incorrectDefaultSize(lineCount, parts[j + 1]);
                                            }
                                        } else if (task.getSize() != Double.parseDouble(parts[j + 1])) { // Is task size and new task same
                                            if (Double.parseDouble(parts[j + 1]) >= 0) {
                                                taskToAdd = new Task(parts[j]);
                                                taskToAdd.setSize(Double.parseDouble(parts[j + 1]));
                                            } else if (Double.parseDouble(parts[j + 1]) == 0.0) {
                                                taskToAdd = task;
                                            } else if (Double.parseDouble(parts[j + 1]) < 0) {
                                                incorrectDefaultSize(lineCount, parts[j + 1]);
                                            }
                                        } else { // Add task to Job
                                            taskToAdd = task;
                                        }
                                    } else {
                                        if (task.getSize() == 0.0) {
                                            noDefaultSize(lineCount, task.getTaskTypeID());
                                        } else {
                                            taskToAdd = task;
                                        }
                                    }
                                }
                            }
                            if (taskExist) {
                                jobTasks.add(taskToAdd);
                            } else {
                                nonDeclaredTask(lineCount, parts[j]);
                            }
                        }
                    }
                    if (isThereTask) { // If there is a task add it to Job
                        boolean jobExist = false;
                        for (JobType job : Main.jobTypes) { // Is this jobID given before
                            if (job.getJobTypeID().equals(parts[0])) {
                                jobExist = true;
                                break;
                            }
                        }
                        if (!jobExist) {
                            Main.jobTypes.add(new JobType(parts[0], new ArrayList<Task>(jobTasks)));
                            jobTasks.clear();
                        } else {
                            alreadyDeclaredJob(lineCount, parts[0]);
                        }
                    } else {
                        noTask(lineCount); // Throws exception if there is no Task
                    }
                    isThereTask = false;
                }
            }else{
                parts[0] = parts[0].substring(1);
                jobIDisWrong(lineCount, parts[0]);
            }


            lineCount++;
        }

        if (!isThereJob) { // If no job given in JOBTYPE list
            noJob();
        }

        if (!allLine.startsWith("(")) {
            System.out.println(allLine);
            controlBracket("(");
        }
        if (!allLine.endsWith("))")) {
            controlBracket(")");
        }

        for (JobType job : Main.jobTypes) { // Prints jobs with their tasks
            System.out.print(job.getJobTypeID());
            for (Task task : job.getTasks()) {
                System.out.print(", " + task.getTaskTypeID() + " Size:" + task.getSize());
            }
            System.out.println();
        }

    }

    // All the Exceptions written below
    public static void controlBracket(String bracketType) throws Exception {

        if (bracketType.equals("(")) {
            throw new Exception("There is no '(' at correct place. ");
        } else if (bracketType.equals(")")) {
            throw new Exception("There is no ')' at correct place. ");
        }
    }

    public static void alreadyDeclaredJob(int lineCount, String jobID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + jobID + ": already declared.");
    }

    public static void nonDeclaredTask(int lineCount, String taskID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + taskID + " is not one of the declared task types.");
    }

    public static void noDefaultSize(int lineCount, String taskID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + taskID + " has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job.");
    }

    public static void incorrectDefaultSize(int lineCount, String part) throws Exception {
        throw new Exception("Line " + lineCount + ": " + part + " is an invalid default size.");
    }

    public static void noTask(int lineCount) throws Exception {
        throw new Exception("Line " + lineCount + ": There is no task given for job.");
    }

    public static void noJob() throws Exception {
        throw new Exception("No job is given for JOBTYPE.");
    }

    public static void jobIDisWrong(int lineCount, String s) throws Exception {
        throw new Exception("Line " + lineCount + ": JOBTYPE " + s + " is written wrong, write a letter first");
    }
}