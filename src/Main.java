import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("MathDoku");

        // Creates a game title.
        Label title = new Label("MathDoku");
        title.setFont(new Font(40));

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
        Label mistakesLabel = new Label("Click to check the grid:");
        CheckBox mistakesCheck = new CheckBox();

        optionsHBox.getChildren().addAll(undoButton, redoButton, clearButton, loadFileButton, loadTextButton, mistakesLabel, mistakesCheck);

        // Sets grid pane size parameters.
        int gridSize = 6;
        int tileSize = 100;

        // Creates a class for the grid and change history.
        Grid grid = new Grid(gridSize, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),  new History(undoButton, redoButton));

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
                            grid.getHistory().addMove(new Change(grid.getSelected(), key.getCode() - 48));
                            grid.getSelected().displayNumber(key.getCode() - 48);
                        }
                    }
                } else {
                    // Removes the value.
                    grid.getHistory().addMove(new Change(grid.getSelected(), 0));
                    grid.getSelected().displayNumber(0);
                }
                grid.getCheckMistake().shouldCheck();
                grid.selectTile(grid.getSelected());
            } catch (NullPointerException e) {
                System.out.println("No tile has been selected.");
            }
        });

        // The lambda expression for undoing a change.
        undoButton.setOnMouseClicked(mouseEvent -> grid.getHistory().undo());

        // The lambda expression for redoing a change.
        redoButton.setOnMouseClicked(mouseEvent -> grid.getHistory().redo());

        // The event handler code for clearing the grid.
        clearButton.setOnAction(actionEvent -> {
            // Displays a confirmation alert.
            Alert alertClear = new Alert(Alert.AlertType.CONFIRMATION);
            alertClear.setTitle("Clear Grid");
            alertClear.setHeaderText("Clear Grid");
            alertClear.setContentText("Are you sure you want to clear the grid?");

            alertClear.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        for (Tile tile : grid.getTiles()) {
                            tile.displayNumber(0);
                            grid.getHistory().clearHistory();
                        }
                    });
        });

        // The event handler code for loading a game from a file.
        loadFileButton.setOnAction(actionEvent -> {
            // Configures the file chooser menu.
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Game File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            // Gets the selected file.
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                grid.readFile(selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // The event handler code for loading a game from a text input.
        loadTextButton.setOnAction(actionEvent -> {
            // Configures the dialog box.
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Enter Game Text");
            dialog.setHeaderText("Enter Game Text");

            // Adds buttons to the dialog box.
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Formats a grid for the dialog box.
            GridPane dialogPane = new GridPane();
            dialogPane.setAlignment(Pos.CENTER);
            dialogPane.setPadding(new Insets(10, 10, 10, 10));

            // Adds a text field in the dialog box.
            TextArea textArea = new TextArea();
            dialogPane.getChildren().add(textArea);
            dialog.getDialogPane().setContent(dialogPane);

            // Reads the text when the OK.
            dialog.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> grid.readText(textArea.getText()));
        });

        // The event handler code for selecting to check the mistakes.
        mistakesCheck.setOnMouseClicked(mouseEvent -> {
            grid.getCheckMistake().setChecked(mistakesCheck.isSelected());
            grid.getCheckMistake().shouldCheck();
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
            numButton.setFont(new Font(25));
            buttonHBox.getChildren().add(numButton);

            // The event handler code for pressing a number button.
            numButton.setOnMouseClicked(mouseEvent -> {
                // Displays the number on the tile.
                try {
                    grid.getHistory().addMove(new Change(grid.getSelected(), Integer.parseInt(numButton.getText())));
                    grid.getSelected().displayNumber(Integer.parseInt(numButton.getText()));
                    grid.getCheckMistake().shouldCheck();
                    grid.selectTile(grid.getSelected());
                } catch (NullPointerException e) {
                    System.out.println("No tile has been selected.");
                }
            });
        }
        grid.findTiles();

        // Finishes setting up the GUI.
        mainPane.add(title, 0, 0);
        mainPane.add(optionsHBox, 0, 1);
        mainPane.add(gridPane, 0, 2);
        mainPane.add(buttonHBox, 0, 3);
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        // Launches the game.
        launch(args);
    }
}
