import java.lang.reflect.Array;
import java.util.ArrayList;

public class Solver {
    private SolverTile[][] testGrid;
    private ArrayList<Cage> cages;
    private int size;

    // Gets the cages and size of the grid.
    public void setGrid(int size, ArrayList<Cage> cages) {
        this.testGrid = new SolverTile[size][size];
        this.cages = cages;
        this.size = size;

        // Populates the test grid.
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                testGrid[i][j] = new SolverTile((i * size) + j + 1, size);
            }
        }
    }

    // Checks the rows to eliminate possible values.
    public void checkRows() {
        // Gets every row.
        for (SolverTile[] row : testGrid) {
            // Will check each possible final value.
            for (int i = 1; i <= size; i++) {
                // Gets every tile in the row.
                for (SolverTile rowTile1 : row) {
                    // Checks if the tile has a final value.
                    if (rowTile1.getFinalValue() == i) {
                        // Removes that possible value from the tiles in the row.
                        for (SolverTile rowTile2 : row) {
                            rowTile2.removePossible(i);
                        }
                        break;
                    }
                }
            }
        }
    }

    // Checks the columns to eliminate possible values.
    public void checkColumns() {
        // Gets every column.
        for (int i = 0; i < size; i++) {
            SolverTile[] column = new SolverTile[size];

            // Creates a column.
            int count = 0;
            for (SolverTile[] row : testGrid) {
                for (SolverTile rowTile : row) {
                    if ((rowTile.getGridPosition() - 1) % size == i) {
                        column[count] = rowTile;
                        count++;
                    }
                }
            }

            // Checks every possible final value.
            for (int j = 1; j <= size; j ++) {
                // Gets every tile in the column
                for (SolverTile columnTile1 : column) {
                    //Checks the tile if it has a final value.
                    if (columnTile1.getFinalValue() == j) {
                        // Removes that possible value from the tiles in the column.
                        for (SolverTile columnTile2 : column) {
                            columnTile2.removePossible(j);
                        }
                        break;
                    }
                }
            }
        }
    }

    // Checks for a tile that has only one possible value.
    public boolean findFinals() {
        boolean update = false;

        // Gets every tile in the grid.
        for (SolverTile[] row : testGrid) {
            for (SolverTile tile : row) {
                // Checks if tile can be made final.
                if (tile.numberPossibles() == 1) {
                    tile.setFinalValue();
                    update = true;
                }
            }
        }
        return update;
    }

    // Finds possible value pairs for adding cages.
    public ArrayList<int[]> findAddPairs(int result, int[] possibleValues) {
        ArrayList<int[]> newPossibles = new ArrayList<>();
        int count = 0;

        for (int i = 0; i < possibleValues.length; i++) {
            for (int j = i + 1; j < possibleValues.length; j++) {
                if (possibleValues[i] + possibleValues[j] == result) {
                    newPossibles.add(count, new int[]{possibleValues[i], possibleValues[j]});
                    count++;
                }
            }
        }
        return newPossibles;
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

        // Eliminates possibilities until there are no more certain possibilities.
        do {
            checkRows();
            checkColumns();
        } while (findFinals());

        // Returns the solved grid.
        return testGrid;
    }
}