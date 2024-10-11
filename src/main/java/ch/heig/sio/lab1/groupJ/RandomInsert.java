package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;

import java.util.*;

public class RandomInsert implements ObservableTspConstructiveHeuristic {


    static Random generator = new Random(0x134DA73);

    private int getInsertionPoint(TspData data,int currentCity,int[] tour,int nbCity){
        int bestPoint = 0;
        int bestCurrentLength = Integer.MAX_VALUE;
        for(int i = 0 ; i < nbCity ; i++ ){
            int length = data.getDistance(tour[i],currentCity) + data.getDistance(currentCity, tour[i+1]);
            if(length < bestCurrentLength){
                bestCurrentLength = length;
                bestPoint = i;
            }
        }
        return bestPoint;
    }
    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        int n = data.getNumberOfCities();

        int[]tour = new int[n];
        ArrayList<Integer> cities = new ArrayList<>();
        for(int i = 0 ; i < n ; i++) {
            cities.add(i);
        }
        Collections.shuffle(cities,generator);

        int currentCity = cities.get(0);
        int length = data.getDistance(startCityIndex,currentCity);
        tour[0] = startCityIndex;
        tour[1] = currentCity;

        for(int i = 1; i <= n ; i ++){
            //Selectionne une ville aléatoire
            currentCity = cities.get(i);
            //Calculer où insérer la ville sélectionner dans le tour
            int bestInsertionPointIndex = getInsertionPoint(data,currentCity,tour,i+1);

            //Mettre à jour tour[]
        }
        //observer.update();
        return null;
    }

}
