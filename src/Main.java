import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

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

                // Deals with block clicks.
                gridBoxes[x][y] = gridBox;
                gridBox.addEventHandler(MouseEvent.MOUSE_CLICKED, new BoxClickHandler(gridBoxes, x, y));

                gridPane.add(gridBox, x, y + 1);
            }
        }
        gridPane.add(optionsHBox, 0, 0, gridSize, 1);

        // Finishes setting up the GUI.
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }

    // Event handler code for click a grid button.
    static class BoxClickHandler implements EventHandler<MouseEvent> {
        private Rectangle[][] gridBoxes;
        private int x;
        private int y;

        // Event handler constructor.
        public BoxClickHandler(Rectangle[][] gridBoxes, int x, int y) {
            this.gridBoxes = gridBoxes;
            this.x = x;
            this.y = y;
        }

        @Override
        public void handle(MouseEvent event) {
            // Highlights the currently selected grid box.
            for (int i = 0; i < gridBoxes.length; i++) {
                for (int j = 0; j < gridBoxes.length; j++) {
                    if (i == x && j == y) {
                        gridBoxes[i][j].setStrokeWidth(2.5);
                    } else {
                        gridBoxes[i][j].setStrokeWidth(1.0);
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
