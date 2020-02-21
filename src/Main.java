import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MathDoku");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setAlignment(Pos.CENTER);

        int gridSize = 5;
        int boxSize = 100;

        // Creates the grid.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                // Creates a box on the grid.
                Rectangle gridBox = new Rectangle(boxSize, boxSize);
                gridBox.setStroke(Color.BLACK);
                gridBox.setFill(Color.WHITE);

                gridPane.add(gridBox, x, y + 1);
            }
        }

        // Creates the action bar at the top.
        HBox optionsHBox = new HBox(5);
        optionsHBox.setPadding(new Insets(5, 5, 5, 5));
        optionsHBox.setAlignment(Pos.CENTER);

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Button loadFileButton = new Button("Load from File");
        Button loadTextButton = new Button("Load from Text");
        Label mistakesLabel = new Label("Click to show mistakes:");
        CheckBox mistakesCheck = new CheckBox();
        optionsHBox.getChildren().addAll(undoButton, redoButton, loadFileButton, loadTextButton, mistakesLabel, mistakesCheck);
        gridPane.add(optionsHBox, 0, 0, gridSize, 1);

        stage.setScene(new Scene(gridPane));
        stage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
