import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MathDoku");

        // Sets size parameters.
        int gridSize = 6;
        int boxSize = 100;

        // Creates the pane.
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setAlignment(Pos.CENTER);

        // Creates the canvas.
        final Canvas canvas = new Canvas(gridSize * boxSize, gridSize * boxSize);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Creates the grid.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                // Creates a box on the grid.
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * boxSize, y * boxSize, boxSize, boxSize);
            }
        }
        gridPane.add(canvas, 0, 1);

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
        gridPane.add(optionsHBox, 0, 0);

        // Finishes setting up the GUI.
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
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
