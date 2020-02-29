import java.util.ArrayList;

// The class for a row on the grid.
public class Row {
    private ArrayList<Tile> rowTiles;

    // The row class constructor.
    public Row(ArrayList<Tile> rowTiles) {
        this.rowTiles = rowTiles;
    }

    // Adds a tile to the row.
    public void addTo(Tile rowTile) {
        rowTiles.add(rowTile);
    }

    // Gets the tiles in a row.
    public ArrayList<Tile> getRowTiles() {
        return rowTiles;
    }
}
