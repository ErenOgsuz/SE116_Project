import java.io.BufferedReader;
import java.io.FileReader;

public class TaskReader {
    public static void readTasks(){

        String workFlowFilePath = "WorkFlow.txt"; // The path of work flow file

        int lineCount = 0;  // indicates the line read.
        int taskCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath))) {
            String line = "";
            String allLine="";

            while (true) {
                line = br.readLine();
                lineCount++;

                if (line.contains("J")) {
                    break;
                }

                String[] parts =line.split(" ");

                if(parts[parts.length-1].endsWith(")")){
                    parts[parts.length-1] = parts[parts.length-1].substring(0,parts[parts.length-1].length()-1);
                }

                allLine = allLine.concat(line);

                for(int i=1; i< parts.length;i++) {
                    if (!parts[i].contains("J")) {
                        if(!parts[i].isEmpty()) {
                            if (!parts[i].contains("TASKTYPES")) {
                                if (parts[i].contains("T")) {
                                    if (parts[i].matches("^T[a-zA-Z0-9_]+$")) {
                                        boolean exist = false;
                                        for (int k = 0; k < Main.taskTypes.size(); ++k) {
                                            if (Main.taskTypes.get(k).getTaskTypeID().equals(parts[i])) {
                                                exist = true;
                                            }
                                        }
                                        if (!exist) {
                                            Main.taskTypes.add(new Task(parts[i]));
                                            taskCount++;
                                        } else {
                                            alreadyDeclaredTaskType(lineCount, parts[i]);
                                        }
                                    } else {
                                        incorrectTaskType(lineCount);
                                    }
                                } else if (parts[i].matches("\\d+(\\.\\d+)?")) {
                                    if (taskCount > 0) {
                                        if (Main.taskTypes.get(taskCount - 1).getSize()==0.0) {
                                            if (Double.parseDouble(parts[i]) >= 0.0) {
                                                Main.taskTypes.get(taskCount - 1).setSize(Double.parseDouble(parts[i]));
                                            } else {
                                                incorrectDefaultSize(lineCount);
                                            }
                                        } else {
                                            noTask(lineCount);
                                        }
                                    } else {
                                        noTask(lineCount);
                                    }
                                } else {
                                    incorrectTaskType(lineCount);
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
            }

            String[] parts=allLine.split(" ");
            allLine= "";

            for(String part: parts){
                part=part.trim();
                allLine= allLine.concat(part);
            }
            if(!allLine.matches("^\\(.*")){
                System.out.println(allLine);
               controlBracket("(");
            }else if(!allLine.endsWith(")")){
                controlBracket(")");
            }

            for (int i = 0  ; i < Main.taskTypes.size(); i++) {
                System.out.println(Main.taskTypes.get(i).getTaskTypeID()+ " "+Main.taskTypes.get(i).getSize() );
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public static void controlBracket(String bracketType) throws  Exception {
        if(bracketType.equals("(")) {
            throw new Exception("Line" + ": " + bracketType + ": There is no '(' before the 'TASKTYPES' .");
        }else{
            throw new Exception("Line" + ": " + bracketType + ": There is no ')' after the last task type .");
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
    public static void alreadyDeclaredTaskType(int lineCount,String task) throws Exception{
        throw new Exception("Line"+ lineCount +": The "+ task +" already declared.");
    }

}
