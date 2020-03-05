import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// The class for a grid.
public class Grid {
    private int size;
    private Tile selectedTile;
    private ArrayList<Row> rows;
    private ArrayList<Column> columns;
    private ArrayList<Cage> cages;
    private ArrayList<Tile> tiles;
    private CheckMistake checkMistake;
    private History history;

    // The grid class constructor.
    public Grid(int size, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages, History history) {
        this.size = size;
        this.selectedTile = null;

        this.rows = rows;
        this.columns = columns;
        this.cages = cages;
        this.tiles = new ArrayList<>();

        this.checkMistake = new CheckMistake(this);
        this.history = history;

        // Populates rows and columns.
        for (int i = 0; i < size; i++) {
            rows.add(new Row(new ArrayList<>()));
            columns.add(new Column(new ArrayList<>()));
        }
    }

    // Adds a tile to a row.
    public void addToRow(int rowNum, Tile tile) {
        rows.get(rowNum).addTo(tile);
    }

    // Adds a tile to a column.
    public void addToColumn(int columnNum, Tile tile) {
        columns.get(columnNum).addTo(tile);
    }

    // Adds a cage to the grid.
    public void addCage(Cage cage) {
        cages.add(cage);
        cage.showCage();
    }

    // Finds all the tiles in the grid.
    public void findTiles() {
        for (Column column : columns) {
            tiles.addAll(column.getColumnTiles());
        }
    }

    // Selects a tile.
    public void selectTile(Tile tile) {
        if (tile != null) {
            this.selectedTile = tile;
            tile.setSelected();
        }
    }

    // Gets the size of the grid.
    public int getSize() {
        return size;
    }

    // Gets the selected tile.
    public Tile getSelected() {
        return selectedTile;
    }

    // Gets all the rows in the grid.
    public ArrayList<Row> getRows() {
        return rows;
    }

    // Gets all the columns in the grid.
    public ArrayList<Column> getColumns() {
        return columns;
    }

    // Gets all the cages in the grid.
    public ArrayList<Cage> getCages() {
        return cages;
    }

    // Gets all the tiles in the grid.
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    // Gets the mistake checking class.
    public CheckMistake getCheckMistake() {
        return checkMistake;
    }

    // Gets the history class.
    public History getHistory() {
        return history;
    }

    // Removes all the cages.
    public void removeCages() {
        for (Cage cage : cages) {
            cage.removeCage();
        }
        this.cages = new ArrayList<>();
    }

    // Displays the cages from a file.
    public void readFile(File selectedFile) throws IOException {
        if (selectedFile != null) {
            removeCages();
            ArrayList<String> splitFile = new ArrayList<>();

            Scanner reader = new Scanner(selectedFile);
            while (reader.hasNextLine()) {
                splitFile.add(reader.nextLine());
            }

            checkCages(splitFile);
        }
    }

    // Displays the cages from text.
    public void readText(String gameText) {
        if (!gameText.isEmpty()) {
            removeCages();
            String[] splitText = gameText.split("\\n");

            checkCages(new ArrayList<>(Arrays.asList(splitText)));
        }
    }

    // Checks if it can read a grid.
    public void checkCages(ArrayList<String> splitCages) {
        ArrayList<Tile> allTiles = new ArrayList<>();
        ArrayList<Cage> validCages = new ArrayList<>();
        boolean valid = true;

        for (String textCage : splitCages) {
            try {
                // Gets the result and operation.
                int cageSplit = textCage.indexOf(" ");
                int result = Integer.parseInt(textCage.substring(0, cageSplit - 1));
                String operation = textCage.substring(cageSplit - 1, cageSplit);

                // Gets the tile.
                ArrayList<Tile> cageTiles = new ArrayList<>();
                for (String tilePosition : textCage.substring(cageSplit + 1).split(",")) {
                    cageTiles.add(getTiles().get(Integer.parseInt(tilePosition) - 1));
                }

                // Check if already taken.
                for (Tile tile : cageTiles) {
                    if (allTiles.contains(tile)) {
                        throw new Exception("This cage overlaps another cage.");
                    }
                }

                // Check if valid symbol.
                if (!operation.equals("+") && !operation.equals("-") && !operation.equals("÷") && !operation.equals("x")) {
                    throw new Exception("This cage doesn't contain a valid symbol.");
                }

                // Check if valid result / single tile.
                if (cageTiles.size() == 1 && (result > 6 || result < 1)) {
                    throw new Exception("This cage cannot achieve its result.");
                }

                // Check if tiles are all adjacent.
                if (cageTiles.size() != 1) {
                    for (Tile tile : cageTiles) {
                        // Finds all possible adjacent positions.
                        ArrayList<Integer> adjacentPositions = new ArrayList<>();
                        adjacentPositions.add(tile.getGridPosition() - 1);
                        adjacentPositions.add(tile.getGridPosition() + 1);
                        adjacentPositions.add(tile.getGridPosition() + getSize());
                        adjacentPositions.add(tile.getGridPosition() - getSize());

                        boolean adjacentTiles = false;
                        for (Tile otherTile : cageTiles) {
                            if (adjacentPositions.contains(otherTile.getGridPosition())) {
                                adjacentTiles = true;
                                break;
                            }
                        }
                        if (!adjacentTiles) {
                            throw new Exception("The tiles in the cage are not adjacent.");
                        }
                    }
                }

                allTiles.addAll(cageTiles);
                validCages.add(new Cage(this, result, operation, cageTiles));
            } catch (Exception e) {
                // COULD BE AN ALERT
                System.out.println("Error: " + textCage + " is not a valid cage. Reason: " + e.getMessage());
                valid = false;
            }
        }

        // Check if all tiles taken.
        for (int i = 1; i <= Math.pow(getSize(), 2); i++) {
            boolean found = false;
            for (Tile tile : allTiles) {
                if (tile.getGridPosition() == i) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Error: the tile at " + i + " is not in a cage.");
                valid = false;
            }
        }

        // Adds the cages to the grid.
        if (valid) {
            for (Cage cage : validCages) {
                addCage(cage);
            }
        }
    }
}
