package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.List;

/**
 * Implémente l'heuristique d'insertion de la ville la plus proche pour le TSP.
 */
public class NearestInsert extends BaseInsertionHeuristic {

    /**
     * Sélectionne la ville non visitée qui est la plus proche d'une ville de la tournée actuelle.
     *
     * @param data            L'instance de données du TSP contenant les villes et les distances.
     * @param tour            La liste des villes déjà insérées dans la tournée.
     * @param unvisitedCities La liste des villes restantes à insérer.
     * @return L'index de la ville à insérer (la ville non visitée la plus proche).
     */
    @Override
    protected int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities) {
        int closestCity = -1;
        long minDistance = Long.MAX_VALUE;

        // Parcourt les villes non visitées et trouve la ville ayant la distance mémorisée minimale.
        for (int city : unvisitedCities) {
            if (memoizedDistances[city] < minDistance) {
                minDistance = memoizedDistances[city];
                closestCity = city;
            }
        }

        // Supprime la ville choisie de la liste des villes non visitées
        unvisitedCities.remove((Integer) closestCity);
        return closestCity;
    }
}
