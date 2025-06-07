package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * Estrategia Greedy:
 * - Candidatos: todas las máquinas disponibles.
 * - Estrategia de selección: en cada paso elegimos la máquina que produce más piezas sin exceder el objetivo.
 * - Consideraciones: puede no encontrar solución exacta (en ese caso devolver null).
 * - Métrica: contamos cuántos candidatos consideramos antes de tomar cada decisión.
 */
public class GreedySolver {
    private static int candidatesConsidered = 0;

    public static Solution solve(List<Machine> machines, int target) {
        List<Machine> sortedMachines = new ArrayList<>(machines);
        // Ordenamos de mayor a menor cantidad de piezas
        Collections.sort(sortedMachines, Comparator.comparingInt(Machine::getPieces).reversed());

        List<String> sequence = new ArrayList<>();
        int remaining = target;
        candidatesConsidered = 0;

        while (remaining > 0) {
            boolean found = false;

            for (Machine machine : sortedMachines) {
                candidatesConsidered++;
                if (machine.getPieces() <= remaining) {
                    sequence.add(machine.getName());
                    remaining -= machine.getPieces();
                    found = true;
                    break;
                }
            }

            if (!found) {
                return null; // No se puede encontrar solución exacta con este enfoque
            }
        }

        return new Solution(sequence, target, sequence.size());
    }

    public static int getCandidatesConsidered() {
        return candidatesConsidered;
    }
}