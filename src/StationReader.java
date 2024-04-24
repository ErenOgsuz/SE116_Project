import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StationReader {
    public static void readStations() {
        ArrayList<Task> stationTasks = new ArrayList<Task>();

        String workFlowFilePath = "WorkFlow.txt";

        int lineCount = 1; //The line that has been read.
        int stationCount = 0;
        boolean isThereStation = false;
        boolean isThereTask = false;
        boolean multiflag = false;
        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath));) {
            String line = "";
            boolean isStation = false;
            while (true) {
                line = br.readLine();

                if (line == null) {
                    // End of file reached, exit the loop
                    break;
                }

                if (line.contains("STATIONS")){
                    int stationTypesIndex = line.indexOf("STATIONS");
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, stationTypesIndex, openingParenthesisIndex,0);
                    lineCount++;
                    isStation = true;
                    continue;
                }

                if (line.contains("S") && isStation) {
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
                                    for(Station station : Main.stationsTypes){
                                        if (station.getStationID().equals(parts[i])) {
                                            stationExist = true;
                                        }
                                    }
                                    if(!stationExist){
                                        Main.stationsTypes.add(new Station(parts[i],new ArrayList<Task>(stationTasks)));
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
                                System.out.println("Call");

                                stationIDisWrong(lineCount);
                            }
                        }
                    }
                }
                lineCount++;

            }

            for (Station station : Main.stationsTypes) {
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
    public static void stationIDisWrong(int lineCount) throws Exception{
        throw new Exception("Line " + lineCount + ": StationID is written wrong, write \"S\" first then give number");
    }
}