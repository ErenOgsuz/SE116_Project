import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class StationReader {
    public static void readStations(String filePath) throws Exception {
        ArrayList<Task> stationTasks = new ArrayList<Task>();

        int lineCount = 1; //The line that has been read.
        boolean isThereTask = false;
        boolean multiflag = false;
        boolean fifoflag = false;
        String allLine = "";

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = "";

        do {
            line = br.readLine();
            if (!line.contains("STATIONS")) {
                lineCount++;
            } else {
                break;
            }
        } while (true);

        while (true) {
            line = br.readLine();

            if (line == null) {
                // End of file reached, exit the loop
                break;
            }

            String[] parts = line.split(" ");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
                allLine = allLine.concat(parts[i]);
            }

            if (line.contains("STATIONS")) {
                allLine = allLine.concat(line);
                lineCount++;
                continue;
            }

            if (parts[0].matches("^\\([a-zA-Z][a-zA-Z0-9_]*$")) {

                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                    if (parts[i].contains(")")) {
                        parts[i] = parts[i].substring(0, parts[i].length() - 1); //To distinguish station from ')'
                    }
                    if (parts[i].contains("(")) {
                        parts[i] = parts[i].substring(1); //To distinguish station from '('
                    }

                    //System.out.println(parts[i]);
                }

                if (line.contains("N") || line.contains("Y")) {
                    if (parts.length >= 2) {
                        String flag = parts[2].toUpperCase();
                        if (flag.equals("Y")) {
                            multiflag = true;
                        } else if (flag.equals("N")) {
                            multiflag = false;
                        }
                    }
                }

                if (line.contains("N") || line.contains("Y")) {
                    if (parts.length >= 3) {
                        String flag = parts[3].toUpperCase();
                        if (flag.equals("Y")) {
                            fifoflag = true;
                        } else if (flag.equals("N")) {
                            fifoflag = false;
                        }
                    }
                }

                if (parts[0].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                    for (int j = 4; j < parts.length; j++) {
                        if (parts[j].matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
                            isThereTask = true;
                            boolean taskExist = false;
                            Task taskToAdd = new Task();
                            for (Task task : Main.taskTypes) { //Main -> Main class
                                if (task.getTaskTypeID().equals(parts[j])) {
                                    taskExist = true;
                                    if (j < parts.length - 1 && !parts[j + 1].contains("T")) {
                                        taskToAdd = new Task(task.getTaskTypeID(), task.getSize());
                                        if (Double.parseDouble(parts[j + 1]) < 0) {
                                            invalidSpeedSize(lineCount);
                                        } else {
                                            taskToAdd.setSpeed(Double.parseDouble(parts[j + 1]));
                                            if (j + 1 < parts.length - 1 && !parts[j + 2].contains("T")) {
                                                taskToAdd = new Task(task.getTaskTypeID(), task.getSize());
                                                if (Double.parseDouble(parts[j + 2]) < 0 || Double.parseDouble(parts[j + 2]) > 1) {
                                                    invalidPlusMinus(lineCount);
                                                } else {
                                                    taskToAdd.setPlusMinus(Double.parseDouble(parts[j + 2]));
                                                }
                                            }
                                        }
                                    } else {
                                        taskToAdd = new Task(task.getTaskTypeID(), task.getSize());
                                        System.err.println(taskToAdd.getTaskTypeID() + "'s speed is not declared. Setting speed to 1");
                                    }
                                }
                            }
                            if (taskExist) {
                                stationTasks.add(taskToAdd);
                            } else {
                                nonDeclaredTask(lineCount, parts[j]);
                            }
                        }
                    }
                    if (isThereTask) {
                        boolean stationExist = false;
                        for (Station station : Main.stationsTypes) {
                            if (station.getStationID().equals(parts[0])) {
                                stationExist = true;
                            }
                        }
                        if (!stationExist) {
                            if (multiflag && fifoflag) {
                                Main.stationsTypes.add(new FifoMultiStation(parts[0], new ArrayList<Task>(stationTasks), Integer.parseInt(parts[1])));
                                stationTasks.clear();
                            } else if (multiflag && !fifoflag) {
                                Main.stationsTypes.add(new EarlyJobMultiStation(parts[0], new ArrayList<Task>(stationTasks), Integer.parseInt(parts[1])));
                                stationTasks.clear();
                            } else if (!multiflag && fifoflag) {
                                Main.stationsTypes.add(new FifoOneStation(parts[0], new ArrayList<Task>(stationTasks)));
                                stationTasks.clear();
                            } else if (!multiflag && !fifoflag) {
                                Main.stationsTypes.add(new EarlyJobOneStation(parts[0], new ArrayList<Task>(stationTasks)));
                                stationTasks.clear();
                            }
                        } else {
                            alreadyDeclaredStation(lineCount, parts[0]);
                        }
                    } else {
                        noTask(lineCount);
                    }
                    isThereTask = false;
                }
            }else if (parts[0].matches("\\(\\d[a-zA-Z0-9_]*$")) {
                parts[0] = parts[0].substring(1);
                stationIDisWrong(lineCount, parts[0]);
            }else {
                stationHasNoBracket(lineCount, parts[0]);
            }

            lineCount++;

        }

        //Controls if there is any left out Task in taskTypes list
        boolean taskExists = false;
        ArrayList<String> tasks = new ArrayList<String>();

        for(Task t: Main.taskTypes){
            for(Station s: Main.stationsTypes){
                for(Task t2: s.getTasksCanDo()){
                    if(t.getTaskTypeID().equals(t2.getTaskTypeID())){
                        taskExists = true;
                    }else{
                        continue;
                    }
                }
            }
            if (!taskExists){
                tasks.add(t.getTaskTypeID());
            }
            taskExists=false;
        }
        taskNotInAnyStation(tasks);

        //Controls if there is any left out Job Task
        boolean jobTaskExists = false;
        ArrayList<String> jobTasks = new ArrayList<String>();

        for (JobType j : Main.jobTypes) {
            for (Task t : j.getTasks()) {
                for (Station s : Main.stationsTypes) {
                    for (Task t2 : s.getTasksCanDo()) {
                        if (t.getTaskTypeID().equals(t2.getTaskTypeID())) {
                            jobTaskExists = true;
                        } else {
                            continue;
                        }
                    }
                }
                if (!jobTaskExists) {
                    jobTasks.add(t.getTaskTypeID());
                }
                jobTaskExists = false;
            }
        }
        if(!jobTasks.isEmpty()){
            jobTaskNotInAnyStation(jobTasks);
        }

        if (!allLine.startsWith("(")) {
            System.out.println(allLine);
            controlBracket("(", lineCount);
        }
        if (!allLine.endsWith("))")) {
            controlBracket(")", lineCount);
        }

        for (Station station : Main.stationsTypes) {
            System.out.print(station.getStationID());
            for (Task task : station.getTasks()) {
                System.out.print(", " + task.getTaskTypeID() + " Plus/Minus:" + task.getPlusMinus());
            }
            System.out.println();
        }
        //Station sr = new Station();
        //Task t = new Task();
            /*if (!Main.stationsTypes.contains(Main.taskTypes.get(0))) {
                System.out.println("");
                taskNotInStation(1, sr.getStationID(), Main.taskTypes.get(0));
            }*/
    }

    public static void controlBracket(String bracketType, int lineCount) throws Exception {
        if (bracketType.equals("(")) {
            throw new Exception("There is no '(' at correct place. ");
        } else if (bracketType.equals(")")) {
            throw new Exception("There is no ')' at correct place. ");
        }
    }

    public static void alreadyDeclaredStation(int lineCount, String stationID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + stationID + ": already declared.");
    }

    public static void invalidSpeedSize(int lineCount) throws Exception {
        throw new Exception("Line " + lineCount + ": There is an invalid default size.");
    }

    public static void nonDeclaredTask(int lineCount, String taskID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + taskID + " is not one of the declared task types.");
    }

    public static void noTask(int lineCount) throws Exception {
        throw new Exception("Line " + lineCount + ": There is no task given for job.");
    }

    public static void stationIDisWrong(int lineCount, String s) throws Exception {
        throw new Exception("Line " + lineCount + ": STATION " + s + " is written wrong, write a letter first then give number");
    }

    public static void stationHasNoBracket(int lineCount, String s) throws Exception {
        throw new Exception("Line " + lineCount + ": STATION " + s + " is written wrong, write \"(\" first then give StationID");
    }

    public static void invalidPlusMinus(int lineCount) throws Exception {
        throw new Exception("Line " + lineCount + ": the PlusMinus rate is invalid.");
    }

    public static void taskNotInAnyStation(ArrayList<String> tasks){
        if(!tasks.isEmpty()) {
            String tasksString = String.join(", ", tasks);
            System.err.println(tasksString + " not executed in any STATIONS even though they are listed as possible task types");
        }
    }

    public static void jobTaskNotInAnyStation(ArrayList<String> tasks) throws Exception {
        String tasksString = String.join(", ", tasks);
        throw new Exception("There are no STATIONs which execute " + tasksString + " however, " + tasksString + " is/are a part of some job type");
    }
}