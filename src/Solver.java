import java.util.ArrayList;

public class Solver {
    private int[][] testGrid;
    private ArrayList<Cage> cages;

    // Responds for a call to solve the grid.
    public int[][] solve() {
        fillSingles();

        // Returns the solved grid.
        return testGrid;
    }

    // Gets the cages and size of the grid.
    public void setGrid(int size, ArrayList<Cage> cages) {
        testGrid = new int[size][size];
        this.cages = cages;
    }

    // Looks for single tile cages.
    public void fillSingles() {
        for (Cage cage : cages) {
            if (cage.getCageTiles().size() == 1) {
                Tile singleTile = cage.getCageTiles().get(0);
                testGrid[singleTile.getRow()][singleTile.getColumn()] = cage.getResult();
            }
        }
    }

}
