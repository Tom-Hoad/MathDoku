// The class for a new grid change.
public class Change {
    private Tile tile;
    private String value;

    // The change class constructor.
    public Change(Tile tile, String value) {
        this.tile = tile;
        this.value = value;
    }

    // Gets the tile of change.
    public Tile getTile() {
        return tile;
    }

    // Gets the value of the change.
    public String getValue() {
        return value;
    }
}
