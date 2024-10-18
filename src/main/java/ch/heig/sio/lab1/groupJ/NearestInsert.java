package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;
import java.util.List;

/**
 Implémente l'heuristique d'insertion de la ville la plus proche pour le TSP.
 * @author Jarod Streckeisen, Timothée Van Hove
 */
public class NearestInsert extends BaseInsertionHeuristic {

    /**
     * Sélectionne la ville non visitée qui est la plus proche d'une ville de la tournée actuelle.
     * @param data L'instance de données du TSP contenant les villes et les distances.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param unvisitedCities La liste des villes restantes à insérer.
     * @return L'index de la ville à insérer (la ville non visitée la plus proche).
     */
    @Override
    protected int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities) {
        int closestCity = -1;
        long minDistance = Long.MAX_VALUE;

        // Parcourt toutes les villes non visitées.
        for (int city : unvisitedCities) {
            // Trouve la distance la plus courte de cette ville à une ville déjà dans la tournée.
            long shortestDistanceToTour = findShortestDistanceToTour(data, tour, city);

            // Mise à jour si la distance est la plus petite
            if (shortestDistanceToTour < minDistance) {
                minDistance = shortestDistanceToTour;
                closestCity = city;
            }
        }

        // Retirer la ville sélectionnée de la liste des villes non visitées, car elle va être insérée.
        unvisitedCities.remove((Integer) closestCity); // Utilise Integer pour supprimer par valeur, pas par index.
        return closestCity;
    }

    /**
     * Trouve la distance la plus courte entre une ville non visitée et une des villes déjà dans la tournée.
     * @param data L'instance de données du TSP contenant les villes et les distances.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param city La ville non visitée pour laquelle on cherche la distance la plus courte.
     * @return La distance la plus courte entre la ville donnée et une ville de la tournée.
     */
    private static long findShortestDistanceToTour(TspData data, List<Integer> tour, int city) {
        long shortestDistance = Long.MAX_VALUE;

        for (int visitedCity : tour) {
            // Calcule la distance entre la ville non visitée et cette ville de la tournée.
            long distance = data.getDistance(city, visitedCity);

            // Mise à jour de la tournée la plus courte
            if (distance < shortestDistance) {
                shortestDistance = distance;
            }
        }
        return shortestDistance;
    }
}
