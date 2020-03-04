import java.util.ArrayList;

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
    public Grid(int size, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages) {
        this.size = size;
        this.selectedTile = null;

        this.rows = rows;
        this.columns = columns;
        this.cages = cages;
        this.tiles = new ArrayList<>();

        this.checkMistake = new CheckMistake(this);
        this.history = new History();

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
}
