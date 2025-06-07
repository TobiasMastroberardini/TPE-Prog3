package src;

public class Machine {
    private String name;
    private int pieces;

    public Machine(String name, int pieces) {
        this.name = name;
        this.pieces = pieces;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getPieces() {
        return pieces;
    }
}