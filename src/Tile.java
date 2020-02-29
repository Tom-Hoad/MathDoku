import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

// The subclass for a single tile in the grid.
public class Tile extends StackPane implements Comparable<Tile> {
    private int gridPosition;
    private String style;
    private String border;
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
        this.setStyle(style + border);
    }

    // Adds border to the tile.
    public void addBorder(String border) {
        this.border = border;
        setDefault();
    }

    // Highlights the tile when selected.
    public void setSelected() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 4; ");
        System.out.println(getGridPosition());
    }

    // Highlights the tile when related to a mistake.
    public void setMistake() {
        this.style = "-fx-border-color: black; -fx-background-color: lightpink; ";
        setDefault();
    }

    // Removes highlights from the tile when a mistake is corrected.
    public void correctTile() {
        this.style = "-fx-border-color: black; ";
        setDefault();
    }

    // Gets the tile position in the grid.
    public int getGridPosition() {
        return gridPosition;
    }

    // Gets the tile value.
    public int getValue() {
        return value;
    }

    // Displays the value.
    public void displayValue(String number) {
        getChildren().remove(0);
        setValue(Integer.parseInt(number));
        Label label = new Label(number);
        label.setFont(new Font(50));
        getChildren().add(0, label);
    }

    // Sets the tile value.
    public void setValue(int value) {
        this.value = value;
    }

    // Compares the value of two tiles.
    @Override
    public int compareTo(Tile tile) {
        return Integer.compare(getValue(), tile.getValue());
    }
}