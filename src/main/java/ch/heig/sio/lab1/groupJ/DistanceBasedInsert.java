package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;
import java.util.Comparator;
import java.util.List;

/**
 * Implémente les parties communes des heuristiques d'insertion basées sur la distance pour le TSP
 * @author Jarod Streckeisen, Timothée Van Hove
 */
public abstract class DistanceBasedInsert extends BaseInsertionHeuristic {

    /**
     * Définit la fonction de comparaison utilisée pour choisir la ville à insérer.
     * Les sous-classes doivent implémenter cette méthode pour fournir une stratégie (plus proche ou plus éloignée).
     * @return Un comparateur définissant comment comparer les distances.
     */
    protected abstract Comparator<Long> getDistanceComparator();

    /**
     * Sélectionne la ville non visitée selon une stratégie basée sur la distance à la tournée actuelle
     * @param data L'instance de données du TSP contenant les villes et les distances.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param unvisitedCities La liste des villes restantes à insérer.
     * @return L'index de la ville à insérer selon la stratégie définie.
     */
    @Override
    protected int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities) {
        int selectedCity = -1;
        Comparator<Long> comparator = getDistanceComparator();
        long optimalDistance = comparator == Comparator.naturalOrder() ? Long.MAX_VALUE : Long.MIN_VALUE;

        // Parcourt toutes les villes non visitées.
        for (int city : unvisitedCities) {
            // Trouve la distance la plus courte de cette ville à une ville déjà dans la tournée.
            long shortestDistanceToTour = findShortestDistanceToTour(data, tour, city);

            // Mise à jour si la distance correspond à la stratégie (plus petite ou plus grande).
            if (comparator.compare(shortestDistanceToTour, optimalDistance) < 0) {
                optimalDistance = shortestDistanceToTour;
                selectedCity = city;
            }
        }

        // Vérifie qu'une ville valide a été trouvée.
        if (selectedCity == -1) {
            throw new IllegalStateException("Aucune ville valide trouvée pour l'insertion.");
        }

        // Retirer la ville sélectionnée de la liste des villes non visitées.
        unvisitedCities.remove((Integer) selectedCity);
        return selectedCity;
    }

    /**
     * Trouve la distance la plus courte entre une ville non visitée et une des villes déjà dans la tournée.
     * @param data L'instance de données du TSP contenant les villes et les distances.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param city La ville non visitée pour laquelle on cherche la distance la plus courte.
     * @return La distance la plus courte entre la ville donnée et une ville de la tournée.
     */
    protected static long findShortestDistanceToTour(TspData data, List<Integer> tour, int city) {
        long shortestDistance = Long.MAX_VALUE;

        // Parcourt chaque ville dans la tournée et calcule la distance entre cette ville et la ville non visitée.
        for (int visitedCity : tour) {
            long distance = data.getDistance(city, visitedCity);

            if (distance < shortestDistance) {
                shortestDistance = distance;
            }
        }
        return shortestDistance;
    }
}
