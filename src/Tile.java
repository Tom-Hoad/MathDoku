import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

// The subclass for a single tile in the grid.
public class Tile extends StackPane {
    private int gridPosition;
    private boolean selected;
    private String style;
    private int value;

    // The Tile subclass constructor.
    public Tile(int gridPosition) {
        this.gridPosition = gridPosition;
        this.getChildren().add(0, new Label(""));
        this.style = "-fx-border-color: black; ";
        this.value = 0;
    }

    // Defaults the tile.
    public void setDefault() {
        this.setStyle(style);
        selected = false;
    }

    // Adds border to the tile.
    public void addBorder(String border) {
        this.style += border;
        setDefault();
    }

    // Highlights the tile.
    public void selectTile() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 4");
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

    // Gets the tile value.
    public int getValue() {
        return value;
    }

    // Sets the tile value.
    public void setValue(int value) {
        this.value = value;
    }
}