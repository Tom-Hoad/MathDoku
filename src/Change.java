// The class for a new grid change.
public class Change {
    private Tile tile;
    private int oldValue;
    private int newValue;

    // The change class constructor.
    public Change(Tile tile, int newValue) {
        this.tile = tile;
        this.oldValue = tile.getValue();
        this.newValue = newValue;
    }

    // Gets the tile of change.
    public Tile getTile() {
        return tile;
    }

    // Gets the old value of the change.
    public int getOldValue() {
        return oldValue;
    }

    // Gets the new value of the change.
    public int getNewValue() { return newValue; }
}
