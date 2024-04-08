import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class Main {

    public static void main(String[] args) {
        String jobFilePath = "JobFile.txt"; // The path of Job file
        String workFlowFilePath = "WorkFlow.txt"; // The path of work flow file

        Path jobFilePathObject = Paths.get(jobFilePath);
        Path workFlowFilePathObject = Paths.get(workFlowFilePath);

        if (Files.exists(jobFilePathObject)) {
            System.out.println("The job file exist.");
        } else {
            System.out.println("The job file does not exist.");
        }

        if (Files.isReadable(jobFilePathObject)) {
            System.out.println("The job file accessible.");
        } else {
            System.out.println("The job file does not accessible.");
        }

        if (Files.exists(workFlowFilePathObject)) {
            System.out.println("The WorkFlow file exist.");
        } else {
            System.out.println("The WorkFlow file does not exist.");
        }

        if (Files.isReadable(workFlowFilePathObject)) {
            System.out.println("The WorkFlow file accessible.");
        } else {
            System.out.println("The WorkFlow file does not accessible.");
        }

        int lineCount = 0;  // indicates the line read

        String allText="";

        try (BufferedReader br = new BufferedReader(new FileReader(workFlowFilePath))) {
            String line = "";

            while ((line = br.readLine()) != null) {
                lineCount++;
                allText=allText+" "+line;

                int taskTypesIndex = line.indexOf("TASKTYPES");

                if (line.contains("TASKTYPES")) {
                    int openingParenthesisIndex = line.indexOf("(");
                    controlBracket(lineCount, taskTypesIndex, openingParenthesisIndex,0);
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String[] parts =allText.split(" ");

        for(int i=2; i< parts.length;i++) {
            if (!parts[i].contains("J")) {
                // add to possible task types.
                System.out.println(parts[i]);
            } else {
                break;
            }
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
}