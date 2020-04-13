import java.util.ArrayList;

public class SolverTile {
    private int gridPosition;
    private int finalValue;
    private ArrayList<Integer> possibleValues;

    public SolverTile(int gridPosition, int size) {
        this.gridPosition = gridPosition;
        this.possibleValues = new ArrayList<>();

        // Fills the possible values.
        for (int i = 1; i <= size; i++) {
            assert false;
            possibleValues.add(i);
        }
    }

    // Gets how many possible values there are.
    public int numberPossibles() {
        return possibleValues.size();
    }

    // Removes a possible value.
    public void removePossible(int value) {
        if (possibleValues.contains(value)) {
            possibleValues.remove((Integer) value);
        }
    }

    // Sets the final value if one is known.
    public void setFinalValue(int finalValue) {
        this.finalValue = finalValue;
        this.possibleValues = new ArrayList<>();
    }

    // Sets the final value if there is only one possible left.
    public void setFinalValue() {
        this.finalValue = possibleValues.get(0);
        this.possibleValues = new ArrayList<>();
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
