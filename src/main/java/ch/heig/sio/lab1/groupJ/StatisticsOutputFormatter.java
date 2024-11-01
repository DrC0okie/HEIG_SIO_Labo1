package ch.heig.sio.lab1.groupJ;

/**
 * Utility class that displays the formatted statistics results
 * @author Jarod Streckeisen, Timothée Van Hove
 */
public class StatisticsOutputFormatter {
    /***
     * Pretty print all heuristic's statistics.
     * @param heuristicNames Array of heuristic names to label each row
     * @param heuristics Array of Statistics for the heuristics to compare
     */
    public static void printHeuristicComparison(String[] heuristicNames, Statistics... heuristics) {
        String format = "| %-15s | %-12s | %-12s | %-15s | %-20s | %-20s | %-22s | %-20s | %-20s | %-20s |\n";
        String separator = "+-----------------+--------------+--------------+-----------------+----------------------+----------------------+------------------------+----------------------+----------------------+----------------------+\n";

        if (heuristics.length == 0 || heuristicNames.length != heuristics.length) {
            throw new IllegalArgumentException("Mismatched heuristic names and statistics entries.");
        }

        System.out.println("Heuristic Comparison for file: " + heuristics[0].getFileName());
        System.out.println("Optimal length : " + heuristics[0].getOptimalLength());
        System.out.print(separator);
        System.out.printf(format, "Heuristic", "Min Length", "Max Length", "Avg Length", "Min Execution Time", "Max Execution Time", "Avg Execution Time", "Max % from Optimum", "Min % from Optimum", "Avg % from Optimum");
        System.out.print(separator);

        for (int i = 0; i < heuristics.length; i++) {
            Statistics stats = heuristics[i];
            String heuristicName = heuristicNames[i];

            System.out.printf(format, heuristicName,
                    stats.getMinLength(),
                    stats.getMaxLength(),
                    String.format("%.2f", stats.getAvgLength()),
                    String.format("%.9f", stats.getMinExecutionTime()),
                    String.format("%.9f", stats.getMaxExecutionTime()),
                    String.format("%.9f", stats.getAvgExecutionTime()),
                    String.format("%.2f%%", stats.getMaxPercentageAboveOptimal()),
                    String.format("%.2f%%", stats.getMinPercentageAboveOptimal()),
                    String.format("%.2f%%", stats.getAvgPercentageAboveOptimal()));
        }
        System.out.print(separator);
    }
}
