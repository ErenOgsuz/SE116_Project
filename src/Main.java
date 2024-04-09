import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

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

        int lineCount = 0;  // indicates the line read.
        int taskCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath))) {
            String line = "";

            ArrayList<Task> taskTypes =new ArrayList<Task>();

            while (true) {
                line = br.readLine();
                lineCount++;

                String[] parts =line.split(" ");
                if(parts[parts.length-1].contains(")")){
                    parts[parts.length-1] = parts[parts.length-1].substring(0,parts[parts.length-1].length()-1);
                }

                for(int i=1; i< parts.length;i++) {
                    if (!parts[i].contains("J")) {
                        if (parts[i].contains("T")) {
                            if (parts[i].charAt(0) == 'T') {
                                    taskTypes.add(new Task(parts[i]));
                                    taskCount++;
                            }else{
                                incorrectTaskType(lineCount);
                            }
                        }else{
                           if(taskCount > 0){
                                if (taskTypes.get(taskCount - 1).getTaskTypeID().equals(parts[i-1])) {
                                    if (Double.parseDouble(parts[i]) >= 0.0) {
                                        taskTypes.get(taskCount - 1).setSize(Double.parseDouble(parts[i]));
                                    } else {
                                        incorrectDefaultSize(lineCount);
                                    }
                                }else{
                                    noTask(lineCount);
                                }
                            }else{
                                noTask(lineCount);
                            }
                        }
                    } else {
                        break;
                    }
                }

                int taskTypesIndex = line.indexOf("TASKTYPES");

                if (line.contains("TASKTYPES")) {
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, taskTypesIndex, openingParenthesisIndex,0);
                }

                if (line.contains("J")) {
                    break;
                }

            }
            for (int i = 0; i < taskTypes.size(); i++) {
                System.out.println(taskTypes.get(i).getTaskTypeID()+ " "+taskTypes.get(i).getSize() );
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

        }else{

        }
    }
    public static void incorrectTaskType(int lineCount) throws Exception{
        throw new Exception("Line" + lineCount + ": There is an invalid type of task.");
    }
    public static void incorrectDefaultSize(int lineCount) throws Exception{
        throw new Exception("Line" + lineCount + ": There is an invalid default size.");
    }
    public static void noTask(int lineCount) throws Exception{
        throw new Exception("Line" + lineCount + ": There is no task for a default size.");
    }

}