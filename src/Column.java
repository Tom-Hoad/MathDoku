import java.util.ArrayList;

public class Column {
    private ArrayList<Tile> columnTiles;

    public Column(ArrayList<Tile> columnTiles) {
        this.columnTiles = columnTiles;
    }

    public void addTo(Tile rowTile) {
        columnTiles.add(rowTile);
    }
}
