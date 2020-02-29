import java.util.ArrayList;

// The class for a grid.
public class Grid {
    private int size;
    private ArrayList<Tile> tiles;
    private ArrayList<Row> rows;
    private ArrayList<Column> columns;
    private ArrayList<Cage> cages;

    // The grid class constructor.
    public Grid(int size, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages) {
        this.size = size;
        this.tiles = new ArrayList<>();
        this.rows = rows;
        this.columns = columns;
        this.cages = cages;

        // Populates rows and columns.
        for (int i = 0; i < size; i++) {
            rows.add(new Row(new ArrayList<>()));
            columns.add(new Column(new ArrayList<>()));
        }

        // Finds all the tile in the grid.
        this.tiles = new ArrayList<>();
        for (Row row : rows) {
            tiles.addAll(row.getRowTiles());
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
        for (Row row : rows) {
            tiles.addAll(row.getRowTiles());
        }
    }

    // Gets the size of the grid.
    public int getSize() {
        return size;
    }

    // Gets all the tiles in the grid.
    public ArrayList<Tile> getTiles() {
        return tiles;
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
}
