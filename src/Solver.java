import java.util.ArrayList;

public class Solver {
    private int[][] testGrid;
    private ArrayList<Cage> cages;

    public Solver(int size, ArrayList<Cage> cages) {
        testGrid = new int[size][size];
        this.cages = cages;
    }

    // Looks for single tile cages.
    public void fillSingles() {
        for (Cage cage : cages) {
            if (cage.getCageTiles().size() == 1) {
                Tile singleTile = cage.getCageTiles().get(0);
                testGrid[singleTile.getRow() - 1][singleTile.getColumn() - 1] = cage.getResult();
            }
        }
    }

    // Returns the solved grid.
    public int[][] getTestGrid() {
        return testGrid;
    }
}
