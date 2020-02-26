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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

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

        // Creates the button bar at the bottom.
        HBox buttonHBox = new HBox(5);
        buttonHBox.setPadding(new Insets(10, 5, 5, 5));
        buttonHBox.setAlignment(Pos.CENTER);

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

        // Creates a 2d array of all tiles and an array list of cages.
        Tile[] tiles = new Tile[gridSize*gridSize];
        ArrayList<Cage> cages = new ArrayList<>();
        mainPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(gridSize, tiles));
        mistakesCheck.addEventHandler(MouseEvent.MOUSE_CLICKED, new MistakeCheckHandler(mistakesCheck, gridSize, tiles, cages));

        // Creates the grid pane.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        // Fills the grid with tiles.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Tile tile = new Tile((x + 1) + (y * gridSize));
                tile.setPrefSize(tileSize, tileSize);
                tile.setDefault();
                tiles[x + (gridSize * y)] = tile;
                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new TileClickHandler(tile, tiles));
                gridPane.add(tile, x, y);
            }

            // Adds number buttons to below the grid.
            Button numButton = new Button(String.valueOf(x + 1));
            numButton.setPrefSize(50, 50);
            numButton.setFont(new Font(30));
            numButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new NumberButtonHandler(x + 1, tiles));
            buttonHBox.getChildren().add(numButton);
        }
        // Reads in the cages for the example grid - TEMPORARY CODE
        Scanner reader = new Scanner(new File("example.txt"));
        while (reader.hasNextLine()) {
            Cage cage = new Cage(gridSize, reader.nextLine(), tiles);
            cage.showCage();
            cages.add(cage);
        }

        // Finishes setting up the GUI.
        mainPane.add(optionsHBox, 0, 0);
        mainPane.add(gridPane, 0, 1);
        mainPane.add(buttonHBox, 0, 2);
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // Returns the selected tile.
    public Tile getSelected(Tile[] tiles) {
        for (Tile tile : tiles) {
            if (tile.isSelected()) {
                return tile;
            }
        }
        return null;
    }

    // Adds a number to the tile.
    public void displayNumber(Tile selectedTile, String number) {
        selectedTile.getChildren().remove(0);
        selectedTile.setValue(Integer.parseInt(number));
        Label label = new Label(number);
        label.setFont(new Font(50));
        selectedTile.getChildren().add(0, label);
    }

    // Event handler code for click a tile.
    class TileClickHandler implements EventHandler<MouseEvent> {
        private Tile tile;
        private Tile[] tiles;

        public TileClickHandler(Tile tile, Tile[] tiles) {
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
        private Tile[] tiles;

        public KeyPressedHandler(int gridSize, Tile[] tiles) {
            this.gridSize = gridSize;
            this.tiles = tiles;
        }

        @Override
        public void handle(KeyEvent event) {
            // Returns the number pressed.
            KeyCode key = event.getCode();
            KeyCode[] codes = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4,
                               KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8};

            // Displays the number on the tile.
            try {
                if (key != KeyCode.BACK_SPACE) {
                    for (int i = 0; i <= gridSize - 1; i++) {
                        if (key == codes[i]) {
                            displayNumber(getSelected(tiles), key.toString().substring(5));
                        }
                    }
                } else {
                    // Removes the value.
                    Tile selectedTile = getSelected(tiles);
                    selectedTile.setValue(0);
                    selectedTile.getChildren().remove(0);
                    selectedTile.getChildren().add(0, new Label(""));
                }
            } catch (NullPointerException e) {
                System.out.println("No tile has been selected.");
            }
        }
    }

    // Event handler code for pressing a number button.
    class NumberButtonHandler implements EventHandler<MouseEvent> {
        private int number;
        private Tile[] tiles;

        public NumberButtonHandler(int number, Tile[] tiles) {
            this.number = number;
            this.tiles = tiles;
        }

        @Override
        public void handle(MouseEvent event) {
            // Displays the number on the tile.
            try {
                displayNumber(getSelected(tiles), String.valueOf(number));
            } catch (NullPointerException e) {
                System.out.println("No tile has been selected.");
            }
        }
    }

    // The event handler for selecting to check the mistakes.
    class MistakeCheckHandler implements EventHandler<MouseEvent> {
        private CheckBox mistakeCheck;
        private int gridSize;
        private Tile[] tiles;
        private ArrayList<Cage> cages;

        public MistakeCheckHandler(CheckBox mistakeCheck, int gridSize, Tile[] tiles, ArrayList<Cage> cages) {
            this.mistakeCheck = mistakeCheck;
            this.gridSize = gridSize;
            this.tiles = tiles;
            this.cages = cages;
        }

        @Override
        public void handle(MouseEvent event) {
            if (mistakeCheck.isSelected()) {
                boolean correct = true;

                // Creates the expected hash.
                int[] expectedTiles = new int[gridSize];
                for (int i = 0; i < gridSize; i++) {
                    expectedTiles[i] = i + 1;
                }
                int expectedHash = Arrays.hashCode(expectedTiles);

                // Check rows if correct.
                for (int i = 0; i < gridSize; i++) {
                    Tile[] rowTiles = Arrays.copyOfRange(tiles, (i * gridSize), ((i + 1) * gridSize));

                    // Gets values of the row.
                    int[] actualRow = new int[rowTiles.length];
                    for (int j = 0; j < actualRow.length; j++) {
                        actualRow[j] = rowTiles[j].getValue();
                    }
                    Arrays.sort(actualRow);

                    // Checks if the hashes match.
                    if (Arrays.hashCode(actualRow) != expectedHash) {
                        correct = false;
                        break;
                    }
                }

                // Checks columns if correct.
                for (int i = 0; i < gridSize; i++) {
                    // Gets the arrays of columns.
                    int[] actualColumn = new int[gridSize];
                    int count = 0;
                    for (Tile columnTile : tiles) {
                        if ((columnTile.getGridPosition() - 1) % gridSize == i) {
                            actualColumn[count] = columnTile.getValue();
                            count++;
                        }
                    }
                    Arrays.sort(actualColumn);

                    // Checks if the hashes match.
                    if (Arrays.hashCode(actualColumn) != expectedHash) {
                        correct = false;
                        break;
                    }
                }

                // Checks cages if correct.
                for (Cage cage : cages) {
                    int givenResult = 0;
                    switch(cage.getOperation()) {
                        // Add values.
                        case "+":
                            for (Tile tile : cage.getCageTiles()) {
                                givenResult += tile.getValue();
                            }
                            break;
                        // Multiply values.
                        case "x":
                            givenResult = 1;
                            for (Tile tile : cage.getCageTiles()) {
                                givenResult = givenResult * tile.getValue();
                            }
                            break;
                        // Minus values.
                        case "-":
                            break;
                        // Divide values.
                        case "÷":
                            break;
                    }
                    if (givenResult != cage.getResult()) {
                        correct = false;
                    }
                }

                // Tells the user if they have won or not.
                if (correct) {
                    System.out.println("You've won!");
                } else {
                    System.out.println("There are some mistakes...");
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
