import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MathDoku");

        // Creates the main pane.
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setAlignment(Pos.CENTER);

        // Creates the action bar at the top.
        HBox optionsHBox = new HBox(5);
        optionsHBox.setPadding(new Insets(5, 5, 10, 5));
        optionsHBox.setAlignment(Pos.CENTER);

        // Adds functionality elements to the action bar.
        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button loadFileButton = new Button("Load from File");
        Button loadTextButton = new Button("Load from Text");
        Label mistakesLabel = new Label("Click to show mistakes:");
        CheckBox mistakesCheck = new CheckBox();
        optionsHBox.getChildren().addAll(undoButton, redoButton, loadFileButton, loadTextButton, mistakesLabel, mistakesCheck);

        // Sets grid pane size parameters.
        int gridSize = 6;
        int tileSize = 100;

        // Creates a 2d array of all tiles.
        Tile[][] tiles = new Tile[gridSize][gridSize];

        // Creates the grid pane.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        // Fills the grid with tiles.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Tile tile = new Tile(x, y, gridSize);
                tile.setPrefSize(tileSize, tileSize);
                tile.setDefault();
                tiles[x][y] = tile;
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new TileClickHandler(tile, tiles));
                gridPane.add(tile, x, y);
            }
        }
        mainPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(gridSize, tiles));

        // Finishes setting up the GUI.
        mainPane.add(optionsHBox, 0, 0);
        mainPane.add(gridPane, 0, 1);
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // Returns the selected tile.
    public Tile getSelected(Tile[][] tiles) {
        for (Tile[] tile : tiles) {
            for (int i = 0; i < tiles.length; i++) {
                if (tile[i].isSelected()) {
                    return tile[i];
                }
            }
        }
        return null;
    }

    // Event handler code for click a tile.
    class TileClickHandler implements EventHandler<MouseEvent> {
        private Tile tile;
        private Tile[][] tiles;

        public TileClickHandler(Tile tile, Tile[][] tiles) {
            this.tile = tile;
            this.tiles = tiles;
        }

        @Override
        public void handle(MouseEvent event) {
            // Defaults the selected tile and selects the new tile.
            try {
                getSelected(tiles).setDefault();
                tile.selectTile();
            } catch (NullPointerException e) {
                tile.selectTile();
            }

        }
    }

    // Event handler code for pressing a key.
    class KeyPressedHandler implements EventHandler<KeyEvent> {
        private int gridSize;
        private Tile[][] tiles;

        public KeyPressedHandler(int gridSize, Tile[][] tiles) {
            this.gridSize = gridSize;
            this.tiles = tiles;
        }

        @Override
        public void handle(KeyEvent event) {
            // Returns the number pressed.
            KeyCode key = event.getCode();
            KeyCode[] codes = {KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
                    KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8};

            // Displays the number on the tile.
            for (int i = 0; i <= gridSize - 2; i++) {
                if (key == codes[i]) {
                    try {
                        Tile selectedTile = getSelected(tiles);
                        selectedTile.getChildren().clear();
                        Label label = new Label(key.toString().substring(5));
                        label.setFont(new Font(50));
                        selectedTile.getChildren().add(label);
                    } catch (NullPointerException e) {
                        System.out.println("No tile has been selected.");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Launches the game.
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
