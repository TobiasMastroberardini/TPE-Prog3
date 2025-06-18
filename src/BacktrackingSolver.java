package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Estrategia de Backtracking:
 * 
 * - Generación del árbol de exploración: cada nodo es un estado parcial con una secuencia de máquinas, piezas producidas y activaciones.
 * 
 * - Estados solución: piezas acumuladas == objetivo.
 * 
 * - Podas aplicadas:
 *   1. Poda por factibilidad: si superamos el objetivo, no tiene sentido seguir.
 *   2. Poda por optimalidad: si la cantidad de activaciones supera la mejor solución actual, se corta.
 *   3. Memoización: se evita recalcular caminos que ya alcanzaron cierta cantidad de piezas con igual o menor cantidad de activaciones.
 *   4. Poda por cota superior: si usando la máquina más productiva para todas las activaciones restantes 
 *      (considerando el límite de la mejor solución actual) no se puede alcanzar el objetivo, se descarta la rama.
 * 
 * - Optimizaciones:
 *   - Las máquinas se ordenan de mayor a menor capacidad para encontrar soluciones óptimas antes.
 * 
 * - Métrica: cantidad de estados generados.
 *    Se contabilizan todos los estados generados durante la exploración (statesGenerated),
 *    lo que permite evaluar la eficiencia del algoritmo.
 */

public class BacktrackingSolver {
    private static int statesGenerated = 0;
    private static Map<Integer, Integer> minActivationsByPieces;

    public static Solution solve(List<Machine> machines, int target) {
        List<String> currentSequence = new ArrayList<>();
        Solution[] bestSolution = { null };
        statesGenerated = 0;
        minActivationsByPieces = new HashMap<>();

        // Ordenar máquinas de mayor a menor productividad
        machines.sort(Comparator.comparingInt(Machine::getPieces).reversed());

        backtrack(machines, target, currentSequence, 0, 0, bestSolution);
        return bestSolution[0];
    }

    private static void backtrack(List<Machine> machines, int target,
            List<String> currentSequence,
            int currentPieces, int activations,
            Solution[] bestSolution) {
        statesGenerated++;

        // Poda 1: ya superamos el objetivo no tiene sentido seguir
        if (currentPieces > target) {
            System.out.print("poda1");
            return;
        }

        // Poda 2: si ya tenemos una solución mejor
        if (bestSolution[0] != null && activations > bestSolution[0].getActivations()) {
            System.out.print("poda2");
            return;
        }

        // Estado solución
        if (currentPieces == target) {
            bestSolution[0] = new Solution(new ArrayList<>(currentSequence), currentPieces, activations);
            return;
        }

        // Poda 3: si ya alcanzamos este nivel de piezas con igual o menos activaciones
        if (minActivationsByPieces.containsKey(currentPieces)
                && activations >= minActivationsByPieces.get(currentPieces)) {
            System.out.print("poda3");
            return;
        }
        minActivationsByPieces.put(currentPieces, activations);

        // Poda 4: si usando la máquina más productiva para las activaciones restantes
        // no llegamos al objetivo
        int maxMachine = machines.get(0).getPieces();
        int remainingActivations = bestSolution[0] == null ? target : bestSolution[0].getActivations() - activations;
        if (currentPieces + maxMachine * remainingActivations < target) {
            System.out.print("poda4");
            return;
        }

        // Probar todas las máquinas
        for (Machine machine : machines) {
            currentSequence.add(machine.getName());
            backtrack(machines, target, currentSequence,
                    currentPieces + machine.getPieces(), activations + 1,
                    bestSolution);
            currentSequence.remove(currentSequence.size() - 1);
        }
    }

    public static int getStatesGenerated() {
        return statesGenerated;
    }
}
