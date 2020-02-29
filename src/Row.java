import java.util.ArrayList;

public class Row {
    private ArrayList<Tile> rowTiles;

    public Row(ArrayList<Tile> rowTiles) {
        this.rowTiles = rowTiles;
    }

    public void addTo(Tile rowTile) {
        rowTiles.add(rowTile);
    }
}
