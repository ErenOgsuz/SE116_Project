import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static void calculateAverageJobTardinessAndUtilization() {
        List<Job> tardyJobs = new ArrayList<>();
        double totalTardiness = 0.0;
        double tardyJobCount = 0.0;

        // Iterate through the list of jobs
        for (Job job : Main.jobs) {
            if(job.getDelayTime()>0){
                tardyJobCount++;
                totalTardiness+=job.getDelayTime();
                tardyJobs.add(job);
            }
        }

        // Print the tardy jobs
        System.out.println("...................Tardy Jobs.....................");
        if (tardyJobCount > 0) {
            System.out.println("Tardy Jobs:");
            for (Job job : tardyJobs) {
                System.out.println("Job ID: " + job.getJobId() + ", Tardiness: " + (job.getDelayTime()));
            }
        } else {
            System.out.println("No tardy jobs found.");
        }

        // Calculate and print average job tardiness
        double averageTardiness = 0.0; // Default value if no tardy jobs found
        if (tardyJobCount > 0) {
            averageTardiness = totalTardiness / tardyJobCount;
            System.out.println("Average Job Tardiness: " + averageTardiness);
        } else {
            System.out.println("Average Job Tardiness: 0");
        }
        System.out.println("..................................................\n");

        System.out.println("...............Station Utilization................");
        for (Station station : Main.stationsTypes) {
            //System.out.println(station.getSumDuration() );
            //System.out.println(Main.events.getLast().getDeadLine());
            double utilizationPercentage = (station.getSumDuration() / Main.events.getLast().getDeadLine()*100);
            double idlenessPercentage = 100 - utilizationPercentage;
            System.out.println("Station ID: " + station.getStationID());
            System.out.println("Utilization Percentage: " + utilizationPercentage + "%");
            System.out.println("Idleness Percentage: " + idlenessPercentage + "%");
            System.out.println();
        }
        System.out.println("..................................................");
    }

}



