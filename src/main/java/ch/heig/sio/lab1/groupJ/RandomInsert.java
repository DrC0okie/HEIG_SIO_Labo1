package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.tsp.TspData;

import java.util.*;

/**
 * Implémente l'heuristique d'insertion aléatoire pour le TSP.
 */
public class RandomInsert extends BaseInsertionHeuristic {

    private static final long SEED = 0x134DA73;
    private final Random random;

    /**
     * Initialise le générateur de nombres pseudo-aléatoires avec la graine définie.
     */
    public RandomInsert() {
        this.random = new Random(SEED);
    }

    /**
     * Sélectionne une ville non visitée aléatoirement dans la liste des villes non visitées.
     *
     * @param data            L'instance de données du TSP (non utilisée directement ici, mais nécessaire pour l'interface).
     * @param tour            La liste des villes déjà insérées dans la tournée (non utilisée ici pour le choix aléatoire).
     * @param unvisitedCities La liste des villes restantes qui ne sont pas encore dans la tournée.
     * @return L'index de la ville choisie aléatoirement et retirée de la liste des villes non visitées.
     */
    @Override
    protected int selectCityToInsert(TspData data, List<Integer> tour, List<Integer> unvisitedCities) {
        int nextCityIndex = random.nextInt(unvisitedCities.size());
        return unvisitedCities.remove(nextCityIndex);
    }
}

