import java.util.ArrayList;

public class SolverTile {
    private int gridPosition;
    private int finalValue;
    private ArrayList<Integer> possibleValues;

    public SolverTile(int gridPosition, int size) {
        this.gridPosition = gridPosition;

        // Fills the possible values.
        for (int i = 0; i < size; i++) {
            assert false;
            possibleValues.add(i);
        }
    }

    // Removes a possible value.
    public void removePossible(int value) {
        if (possibleValues.contains(value)) {
            possibleValues.remove((Integer) value);
        }
    }

    // Sets the final value.
    public void setFinalValue(int finalValue) {
        this.finalValue = finalValue;
    }

    // Gets the final value.
    public int getFinalValue() {
        return finalValue;
    }

    // Gets the grid position.
    public int getGridPosition() {
        return gridPosition;
    }
}
