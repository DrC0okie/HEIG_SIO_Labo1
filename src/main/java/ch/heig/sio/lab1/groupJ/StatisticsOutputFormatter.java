package ch.heig.sio.lab1.groupJ;

public class StatisticsOutputFormatter {
    /***
     * Pretty print all 3 heuristics statistics.
     * @param randomInsert Random insert Statistics
     * @param nearestInsert Nearest insert Statistics
     * @param furthestInsert Furthest insert Statistics
     */
    public static void printHeuristicComparison(Statistics randomInsert, Statistics nearestInsert, Statistics furthestInsert) {
        String format = "| %-15s | %-12s | %-12s | %-15s | %-20s | %-20s | %-22s | %-20s | %-20s | %-20s |\n";
        String separator = "+-----------------+--------------+--------------+-----------------+----------------------+----------------------+------------------------+----------------------+----------------------+----------------------+\n";

        System.out.println("Heuristic Comparison for file: " + randomInsert.getFileName());
        System.out.println("Optimal length : " + randomInsert.getOptimalLength());
        System.out.print(separator);
        System.out.printf(format, "Heuristic", "Min Length", "Max Length", "Avg Length", "Min Execution Time", "Max Execution Time", "Avg Execution Time","Max % from Optimum","Min % from Optimum","Avg % from Optimum");
        System.out.print(separator);
        System.out.printf(format, "Random Insert",
                randomInsert.getMinLength(),
                randomInsert.getMaxLength(),
                String.format("%.2f", randomInsert.getAvgLength()),
                String.format("%.9f", randomInsert.getMinExecutionTime()),
                String.format("%.9f", randomInsert.getMaxExecutionTime()),
                String.format("%.9f", randomInsert.getAvgExecutionTime()),
                String.format("%.2f%s", randomInsert.getMaxPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", randomInsert.getMinPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", randomInsert.getAvgPercentageAboveOptimal(),"%"));

        System.out.printf(format, "Nearest Insert",
                nearestInsert.getMinLength(),
                nearestInsert.getMaxLength(),
                String.format("%.2f", nearestInsert.getAvgLength()),
                String.format("%.9f", nearestInsert.getMinExecutionTime()),
                String.format("%.9f", nearestInsert.getMaxExecutionTime()),
                String.format("%.9f", nearestInsert.getAvgExecutionTime()),
                String.format("%.2f%s", nearestInsert.getMaxPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", nearestInsert.getMinPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", nearestInsert.getAvgPercentageAboveOptimal(),"%"));

                System.out.printf(format, "Furthest Insert",
                furthestInsert.getMinLength(),
                furthestInsert.getMaxLength(),
                String.format("%.2f", furthestInsert.getAvgLength()),
                String.format("%.9f", furthestInsert.getMinExecutionTime()),
                String.format("%.9f", furthestInsert.getMaxExecutionTime()),
                String.format("%.9f", furthestInsert.getAvgExecutionTime()),
                String.format("%.2f%s", furthestInsert.getMaxPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", furthestInsert.getMinPercentageAboveOptimal(),"%"),
                String.format("%.2f%s", furthestInsert.getAvgPercentageAboveOptimal(),"%"));

                System.out.print(separator);
    }
}