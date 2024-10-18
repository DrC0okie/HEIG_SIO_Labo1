package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.display.ObservableTspConstructiveHeuristic;
import ch.heig.sio.lab1.display.TspHeuristicObserver;
import ch.heig.sio.lab1.tsp.Edge;
import ch.heig.sio.lab1.tsp.TspData;
import ch.heig.sio.lab1.tsp.TspTour;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gère l'initialisation du tour et l'insertion progressive des villes, en permettant
 * aux sous-classes de définir leur stratégie pour choisir la ville à insérer.
 */
public abstract class BaseInsertionHeuristic implements ObservableTspConstructiveHeuristic {

    // Mémorisation des distances minimales entre villes non visitées et la tournée actuelle
    protected long[] memoizedDistances;

    /**
     * Construit une tournée en insérant les villes progressivement.
     * @param data L'instance de données du TSP contenant les villes et les distances.
     * @param startCityIndex L'index de la ville de départ.
     * @param observer Oservateur permettant de visualiser l'évolution du tour.
     * @return TspTour représentant la tournée finale et sa longueur totale.
     */
    @Override
    public TspTour computeTour(TspData data, int startCityIndex, TspHeuristicObserver observer) {
        int n = data.getNumberOfCities();
        List<Integer> tour = new ArrayList<>();  // Liste représentant les villes dans la tournée.

        // Commence la tournée avec la ville de départ donnée.
        tour.add(startCityIndex);

        // Initialise la liste des villes qui ne sont pas encore visitées.
        List<Integer> unvisitedCities = initializeUnvisitedCities(n, startCityIndex);

        // Initialisation du tableau de distances mémorisées pour chaque ville non visitée
        memoizedDistances = new long[n];
        for (int i = 0; i < n; i++) {
            memoizedDistances[i] = Long.MAX_VALUE;
        }

        // Initialisation de la longueur de la tournée
        long totalLength = 0;

        // Boucle jusqu'à ce que toutes les villes aient été insérées dans la tournée.
        while (!unvisitedCities.isEmpty()) {
            // Sélection de la prochaine ville à insérer (définie par la sous-classe).
            int cityToInsert = selectCityToInsert(data, tour, unvisitedCities);
            // Trouver la meilleure position pour insérer la ville choisie dans la tournée existante.
            int bestPosition = findBestInsertionPosition(data, tour, cityToInsert);
            // Calculer l'augmentation de la longueur de la tournée due à l'insertion de la ville.
            long minLengthIncrease = calculateLengthIncrease(data, tour, cityToInsert, bestPosition);

            // Insérer la ville à la meilleure position trouvée et mettre à jour la longueur totale.
            tour.add(bestPosition, cityToInsert);
            totalLength += minLengthIncrease;

            // Met à jour les distances mémorisées après l'insertion de la ville.
            updateMemoizedDistances(data, tour, cityToInsert, unvisitedCities);

            // Notifier l'observateur pour visualiser la progression de la tournée.
            observer.update(tourToEdges(tour));
        }

        // Retourner la tournée finale avec sa longueur totale.
        return new TspTour(data, tour.stream().mapToInt(Integer::intValue).toArray(), totalLength);
    }

    /**
     * Définit comment choisir la prochaine ville à insérer dans la tournée.
     * @param data L'instance de données du TSP.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param unvisitedCities La liste des villes restantes à visiter.
     * @return L'index de la ville à insérer.
     */
    protected abstract int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities);

    /**
     * Met à jour les distances mémorisées après l'insertion d'une nouvelle ville dans la tournée.
     * @param data L'instance de données du TSP.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param insertedCity L'index de la ville insérée.
     * @param unvisitedCities La liste des villes restantes à visiter.
     */
    private void updateMemoizedDistances(TspData data, List<Integer> tour, int insertedCity, List<Integer> unvisitedCities) {
        // Met à jour les distances mémorisées en calculant la distance entre chaque ville non visitée et la ville insérée.
        for (int city : unvisitedCities) {
            long newDistance = data.getDistance(city, insertedCity);
            if (newDistance < memoizedDistances[city]) {
                memoizedDistances[city] = newDistance;
            }
        }
    }

    /**
     * Initialise la liste des villes non visitées, en excluant la ville de départ.
     * @param n Le nombre total de villes.
     * @param startCityIndex L'index de la ville de départ.
     * @return Une liste d'index représentant les villes non visitées.
     */
    private static List<Integer> initializeUnvisitedCities(int n, int startCityIndex) {
        List<Integer> unvisitedCities = new ArrayList<>();
        // Parcourt toutes les villes et ajoute celles qui ne sont pas la ville de départ.
        for (int i = 0; i < n; i++) {
            if (i != startCityIndex) unvisitedCities.add(i);
        }
        return unvisitedCities;
    }

    /**
     * Trouve la meilleure position d'insertion pour une ville donnée dans la tournée actuelle.
     * @param data L'instance de données du TSP.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param cityToInsert L'index de la ville à insérer.
     * @return L'index dans la tournée où la ville doit être insérée pour minimiser l'augmentation de la longueur.
     */
    private static int findBestInsertionPosition(TspData data, List<Integer> tour, int cityToInsert) {
        int bestInsertPosition = 0;
        long minLengthIncrease = Long.MAX_VALUE;

        // Parcourt chaque paire consécutive de villes dans la tournée.
        for (int i = 0; i < tour.size(); i++) {
            int prevCity = tour.get(i);
            int nextCity = tour.get((i + 1) % tour.size());  // La tournée est circulaire, on revient à la première ville après la dernière.

            // Calcul de l'augmentation de la longueur si la ville est insérée entre prevCity et nextCity.
            long lengthIncrease = data.getDistance(prevCity, cityToInsert) +
                    data.getDistance(cityToInsert, nextCity) -
                    data.getDistance(prevCity, nextCity);

            // Si l'augmentation de la longueur est inférieure à celle actuellement stockée, mettre à jour les valeurs.
            if (lengthIncrease < minLengthIncrease) {
                minLengthIncrease = lengthIncrease;
                bestInsertPosition = i + 1;
            }
        }
        return bestInsertPosition;
    }

    /**
     * Calcule l'augmentation de la longueur de la tournée si une ville est insérée à une position donnée.
     * @param data L'instance de données du TSP.
     * @param tour La liste des villes déjà insérées dans la tournée.
     * @param cityToInsert L'index de la ville à insérer.
     * @param position La position où la ville doit être insérée.
     * @return L'augmentation de la longueur de la tournée.
     */
    private static long calculateLengthIncrease(TspData data, List<Integer> tour, int cityToInsert, int position) {
        // Gérer l'insertion en première position
        //Si position == 0, la ville précédente dans la tournée doit être la dernière ville (tournée circulaire).
        int prevCity = (position == 0) ? tour.getLast() : tour.get(position - 1);
        int nextCity = tour.get(position % tour.size());

        // Calcul de la différence de longueur après l'insertion.
        return data.getDistance(prevCity, cityToInsert) +
                data.getDistance(cityToInsert, nextCity) -
                data.getDistance(prevCity, nextCity);
    }

    /**
     * Convertit la tournée en un ensemble d'arêtes pour visualiser le tour.
     * @param tour La liste des villes dans la tournée.
     * @return Un itérateur d'arêtes représentant la tournée actuelle.
     */
    private static Iterator<Edge> tourToEdges(List<Integer> tour) {
        List<Edge> edges = new ArrayList<>();
        // Parcourt toutes les villes et crée des arêtes entre chaque paire consécutive de villes.
        for (int i = 0; i < tour.size(); i++) {
            edges.add(new Edge(tour.get(i), tour.get((i + 1) % tour.size())));  // La tournée est circulaire.
        }
        return edges.iterator();
    }
}
