import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("MathDoku");
        Pane pane = new Pane();

        int gridSize = 5;
        int boxSize = 100;

        // Creates the grid.
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Rectangle gridBox = new Rectangle(boxSize, boxSize);

                // Determines how a box looks.
                gridBox.setStroke(Color.BLACK);
                gridBox.setFill(Color.WHITE);
                gridBox.relocate(x * boxSize, y * boxSize);

                // Deals with block clicks.
                gridBox.setId((x+1) + "," + (y+1));
                gridBox.setOnMouseClicked(t -> System.out.println("Box at " + gridBox.getId() + " has been clicked."));

                pane.getChildren().add(gridBox);
            }
        }

        stage.setScene(new Scene(pane));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
