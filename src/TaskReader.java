import java.io.BufferedReader;
import java.io.FileReader;

public class TaskReader {
    public static void readTasks(String filePath) throws Exception{

        int lineCount = 0;  // indicates the line read.
        int taskCount = 0;

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";
        String allLine = "";

        while (true) {
            line = br.readLine();
            lineCount++;

            if (line.contains("JOBTYPES")) {
                break;
            }

            String[] parts = line.split(" ");

            if (parts[parts.length - 1].endsWith(")")) {
                parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].length() - 1);
            }

            allLine = allLine.concat(line);

            for (int i = 1; i < parts.length; i++) {
                if (!parts[i].contains("JOBTYPES")) {
                    if (!parts[i].isEmpty()) {
                        if (!parts[i].contains("TASKTYPES")) {
                            if (parts[i].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
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

                            } else if (parts[i].matches("\\d+(\\.\\d+)?")) {
                                if (taskCount > 0) {
                                    if (Main.taskTypes.get(taskCount - 1).getSize() == 0.0) {
                                        if (Double.parseDouble(parts[i]) >= 0.0) {
                                            Main.taskTypes.get(taskCount - 1).setSize(Double.parseDouble(parts[i]));
                                        } else {
                                            incorrectDefaultSize(lineCount, parts[i]);
                                        }
                                    } else {
                                        noTask(lineCount);
                                    }
                                } else {
                                    noTask(lineCount);
                                }
                            } else {
                                incorrectTaskType(lineCount, parts[i]);
                            }
                        }
                    }
                } else {
                    break;
                }
            }
        }

        String[] parts = allLine.split(" ");
        allLine = "";

        for (String part : parts) {
            part = part.trim();
            allLine = allLine.concat(part);
        }
        if (!allLine.matches("^\\(.*")) {
            System.out.println(allLine);
            controlBracket("(",lineCount);
        } else if (!allLine.endsWith(")")) {
            controlBracket(")",lineCount);
        }

        for (int i = 0; i < Main.taskTypes.size(); i++) {
            System.out.println(Main.taskTypes.get(i).getTaskTypeID() + ", Size: " + Main.taskTypes.get(i).getSize());
        }

    }
    public static void controlBracket(String bracketType, int lineCount) throws  Exception {
        if(bracketType.equals("(")) {
            throw new Exception("Line" + ": " + lineCount + ": There is no '(' before the 'TASKTYPES' .");
        }else{
            throw new Exception("Line" + ": " + lineCount + ": There is no ')' after the last task type .");
        }
    }
    public static void incorrectTaskType(int lineCount, String task) throws Exception{
        throw new Exception("Line" + lineCount + ": " + task + " is an invalid type of taskTypeID.");
    }
    public static void incorrectDefaultSize(int lineCount, String task) throws Exception{
        throw new Exception("Line" + lineCount + ": " + task + " has an invalid default size.");
    }
    public static void noTask(int lineCount) throws Exception{
        throw new Exception("Line" + lineCount + ": There is no task for a default size.");
    }
    public static void alreadyDeclaredTaskType(int lineCount,String task) throws Exception{
        throw new Exception("Line"+ lineCount +": "+ task +" already declared.");
    }

}