package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Leer archivo de entrada
            String filePath = "resources/input.txt";
            List<Machine> machines = new ArrayList<>();
            int targetPieces = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                // Primera línea: piezas totales
                targetPieces = Integer.parseInt(br.readLine().trim());

                // Resto de líneas: máquinas
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    machines.add(new Machine(parts[0].trim(), Integer.parseInt(parts[1].trim())));
                }
            }

            System.out.println("Piezas a producir: " + targetPieces);
            System.out.println("Máquinas disponibles:");
            for (Machine m : machines) {
                System.out.println("- " + m.getName() + ": " + m.getPieces() + " piezas");
            }

            System.out.println("\n=== Backtracking ===");
            Solution backtrackingSolution = BacktrackingSolver.solve(machines, targetPieces);
            if (backtrackingSolution != null) {
                System.out.println("Solución obtenida: " + backtrackingSolution.getMachineSequence());
                System.out.println("Piezas producidas: " + backtrackingSolution.getTotalPieces());
                System.out.println("Puestas en funcionamiento: " + backtrackingSolution.getActivations());
                System.out.println("Estados generados: " + BacktrackingSolver.getStatesGenerated());
            } else {
                System.out.println("No se encontró solución con Backtracking");
            }

            System.out.println("\n=== Greedy ===");
            Solution greedySolution = GreedySolver.solve(machines, targetPieces);
            if (greedySolution != null) {
                System.out.println("Solución obtenida: " + greedySolution.getMachineSequence());
                System.out.println("Piezas producidas: " + greedySolution.getTotalPieces());
                System.out.println("Puestas en funcionamiento: " + greedySolution.getActivations());
                System.out.println("Candidatos considerados: " + GreedySolver.getCandidatesConsidered());
            } else {
                System.out.println("No se encontró solución exacta con Greedy");
            }

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Formato de archivo incorrecto: " + e.getMessage());
        }
    }
}