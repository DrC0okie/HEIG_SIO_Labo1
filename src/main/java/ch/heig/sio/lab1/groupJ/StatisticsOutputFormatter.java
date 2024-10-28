package ch.heig.sio.lab1.groupJ;

public class StatisticsOutputFormatter {
    public static void printHeuristicComparison(Statistics randomInsert, Statistics nearestInsert, Statistics furthestInsert) {
        String format = "| %-15s | %-12s | %-12s | %-15s | %-20s | %-20s | %-22s | %-20s | %-20s | %-20s |\n";
        String separator = "+----------------+--------------+--------------+-----------------+----------------------+----------------------+------------------------+----------------------+----------------------+----------------------+\n";

        System.out.println("Heuristic Comparison for file: " + randomInsert.getFileName());
        System.out.print(separator);
        System.out.printf(format, "Heuristic", "Min Length", "Max Length", "Average Length", "Min Execution Time", "Max Execution Time", "Average Execution Time","Max % from Optimum","Min % from Optimum","Average % from Optimum");
        System.out.print(separator);
        System.out.printf(format, "Random Insert",
                randomInsert.getMinLength(),
                randomInsert.getMaxLength(),
                String.format("%.2f", randomInsert.getAvgLength()),
                String.format("%.9f", randomInsert.getMinExecutionTime()),
                String.format("%.9f", randomInsert.getMaxExecutionTime()),
                String.format("%.9f", randomInsert.getAvgExecutionTime()),
                String.format("%.2f", randomInsert.getAvgPercentageAboveOptimal()),
                String.format("%.2f", randomInsert.getMaxPercentageAboveOptimal()),
                String.format("%.2f", randomInsert.getMinPercentageAboveOptimal()));

        System.out.printf(format, "Nearest Insert",
                nearestInsert.getMinLength(),
                nearestInsert.getMaxLength(),
                String.format("%.2f", nearestInsert.getAvgLength()),
                String.format("%.9f", nearestInsert.getMinExecutionTime()),
                String.format("%.9f", nearestInsert.getMaxExecutionTime()),
                String.format("%.9f", nearestInsert.getAvgExecutionTime()),
                String.format("%.2f", nearestInsert.getAvgPercentageAboveOptimal()),
                String.format("%.2f", nearestInsert.getMaxPercentageAboveOptimal()),
                String.format("%.2f", nearestInsert.getMinPercentageAboveOptimal()));
        System.out.printf(format, "Furthest Insert",
                furthestInsert.getMinLength(),
                furthestInsert.getMaxLength(),
                String.format("%.2f", furthestInsert.getAvgLength()),
                String.format("%.9f", furthestInsert.getMinExecutionTime()),
                String.format("%.9f", furthestInsert.getMaxExecutionTime()),
                String.format("%.9f", furthestInsert.getAvgExecutionTime()),
                String.format("%.2f", furthestInsert.getAvgPercentageAboveOptimal()),
                String.format("%.2f", furthestInsert.getMaxPercentageAboveOptimal()),
                String.format("%.2f", furthestInsert.getMinPercentageAboveOptimal()));
        System.out.print(separator);
    }
}