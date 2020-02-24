import javafx.scene.layout.Pane;

// The subclass for a single tile in the grid.
public class Tile extends Pane {
    private int x;
    private int y;
    private int gridSize;
    private boolean selected;

    public Tile(int x, int y, int gridSize) {
        this.x = x;
        this.y = y;
        this.gridSize = gridSize;

        setDefault();
        this.setOnMouseClicked(event -> selectTile());
    }

    // Styles the tile.
    public void setDefault() {
        // Adds a border to the grid.
        StringBuilder tileStyle = new StringBuilder("-fx-border-color: black;");

        if (x == 0 && y == 0) {
            tileStyle.append("-fx-border-width: 3 1 1 3");
        } else if (x == gridSize - 1 && y == 0) {
            tileStyle.append("-fx-border-width: 3 3 1 1");
        } else if (x == 0 && y == gridSize - 1) {
            tileStyle.append("-fx-border-width: 1 1 3 3");
        } else if (x == gridSize - 1 && y == gridSize - 1) {
            tileStyle.append("-fx-border-width: 1 3 3 1");
        } else if (x == 0) {
            tileStyle.append("-fx-border-width: 1 1 1 3");
        } else if (x == gridSize - 1) {
            tileStyle.append("-fx-border-width: 1 3 1 1");
        } else if (y == 0) {
            tileStyle.append("-fx-border-width: 3 1 1 1");
        } else if (y == gridSize - 1) {
            tileStyle.append("-fx-border-width: 1 1 3 1");
        }

        this.setStyle(tileStyle.toString());
        selected = false;
    }

    // Highlights the tile.
    public void selectTile() {
        this.setStyle("-fx-border-width: 2;" +
                      "-fx-border-color: red;");

        selected = true;
    }

    // Gets if the tile is selected.
    public boolean isSelected() {
        return selected;
    }

    // Returns the tile.
    public Tile getTile() {
        return this;
    }
}