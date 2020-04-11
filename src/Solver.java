import java.util.ArrayList;

public class Solver {
    private SolverTile[][] testGrid;
    private ArrayList<Cage> cages;

    // Gets the cages and size of the grid.
    public void setGrid(int size, ArrayList<Cage> cages) {
        this.testGrid = new SolverTile[size][size];
        this.cages = cages;

        // Populates the test grid.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                testGrid[i][j] = new SolverTile((i * size) + j, size);
            }
        }
    }

    // Responds for a call to solve the grid.
    public SolverTile[][] solve() {
        // Looks for single tile cages.
        for (Cage cage : cages) {
            if (cage.getCageTiles().size() == 1) {
                Tile singleTile = cage.getCageTiles().get(0);
                testGrid[singleTile.getRow()][singleTile.getColumn()].setFinalValue(cage.getResult());
            }
        }

        // Returns the solved grid.
        return testGrid;
    }
}
