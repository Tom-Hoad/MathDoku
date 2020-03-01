// The class for a new grid change.
public class Change {
    private Tile tile;
    private int value;

    // The change class constructor.
    public Change(Tile tile) {
        this.tile = tile;
        this.value = tile.getValue();
    }

    // Gets the tile of change.
    public Tile getTile() {
        return tile;
    }

    // Gets the value of the change.
    public int getValue() {
        return value;
    }
}
