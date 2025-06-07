package src;

import java.util.List;

public class Solution {
    private List<String> machineSequence;
    private int totalPieces;
    private int activations;

    public Solution(List<String> machineSequence, int totalPieces, int activations) {
        this.machineSequence = machineSequence;
        this.totalPieces = totalPieces;
        this.activations = activations;
    }

    // Getters
    public List<String> getMachineSequence() {
        return machineSequence;
    }

    public int getTotalPieces() {
        return totalPieces;
    }

    public int getActivations() {
        return activations;
    }
}