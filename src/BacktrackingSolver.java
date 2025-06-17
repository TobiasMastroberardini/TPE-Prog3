package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/*
 * Estrategia de Backtracking (Optimizada):
 * - Generamos un árbol de exploración donde cada nodo representa un estado parcial de la solución.
 * - En cada nivel, probamos agregar una máquina (con repetición permitida).
 * - Estados solución: cuando la suma de piezas iguala exactamente el objetivo.
 * - Poda 1: si la suma parcial supera el objetivo (no tiene sentido seguir).
 * - Poda 2: si ya tenemos una solución mejor en activaciones, no seguimos.
 * - Ordenamos las máquinas de mayor a menor para alcanzar el objetivo con menos pasos.
 * - Métrica: estados generados.
 */
public class BacktrackingSolver {
    private static int statesGenerated = 0;

    public static Solution solve(List<Machine> machines, int target) {
        List<String> currentSequence = new ArrayList<>();
        Solution[] bestSolution = { null }; // Para simular paso por referencia
        statesGenerated = 0;

        // Poda por ordenamiento: intentamos primero las máquinas más productivas
        machines.sort(Comparator.comparingInt(Machine::getPieces).reversed());

        backtrack(machines, target, currentSequence, 0, 0, bestSolution);
        return bestSolution[0];
    }

    private static void backtrack(List<Machine> machines, int target,
            List<String> currentSequence,
            int currentPieces, int activations,
            Solution[] bestSolution) {
        statesGenerated++;

        // Poda 1: si ya superamos el objetivo, cortamos esta rama
        if (currentPieces > target)
            return;

        // Poda 2: si ya tenemos una solución mejor en activaciones, no seguimos
        if (bestSolution[0] != null && activations >= bestSolution[0].getActivations())
            return;

        // Estado solución
        if (currentPieces == target) {
            bestSolution[0] = new Solution(new ArrayList<>(currentSequence), currentPieces, activations);
            return;
        }

        // Probar todas las máquinas
        for (Machine machine : machines) {
            currentSequence.add(machine.getName());
            backtrack(machines, target, currentSequence,
                    currentPieces + machine.getPieces(), activations + 1, bestSolution);
            currentSequence.remove(currentSequence.size() - 1);
        }
    }

    public static int getStatesGenerated() {
        return statesGenerated;
    }
}
