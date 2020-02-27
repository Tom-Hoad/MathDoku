import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

// The subclass for a single tile in the grid.
public class Tile extends StackPane {
    private int gridPosition;
    private boolean selected;
    private String style;
    private String border;
    private int value;

    // The Tile subclass constructor.
    public Tile(int gridPosition) {
        this.gridPosition = gridPosition;
        this.getChildren().add(0, new Label(""));
        this.style = "-fx-border-color: black; -fx-background-color: white; ";
        this.value = 0;
    }

    // Defaults the tile.
    public void setDefault() {
        this.setStyle(style + border);
        selected = false;
    }

    // Adds border to the tile.
    public void addBorder(String border) {
        this.border = border;
        setDefault();
    }

    // Highlights the tile when selected.
    public void selectTile() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 4");
        selected = true;
    }

    // Highlights the tile when related to a mistake.
    public void mistakeTile() {
        this.style = "-fx-border-color: black; -fx-background-color: lightpink; ";
        setDefault();
    }

    // Removes highlights from the tile when a mistake is corrected.
    public void correctTile() {
        this.style = "-fx-border-color: black; ";
        setDefault();
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