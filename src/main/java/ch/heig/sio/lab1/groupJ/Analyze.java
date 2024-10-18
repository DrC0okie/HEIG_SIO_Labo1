package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.io.File;
import java.io.IOException;

public final class Analyze {

  private static final double NANO_TO_MILLIS  = 1_000_000.0;
  public static void main(String[] args) throws IOException {
    // TODO
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.
    File dataFolder = new File("data");
    File[] dataFiles = dataFolder.listFiles();
    if(dataFiles == null){
      throw new IOException("Data files not found");
    }
    FurthestInsert FIHeuristic = new FurthestInsert();
    NearestInsert NIHeuristic = new NearestInsert();
    RandomInsert RIHeuristic = new RandomInsert();

    for (File dataFile : dataFiles) {

      Statistics RIStatistics = computeStatistics(RIHeuristic, dataFile);
      Statistics FIStatistics = computeStatistics(FIHeuristic, dataFile);
      Statistics NIStatistics = computeStatistics(NIHeuristic, dataFile);

      StatisticsOutputFormatter.printHeuristicComparison(RIStatistics, NIStatistics, FIStatistics);
    }

    // Longueurs optimales :
    // pcb442 : 50778
    // att532 : 86729
    // u574 : 36905
    // pcb1173   : 56892
    // nrw1379  : 56638
    // u1817 : 57201

    // Exemple de lecture d'un jeu de données :
    // TspData data = TspData.fromFile("data/att532.dat");
  }

  /***
   * Compute statistics for a single file for a given heuristic
   * @param heuristic The heuristic to make statistics on
   * @param file The file we want to make statistics on
   * @return a new statistics
   */
  static public Statistics computeStatistics(BaseInsertionHeuristic heuristic,File file) throws IOException {
    String fileName = file.getName();
    TspData data = TspData.fromFile(file.getCanonicalPath());

    int nbCities = data.getNumberOfCities();
    long[] length = new long[nbCities];
    double[] executionTime = new double[nbCities];

    for(int i = 0; i < nbCities ; i++ ){

      long startTime = System.nanoTime();
      TspTour tour = heuristic.computeTour(data,i);
      long endTime = System.nanoTime();

      long duration = endTime - startTime;

      length[i] = tour.length();
      executionTime[i] =  duration / NANO_TO_MILLIS;
    }
    return new Statistics(fileName,length,executionTime);
  }
}
