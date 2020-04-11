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
        possibleValues.remove((Integer) value);
    }

    // Checks if there only one possible value.
    public boolean isFinal() {
        return possibleValues.size() == 1;
    }

    // Sets the final value.
    public void setFinalValue(int finalValue) {
        this.finalValue = finalValue;
    }

    // Gets the final value.
    public int getFinalValue() {
        return finalValue;
    }
}
