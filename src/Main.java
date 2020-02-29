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
import java.lang.reflect.Array;
import java.util.ArrayList;
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

        // Creates an object for the grid.
        Grid grid = new Grid(gridSize, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Adds event handlers for numbering and selecting tiles.
        mainPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(grid, mistakesCheck));
        mainPane.addEventHandler(MouseEvent.MOUSE_CLICKED, new MistakeCheckHandler(grid, mistakesCheck));

        // Creates the grid pane.
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        // Fills the grid with tiles.
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // Styles the tile.
                Tile tile = new Tile((i * gridSize) + (j + 1));
                tile.setPrefSize(tileSize, tileSize);
                tile.setDefault();

                // Adds the tile to the grid.
                grid.addToRow(j, tile);
                grid.addToColumn(i, tile);
                gridPane.add(tile, j, i);

                tile.addEventHandler(MouseEvent.MOUSE_CLICKED, new TileClickHandler(tile, mistakesCheck));
            }

            // Adds number buttons to below the grid.
            Button numButton = new Button(String.valueOf(i + 1));
            numButton.setPrefSize(50, 50);
            numButton.setFont(new Font(30));
            numButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new NumberButtonHandler(String.valueOf(i + 1), grid, mistakesCheck));
            buttonHBox.getChildren().add(numButton);
        }
        grid.findTiles();

        // Reads in the cages for the example grid - TEMPORARY CODE
        Scanner reader = new Scanner(new File("example.txt"));
        while (reader.hasNextLine()) {
            grid.addCage(new Cage(grid, reader.nextLine()));
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
    public Tile getSelected(Grid grid) {
        for (Tile tile : grid.getTiles()) {
            if (tile.isSelected()) {
                return tile;
            }
        }
        return null;
    }

    // Adds a number to the tile.
    public void displayNumber(Grid grid, String number, CheckBox mistakesCheck) {
        getSelected(grid).displayValue(number);

        // Checks for mistakes.
        if (mistakesCheck.isSelected()) {
            CheckMistake checkMistake = new CheckMistake(grid);

            // Tells the user if they have won or not.
            if (checkMistake.checkGrid()) {
                System.out.println("You've won!");
            } else {
                System.out.println("There are some mistakes...");
            }

            getSelected(grid).selectTile();
        }
    }

    // Event handler code for click a tile.
    class TileClickHandler implements EventHandler<MouseEvent> {
        private Grid grid;
        private Tile tile;
        private CheckBox mistakesCheck;

        public TileClickHandler(Grid grid, Tile tile, CheckBox mistakesCheck) {
            this.grid = grid;
            this.tile = tile;
            this.mistakesCheck = mistakesCheck;
        }

        @Override
        public void handle(MouseEvent event) {
            try {
                // Checks for mistakes.
                if (mistakesCheck.isSelected()) {
                    CheckMistake checkMistake = new CheckMistake(grid, cages);

                    // Tells the user if they have won or not.
                    if (checkMistake.checkGrid()) {
                        System.out.println("You've won!");
                    }
                }

                // Defaults the selected tile and selects the new tile.
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
        private ArrayList<Tile> tiles;
        private ArrayList<Cage> cages;
        private CheckBox mistakesCheck;

        public KeyPressedHandler(int gridSize, ArrayList<Tile> tiles, ArrayList<Cage> cages, CheckBox mistakesCheck) {
            this.gridSize = gridSize;
            this.tiles = tiles;
            this.cages = cages;
            this.mistakesCheck = mistakesCheck;
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
                            displayNumber(gridSize, key.toString().substring(5), getSelected(tiles), gridSize, rows, columns, cages, mistakesCheck);
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
        private int gridSize;
        private int number;
        private ArrayList<Row> rows;
        private ArrayList<Column> columns;
        private ArrayList<Cage> cages;
        private CheckBox mistakesCheck;

        public NumberButtonHandler(int gridSize, int number, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages, CheckBox mistakesCheck) {
            this.gridSize = gridSize;
            this.number = number;
            this.rows = rows;
            this.columns = columns;
            this.cages = cages;
            this.mistakesCheck = mistakesCheck;
        }

        @Override
        public void handle(MouseEvent event) {
            // Displays the number on the tile.
            try {
                displayNumber(gridSize, String.valueOf(number), getSelected(tiles), gridSize, rows, columns, cages, mistakesCheck);
            } catch (NullPointerException e) {
                System.out.println("No tile has been selected.");
            }
        }
    }

    // The event handler for selecting to check the mistakes.
    class MistakeCheckHandler implements EventHandler<MouseEvent> {
        private int gridSize;
        private ArrayList<Row> rows;
        private ArrayList<Column> columns;
        private ArrayList<Cage> cages;
        private CheckBox mistakesCheck;

        public MistakeCheckHandler(int gridSize, ArrayList<Row> rows, ArrayList<Column> columns, ArrayList<Cage> cages, CheckBox mistakesCheck) {
            this.gridSize = gridSize;
            this.rows = rows;
            this.columns = columns;
            this.cages = cages;
            this.mistakesCheck = mistakesCheck;
        }

        @Override
        public void handle(MouseEvent event) {
            if (mistakesCheck.isSelected()) {
                CheckMistake checkMistake = new CheckMistake(gridSize, rows, columns, cages);

                // Tells the user if they have won or not.
                if (checkMistake.checkGrid()) {
                    System.out.println("You've won!");
                } else {
                    System.out.println("There are some mistakes...");
                }
            }
            // NEEDS TO CORRECT EVERYTHING
        }
    }

    public static void main(String[] args) {
        // Launches the game.
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
