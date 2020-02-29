import java.util.ArrayList;

// The class for a grid.
public class Grid {
    private int size;
    private ArrayList<Row> rows;
    private ArrayList<Column> columns;
    private ArrayList<Cage> cages;

    // The grid class constructor.
    public Grid(int size, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages) {
        this.size = size;
        this.rows = rows;
        this.columns = columns;
        this.cages = cages;

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
    }

    // Gets all the tiles in the grid.
    public ArrayList<Tile> getTiles() {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Row row : rows) {
            tiles.add(row.getRowTiles());
        }
    }
}
