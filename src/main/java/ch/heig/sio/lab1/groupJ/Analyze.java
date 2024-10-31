package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Analyze {

    private static final double NANO_TO_MILLIS = 1_000_000.0;

    /***
     * Main function for Statistics calculation.
     * Read all file in "data" folder and perform statistics for every euristics
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //Map contenant les valeurs optimales pour chaque fichier.
        Map<String, Long> optimalLengths = new HashMap<>();
        optimalLengths.put("pcb442", 50778L);
        optimalLengths.put("att532", 86729L);
        optimalLengths.put("u574", 36905L);
        optimalLengths.put("pcb1173", 56892L);
        optimalLengths.put("nrw1379", 56638L);
        optimalLengths.put("u1817", 57201L);

        File dataFolder = new File("data");
        File[] dataFiles = dataFolder.listFiles();
        if (dataFiles == null) {
            throw new IOException("Data files not found");
        }
        FurthestInsert FIHeuristic = new FurthestInsert();
        NearestInsert NIHeuristic = new NearestInsert();
        RandomInsert RIHeuristic = new RandomInsert();

        for (File dataFile : dataFiles) {

            Statistics RIStatistics = computeStatistics(RIHeuristic, dataFile, optimalLengths);
            Statistics FIStatistics = computeStatistics(FIHeuristic, dataFile, optimalLengths);
            Statistics NIStatistics = computeStatistics(NIHeuristic, dataFile, optimalLengths);


            StatisticsOutputFormatter.printHeuristicComparison(new String[]{"Random Insert", "Nearest Insert", "Furthest Insert"}, RIStatistics, NIStatistics, FIStatistics);
        }
    }

    /***
     * Compute statistics for a single file for a given heuristic
     * @param heuristic The heuristic used to compute the statistics
     * @param file The file we want to make statistics on
     * @return a new Statistics instance
     */
    static public Statistics computeStatistics(BaseInsertionHeuristic heuristic, File file, Map<String, Long> optimalLength) throws IOException {
        String fileName = file.getName();
        TspData data = TspData.fromFile(file.getCanonicalPath());

        int nbCities = data.getNumberOfCities();
        //Store length and execution time  for every start city
        long[] length = new long[nbCities];
        double[] executionTime = new double[nbCities];
        //Compute tour for every start city
        for (int i = 0; i < nbCities; i++) {
            long startTime = System.nanoTime();
            TspTour tour = heuristic.computeTour(data, i);
            long endTime = System.nanoTime();

            long duration = endTime - startTime;

            length[i] = tour.length();
            executionTime[i] = duration / NANO_TO_MILLIS;
        }
        String filenameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        long currentOptimalLength = optimalLength.get(filenameWithoutExtension);
        return new Statistics(fileName, length, executionTime, currentOptimalLength);
    }
}
