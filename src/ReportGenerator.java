import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

    public static double calculateAverageJobTardiness(List<Job> jobs) {
        List<Job> tardyJobs = new ArrayList<>();
        int totalTardiness = 0;
        int tardyJobCount = 0;

        // Iterate through the list of jobs
        for (Job job : jobs) {
            // Calculate the completion time of the job
            double completionTime = job.getStartTime() + job.getDuration();
            // Check if the job is late
            if (completionTime > job.getDeadline()) {
                tardyJobs.add(job); // Add tardy job to the list
                int tardiness = (int) (completionTime - job.getDeadline());
                totalTardiness += tardiness; // Increment total tardiness
                tardyJobCount++; // Increment tardy job count
            }
        }

        // Print the tardy jobs
        if (tardyJobCount > 0) {
            System.out.println("Tardy Jobs:");
            for (Job job : tardyJobs) {
                System.out.println("Job ID: " + job.getJobId() + ", Tardiness: " + (job.getStartTime() + job.getDuration() - job.getDeadline()));
            }
        } else {
            System.out.println("No tardy jobs found.");
        }

        // Calculate and print average job tardiness
        double averageTardiness = 0.0; // Default value if no tardy jobs found
        if (tardyJobCount > 0) {
            averageTardiness = (double) totalTardiness / tardyJobCount;
            System.out.println("Average Job Tardiness: " + averageTardiness);
        } else {
            System.out.println("Average Job Tardiness: 0");
        }

        // Return the calculated average job tardiness
        return averageTardiness;
    }


    public static void calculateStationUtilization(List<Station> stations, double totalSimulationTime) {
        for (Station station : stations) {
            List<Task> tasks = station.getCurrentTasks();
            double busyTime = 0;
            for (Task task : tasks) {
                busyTime += task.getDuration();
            }
            double idleTime = totalSimulationTime - busyTime;
            double utilizationPercentage = (busyTime / totalSimulationTime) * 100;
            double idlenessPercentage = 100 - utilizationPercentage;
            System.out.println("Station ID: " + station.getStationID());
            System.out.println("Utilization Percentage: " + utilizationPercentage + "%");
            System.out.println("Idleness Percentage: " + idlenessPercentage + "%");

        }
    }
}



