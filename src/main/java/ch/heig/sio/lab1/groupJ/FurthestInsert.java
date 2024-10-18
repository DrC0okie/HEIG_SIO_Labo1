package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;
import java.util.List;

/**
 * Implémente l'heuristique d'insertion de la ville la plus éloignée pour le TSP.
 */
public class FurthestInsert extends BaseInsertionHeuristic {

    @Override
    protected int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities) {
        int bestCity = -1;
        long maxDistance = Long.MIN_VALUE;

        // Parcourt les villes non visitées et trouve la ville ayant la distance mémorisée maximale.
        for (int city : unvisitedCities) {
            if (memoizedDistances[city] > maxDistance) {
                maxDistance = memoizedDistances[city];
                bestCity = city;
            }
        }

        // Supprime la ville choisie de la liste des villes non visitées
        unvisitedCities.remove((Integer) bestCity);

        return bestCity;
    }
}
