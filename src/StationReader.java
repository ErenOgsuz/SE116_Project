import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StationReader {
    public static void readStations() {
        ArrayList<Station> stations = new ArrayList<Station>();
        ArrayList<Task> stationTasks = new ArrayList<Task>();

        String workFlowFilePath = "WorkFlow.txt";

        int lineCount = 1; //The line that has been read.
        int stationCount = 0;
        boolean isThereStation = false;
        boolean isThereTask = false;
        boolean multiflag = false;
        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath));) {
            String line = "";
            while (true) {
                line = br.readLine();

                if (line.contains("STATIONS")){
                    int stationTypesIndex = line.indexOf("STATIONS");
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, stationTypesIndex, openingParenthesisIndex,0);
                    lineCount++;
                    continue;
                }
                else if (line.contains("MULTIFLAG")) {
                    String[] parts = line.split(" ");
                    if (parts.length >= 2) {
                        String flag = parts[2].trim().toUpperCase();
                        if (flag.equals("Y")) {
                            isMultipleTasks(multiflag, true);
                        } else if (flag.equals("N")) {
                            isMultipleTasks(multiflag, false);
                        }
                    }
                    lineCount++;
                    continue;
                }
                else if (line.contains("S")) {
                    isThereStation = true;
                    String[] parts =line.split(" ");

                    for (int i = 0; i< parts.length; i++){
                        parts[i] = parts[i].trim();
                        if(parts[i].contains(")")){
                            parts[i] = parts[i].substring(0,parts[i].length()-1); //To distinguish station from ')'
                        }
                        if(parts[i].contains("(")){
                            parts[i] = parts[i].substring(1); //To distinguish station from '('
                        }
                    }

                    for (int i = 0; i < parts.length;i++) {
                        if (parts[i].contains("S")) {
                            if (parts[i].charAt(0) == 'S') {
                                for (int j = 0; j < parts.length; j++){
                                    if (parts[j].contains("T")){
                                        isThereTask = true;
                                        boolean taskExist = false;
                                        Task taskToAdd = new Task();
                                        for (Task task : Main.taskTypes) { //Main -> Main class
                                            if (task.getTaskTypeID().equals(parts[j])) {
                                                taskExist = true;
                                                if (j < parts.length - 1 && !parts[j + 1].contains("T")) {
                                                    if(task.getSize() == 0.0) {
                                                        if(Double.parseDouble(parts[j + 1]) == 0.0) {
                                                            noDefaultSize(lineCount, task.getTaskTypeID());
                                                        }
                                                        else if (Double.parseDouble(parts[j + 1]) >= 0){
                                                            taskToAdd = new Task(parts[j]);
                                                            taskToAdd.setSize(Double.parseDouble(parts[j+1]));
                                                        }
                                                        else {
                                                            incorrectDefaultSize(lineCount);
                                                        }
                                                    }
                                                    else if(task.getSize() != Double.parseDouble(parts[j+1])) {
                                                        if (Double.parseDouble(parts[j+1]) >= 0){
                                                            taskToAdd = new Task(parts[j]);
                                                            taskToAdd.setSize(Double.parseDouble(parts[j + 1]));
                                                        }
                                                        else if(Double.parseDouble(parts[j+1]) == 0.0){
                                                            taskToAdd = task;
                                                        }
                                                        else if(Double.parseDouble(parts[j+1]) < 0){
                                                            incorrectDefaultSize(lineCount);
                                                        }
                                                    }
                                                    else {
                                                        taskToAdd = task;
                                                    }
                                                }
                                                else {
                                                    if (task.getSize() == 0.0){
                                                        noDefaultSize(lineCount, task.getTaskTypeID());
                                                    }
                                                    else {
                                                        taskToAdd = task;
                                                    }
                                                }
                                            }
                                        }
                                        if(taskExist){
                                            stationTasks.add(taskToAdd);
                                        }
                                        else {
                                            nonDeclaredTask(lineCount,parts[j]);
                                        }
                                    }
                                }
                                if(isThereTask){
                                    boolean stationExist = false;
                                    for(Station station : stations){
                                        if (Integer.toString(station.getStationID()).equals(parts[i])) {
                                            stationExist = true;
                                        }
                                    }
                                    if(!stationExist){
                                        stations.add(new Station(Integer.parseInt(parts[i]),new ArrayList<Task>(stationTasks)));
                                        stationTasks.clear();
                                        stationCount++;
                                    }else {
                                        alreadyDeclaredStation(lineCount,parts[i]);
                                    }
                                }else{
                                    noTask(lineCount);
                                }
                                isThereTask = false;
                            }
                            else {
                                System.out.println("Station ID is not written correctly!");
                            }
                        }
                    }
                }

                lineCount++;

                if (!line.contains("STATIONS")) {
                    break;
                }
            }

            for (Station station : stations) {
                System.out.print(station.getStationID() + " ");
                for(Task task : station.getTasks()){
                    System.out.print(task.getTaskTypeID() + " ");
                }
                System.out.println();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
    public static void isMultipleTasks(boolean multiflag, boolean result) {
        multiflag = result;
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
    public static void alreadyDeclaredStation(int lineCount, String stationID) throws Exception {
        throw new Exception("Line " + lineCount + ": " + stationID + ": already declared.");
    }
    public static void noDefaultSize(int lineCount, String taskID) throws Exception{
        throw new Exception("Line " + lineCount + ": " + taskID + " has no default size, either a default size must be declared in TASKTYPE list or the size must be declared within the job.");
    }
    public static void incorrectDefaultSize(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": There is an invalid default size.");
    }
    public static void nonDeclaredTask(int lineCount, String taskID) throws Exception{
        throw new Exception("Line " + lineCount + ": " + taskID + " is not one of the declared task types.");
    }
    public static void noTask(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": There is no task given for job.");
    }
}