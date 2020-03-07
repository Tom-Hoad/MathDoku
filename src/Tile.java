import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

// The subclass for a single tile in the grid.
public class Tile extends StackPane implements Comparable<Tile> {
    private int gridPosition;
    private int value;
    private int row;
    private int column;
    private int fontSize;
    private String style;
    private String border;

    // The Tile subclass constructor.
    public Tile(int gridPosition) {
        this.gridPosition = gridPosition;
        this.value = 0;
        this.getChildren().add(0, new Label(""));
        this.style = "-fx-border-color: black; ";
        this.fontSize = 50;
    }

    // Defaults the tile.
    public void setDefault() {
        if (border != null) {
            this.setStyle(style + border);
        } else {
            this.setStyle(style);
        }
    }

    // Adds border to the tile.
    public void addBorder(String border) {
        this.border = border;
        setDefault();
    }

    // Highlights the tile when selected.
    public void setSelected() {
        this.setStyle("-fx-border-color: red; -fx-border-width: 4; ");
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

    // Adds a number to the tile.
    public void displayNumber(int tileValue) {
        this.value = tileValue;

        getChildren().remove(0);
        Label label = new Label();

        if (tileValue != 0) {
            label.setText(String.valueOf(tileValue));
        } else {
            label.setText("");
        }

        label.setFont(new Font(fontSize));
        getChildren().add(0, label);
    }

    // Gets the row the tiles on.
    public int getRow() {
        return row;
    }

    // Sets the row the tiles on.
    public void setRow(int row) {
        this.row = row;
    }

    // Gets the column the tiles on.
    public int getColumn() {
        return column;
    }

    // Sets the column the tiles on.
    public void setColumn(int column) {
        this.column = column;
    }

    // Sets the font size.
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;

        getChildren().remove(0);
        Label label = new Label();

        if (value != 0) {
            label.setText(String.valueOf(value));
        } else {
            label.setText("");
        }

        label.setFont(new Font(fontSize));
        getChildren().add(0, label);
    }

    // Compares the value of two tiles.
    @Override
    public int compareTo(Tile tile) {
        return Integer.compare(getValue(), tile.getValue());
    }
}