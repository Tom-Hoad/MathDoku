import javafx.scene.layout.StackPane;

// The subclass for a single tile in the grid.
public class Tile extends StackPane {
    private int gridPosition;
    private boolean selected;

    // The Tile subclass constructor.
    public Tile(int gridPosition) {
        this.gridPosition = gridPosition;
    }

    // Defaults the tile.
    public void setDefault() {
        this.setStyle("-fx-border-width: 1;" +
                      "-fx-border-color: black;");
        selected = false;
    }

    // Highlights the tile.
    public void selectTile() {
        this.setStyle("-fx-border-width: 3;" +
                      "-fx-border-color: red;");
        selected = true;
    }

    // Gets if the tile is selected.
    public boolean isSelected() {
        return selected;
    }

    // Gets the tile position in the grid.
    public int getGridPosition() {
        return gridPosition;
    }
}