package src;

import java.util.ArrayList;
import java.util.List;

/*
 * Estrategia de Backtracking:
 * - Generamos un árbol de exploración donde cada nodo representa un estado parcial de la solución.
 * - En cada nivel del árbol, probamos agregar una máquina (con repetición permitida).
 * - Estados finales: cuando la suma de piezas alcanza o supera el objetivo.
 * - Estados solución: cuando la suma de piezas iguala exactamente el objetivo.
 * - Podas: cuando la suma parcial supera el objetivo (no tiene sentido seguir explorando).
 * - Mantenemos la mejor solución encontrada (menor cantidad de activaciones).
 */
public class BacktrackingSolver {
    private static int statesGenerated = 0;

    public static Solution solve(List<Machine> machines, int target) {
        List<String> currentSequence = new ArrayList<>();
        Solution[] bestSolution = { null }; // Usamos un array para simular "paso por referencia"
        statesGenerated = 0;

        backtrack(machines, target, currentSequence, 0, 0, bestSolution);
        return bestSolution[0];
    }

    private static void backtrack(List<Machine> machines, int target,
            List<String> currentSequence,
            int currentPieces, int activations,
            Solution[] bestSolution) {
        statesGenerated++;

        // Estado final no solución
        if (currentPieces > target) {
            return;
        }

        // Estado solución
        if (currentPieces == target) {
            if (bestSolution[0] == null || activations < bestSolution[0].getActivations()) {
                bestSolution[0] = new Solution(new ArrayList<>(currentSequence), currentPieces, activations);
            }
            return;
        }

        // Explorar todas las máquinas
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
