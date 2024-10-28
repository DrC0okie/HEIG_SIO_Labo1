package ch.heig.sio.lab1.groupJ;

import java.util.Map;

public class Statistics {
    private String fileName;
    private long minLength;
    private long maxLength;
    private double avgLength;

    private double avgPercentageAboveOptimal;
    private double minPercentageAboveOptimal;
    private double maxPercentageAboveOptimal;
    private double minExecutionTime;
    private double maxExecutionTime;
    private double avgExecutionTime;
    private Map<String,Long> optimalLength;

    public String getFileName() { return fileName; }
    public long getMinLength() { return minLength; }
    public long getMaxLength() { return maxLength; }
    public double getAvgLength() { return avgLength; }
    public double getMinExecutionTime() { return minExecutionTime; }
    public double getMaxExecutionTime() { return maxExecutionTime; }
    public double getAvgExecutionTime() { return avgExecutionTime; }

    public double getAvgPercentageAboveOptimal() {
        return avgPercentageAboveOptimal;
    }
    public double getMinPercentageAboveOptimal() {
        return minPercentageAboveOptimal;
    }


    public double getMaxPercentageAboveOptimal() {
        return maxPercentageAboveOptimal;
    }
    public Statistics(String fileName, long[] lengths, double[] executionTimes, Map<String,Long> optimalLength) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        if (lengths == null || executionTimes == null || lengths.length == 0 || executionTimes.length == 0) {
            throw new IllegalArgumentException("Input arrays cannot be null or empty");
        }
        if (lengths.length != executionTimes.length) {
            throw new IllegalArgumentException("Input arrays must have the same length");
        }
        this.optimalLength = optimalLength;
        this.fileName = fileName;
        minLength = maxLength = lengths[0];
        minExecutionTime = maxExecutionTime = executionTimes[0];
        long totalLength = lengths[0];
        double totalExecutionTime = executionTimes[0];

        for (int i = 1; i < lengths.length; i++) {
            minLength = Math.min(minLength, lengths[i]);
            maxLength = Math.max(maxLength, lengths[i]);
            minExecutionTime = Math.min(minExecutionTime, executionTimes[i]);
            maxExecutionTime = Math.max(maxExecutionTime, executionTimes[i]);
            totalLength += lengths[i];
            totalExecutionTime += executionTimes[i];
        }


        avgLength = (double) totalLength / lengths.length;
        String filenameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        long currentOptLength = optimalLength.get(filenameWithoutExtension);
        avgPercentageAboveOptimal = (avgLength - currentOptLength)/currentOptLength * 100;
        minPercentageAboveOptimal = ((double)minLength - currentOptLength)/currentOptLength * 100;
        maxPercentageAboveOptimal = ((double)maxLength - currentOptLength)/currentOptLength * 100;
        avgExecutionTime = totalExecutionTime / executionTimes.length;
    }
}