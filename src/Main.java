import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
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
        Button clearButton = new Button("Clear Grid");
        Button loadFileButton = new Button("Load from File");
        Button loadTextButton = new Button("Load from Text");
        Label mistakesLabel = new Label("Click to show mistakes:");
        CheckBox mistakesCheck = new CheckBox();
        optionsHBox.getChildren().addAll(undoButton, redoButton, clearButton, loadFileButton, loadTextButton, mistakesLabel, mistakesCheck);

        // Sets grid pane size parameters.
        int gridSize = 6;
        int tileSize = 100;

        // Creates an object for the grid.
        Grid grid = new Grid(gridSize, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Event handler code for pressing a key.
        mainPane.setOnKeyPressed(keyEvent -> {
            // Returns the number pressed.
            KeyCode key = keyEvent.getCode();
            KeyCode[] codes = {KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4,
                    KeyCode.DIGIT5, KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8};

            // Displays the number on the tile.
            try {
                if (key != KeyCode.BACK_SPACE) {
                    for (int i = 0; i <= grid.getSize() - 1; i++) {
                        if (key == codes[i]) {
                            grid.getSelected().displayNumber(grid, key.toString().substring(5), mistakesCheck);
                        }
                    }
                } else {
                    // Removes the value.
                    grid.getSelected().displayNumber(grid, "", mistakesCheck);
                }
            } catch (NullPointerException e) {
                System.out.println("No tile has been selected.");
            }
        });

        // The event handler code for clearing the grid.
        clearButton.setOnAction(actionEvent -> {
            // Displays a confirmation alert.
            Alert alertClear = new Alert(Alert.AlertType.CONFIRMATION);
            alertClear.setTitle("Clear Grid");
            alertClear.setHeaderText("Clear Grid");
            alertClear.setContentText("Are you sure you want to clear the grid?");
            alertClear.show();

            // Clears the grid if accepted the confirmation.
            for (Tile tile : grid.getTiles()) {
                tile.displayNumber(grid, "", mistakesCheck);
            }
        });

        // The event handler for selecting to check the mistakes.
        mistakesCheck.setOnMouseClicked(mouseEvent -> {
            // Checks mistake.
            if (mistakesCheck.isSelected()) {
                CheckMistake checkMistake = new CheckMistake(grid);

                // Tells the user if they have won or not.
                if (checkMistake.checkGrid()) {
                    System.out.println("You've won!");
                } else {
                    System.out.println("There are some mistakes...");
                }
            } else {
                for (Tile tile : grid.getTiles()) {
                    tile.correctTile();
                }
            }
            grid.selectTile(grid.getSelected());
        });

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

                // Event handler code for click a tile.
                tile.setOnMouseClicked(mouseEvent -> {
                    try {
                        // Defaults the selected tile and selects the new tile.
                        grid.getSelected().setDefault();
                        grid.selectTile(tile);
                    } catch (NullPointerException e) {
                        grid.selectTile(tile);
                    }
                });
            }

            // Adds number buttons to below the grid.
            Button numButton = new Button(String.valueOf(i + 1));
            numButton.setFont(new Font(30));
            buttonHBox.getChildren().add(numButton);

            // The event handler code for pressing a number button.
            numButton.setOnMouseClicked(mouseEvent -> {
                // Displays the number on the tile.
                try {
                    grid.getSelected().displayNumber(grid, numButton.getText(), mistakesCheck);
                } catch (NullPointerException e) {
                    System.out.println("No tile has been selected.");
                }
            });
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

    public static void main(String[] args) {
        // Launches the game.
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
