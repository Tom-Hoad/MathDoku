import java.util.ArrayList;

// The class for a column on the grid.
public class Column {
    private ArrayList<Tile> columnTiles;

    // The column class constructor.
    public Column(ArrayList<Tile> columnTiles) {
        this.columnTiles = columnTiles;
    }

    // Adds a tile to the column.
    public void addTo(Tile rowTile) {
        columnTiles.add(rowTile);
    }

    // Gets the tiles in a column.
    public ArrayList<Tile> getColumnTiles() {
        return columnTiles;
    }
}
