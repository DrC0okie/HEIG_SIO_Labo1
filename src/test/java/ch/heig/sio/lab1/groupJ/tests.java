package ch.heig.sio.lab1.groupJ;

import ch.heig.sio.lab1.TestUtils;
import ch.heig.sio.lab1.tsp.TspConstructiveHeuristic;
import ch.heig.sio.lab1.tsp.TspTour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class tests {
    /**
     * Tests les heuristiques sur les cas de base où toutes devraient produire les mêmes résultats.
     * Vérifie les résultats sur les cas simples ONE_CITY, TWO_CITIES et THREE_CITIES.
     */
    @Test
    public void testSimpleCases() {
        TspConstructiveHeuristic[] heuristics = {
                new NearestInsert(),
                new FurthestInsert(),
                new RandomInsert()
        };

        // Test les cas simples où toutes les heuristiques devraient produire le même résultat.
        TestUtils.testAll(heuristics, TestUtils.ONE_CITY);
        TestUtils.testAll(heuristics, TestUtils.TWO_CITIES);
        TestUtils.testAll(heuristics, TestUtils.THREE_CITIES);
    }

    /**
     * Teste NearestInsert sur EXERCISE_1 avec le tour attendu pour l'insertion la plus proche.
     * Utilise les résultats attendus définis dans EXERCISE_1_NI.
     */
    @Test
    public void testExercise1WithNearestInsert() {
        TestUtils.test(new NearestInsert(), TestUtils.EXERCISE_1_NI);
    }

    /**
     * Teste FurthestInsert sur EXERCISE_1 avec le tour attendu pour l'insertion la plus éloignée.
     * Utilise les résultats attendus définis dans EXERCISE_1_FI.
     */
    @Test
    public void testExercise1WithFurthestInsert() {
        TestUtils.test(new FurthestInsert(), TestUtils.EXERCISE_1_FI);
    }

    /**
     * Teste RandomInsert sur EXERCISE_1 pour vérifier la cohérence de la longueur du tour.
     * Vérifie que le tour produit est raisonnable, sans dépasser une longueur maximale prédéfinie.
     */
    @Test
    public void testExercise1WithRandomInsert() {
        RandomInsert heuristic = new RandomInsert();
        int maxLength = 450; // Seuil basé sur les valeurs observées dans les autres heuristiques
        int startCity = TestUtils.EXERCISE_1_NI.startCity();

        // Exécute plusieurs tests pour vérifier la stabilité de RandomInsert.
        for (int i = 0; i < 10; i++) {
            TspTour tour = heuristic.computeTour(TestUtils.EXERCISE_1, startCity);
            assertTrue(tour.length() <= maxLength, "RandomInsert tour length exceeded max expected length");
        }
    }

}
