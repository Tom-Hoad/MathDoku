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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.security.Key;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MathDoku");

        // Creates the action bar at the top.
        HBox optionsHBox = new HBox(5);
        optionsHBox.setPadding(new Insets(5, 5, 5, 5));
        optionsHBox.setAlignment(Pos.CENTER);

        // Creates functionality elements to the action bar.
        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button loadFileButton = new Button("Load from File");
        Button loadTextButton = new Button("Load from Text");
        Label mistakesLabel = new Label("Click to show mistakes:");
        CheckBox mistakesCheck = new CheckBox();

        optionsHBox.getChildren().addAll(undoButton, redoButton, loadFileButton, loadTextButton, mistakesLabel, mistakesCheck);

        // Sets size parameters.
        int gridSize = 6;
        int boxSize = 100;

        // Creates the pane.
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setAlignment(Pos.CENTER);

        Rectangle[][] gridBoxes = new Rectangle[gridSize][gridSize];
        gridPane.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressedHandler(gridSize));

        // Creates the grid.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                // Creates a box on the grid.
                Rectangle gridBox = new Rectangle(boxSize, boxSize);

                // Determines how a box looks.
                gridBox.setStroke(Color.BLACK);
                gridBox.setStrokeType(StrokeType.INSIDE);
                gridBox.setFill(Color.WHITE);
                gridBox.relocate(x * boxSize, y * boxSize);
                gridPane.add(gridBox, x, y + 1);

                // Deals with block clicks.
                gridBoxes[x][y] = gridBox;
                gridBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new BoxClickHandler(gridBoxes, x, y, gridPane));
            }
        }
        gridPane.add(optionsHBox, 0, 0, gridSize, 1);

        // Finishes setting up the GUI.
        Scene scene = new Scene(gridPane);

        stage.setScene(scene);
        stage.show();
    }

    // Event handler code for clicking a grid box.
    class BoxClickHandler implements EventHandler<MouseEvent> {
        private Rectangle[][] gridBoxes;
        private int x;
        private int y;
        private int gridSize;
        private GridPane gridPane;

        // Event handler constructor.
        public BoxClickHandler(Rectangle[][] gridBoxes, int x, int y, GridPane gridPane) {
            this.gridBoxes = gridBoxes;
            this.x = x;
            this.y = y;
            this.gridSize = gridBoxes.length;
            this.gridPane = gridPane;
        }

        @Override
        public void handle(MouseEvent event) {
            // Highlights the currently selected grid box.
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    Rectangle gridBox = gridBoxes[i][j];

                    if (i == x && j == y) {
                        gridBox.setStroke(Color.RED);
                        gridBox.setStrokeWidth(2.5);
                    } else if (gridBox.getStrokeWidth() != 1.0) {
                        gridBox.setStroke(Color.BLACK);
                        gridBox.setStrokeWidth(1.0);
                    }
                }
            }
        }
    }

    // Event handler code for pressing a key.
    class KeyPressedHandler implements EventHandler<KeyEvent> {
        private int gridSize;

        public KeyPressedHandler(int gridSize) {
            this.gridSize = gridSize;
        }

        @Override
        public void handle(KeyEvent event) {
            // Returns the number pressed.
            KeyCode key = event.getCode();
            KeyCode[] codes = {KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
                                KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8};

            for (int i = 0; i <= gridSize - 2; i++) {
                if (key == codes[i]) {
                    System.out.println("A valid number key was pressed.");
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
