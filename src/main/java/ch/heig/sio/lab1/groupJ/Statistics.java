package ch.heig.sio.lab1.groupJ;

import java.util.Arrays;

/**
 * This class provides statistics for TSP heuristics by computing min, max, avg tour lengths and as execution times.
 * It also calculates how much the computed tour lengths exceed an optimal solution.
 * @author Jarod Streckeisen, Timothée Van Hove
 */
public class Statistics {
    private final String fileName;
    private final long minLength;
    private final long maxLength;
    private final double avgLength;
    private final double avgPercentageAboveOptimal;
    private final double minPercentageAboveOptimal;
    private final double maxPercentageAboveOptimal;
    private final double minExecutionTime;
    private final double maxExecutionTime;
    private final double avgExecutionTime;
    private final long optimalLength;

    public Statistics(String fileName, long[] lengths, double[] executionTimes, long optimalLength) {
        this.fileName = fileName;
        this.optimalLength = optimalLength;

        this.minLength = Arrays.stream(lengths).min().orElse(0);
        this.maxLength = Arrays.stream(lengths).max().orElse(0);
        this.avgLength = Arrays.stream(lengths).average().orElse(0.0);

        this.avgPercentageAboveOptimal = ((avgLength - optimalLength) / optimalLength) * 100;
        this.minPercentageAboveOptimal = ((double) minLength - optimalLength) / optimalLength * 100;
        this.maxPercentageAboveOptimal = ((double) maxLength - optimalLength) / optimalLength * 100;

        this.minExecutionTime = Arrays.stream(executionTimes).min().orElse(0.0);
        this.maxExecutionTime = Arrays.stream(executionTimes).max().orElse(0.0);
        this.avgExecutionTime = Arrays.stream(executionTimes).average().orElse(0.0);
    }

    public String getFileName() {
        return fileName;
    }

    public long getMinLength() {
        return minLength;
    }

    public long getMaxLength() {
        return maxLength;
    }

    public double getAvgLength() {
        return avgLength;
    }

    public double getMinExecutionTime() {
        return minExecutionTime;
    }

    public double getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public double getAvgExecutionTime() {
        return avgExecutionTime;
    }

    public long getOptimalLength() {
        return optimalLength;
    }

    public double getAvgPercentageAboveOptimal() {
        return avgPercentageAboveOptimal;
    }

    public double getMinPercentageAboveOptimal() {
        return minPercentageAboveOptimal;
    }

    public double getMaxPercentageAboveOptimal() {
        return maxPercentageAboveOptimal;
    }
}
