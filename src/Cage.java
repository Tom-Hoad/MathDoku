import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

// The class for a cage on the grid.
public class Cage {
    private int gridSize;
    private String result;
    private String operation;
    private Tile[] cageTiles;

    // The cage class constructor.
    public Cage(int gridSize, String cageDefinition, Tile[] tiles) {
        this.gridSize = gridSize;

        int cageSplit = cageDefinition.indexOf(" ");
        this.result = cageDefinition.substring(0, cageSplit - 1);
        this.operation = cageDefinition.substring(cageSplit - 1, cageSplit);

        // Finds the tiles that belong to the cage.
        String[] tilePositions = cageDefinition.substring(cageSplit + 1).split(",");
        this.cageTiles = new Tile[tilePositions.length];
        for (int i = 0; i < tilePositions.length; i++) {
            cageTiles[i] = tiles[Integer.parseInt(tilePositions[i]) - 1];
        }
    }

    // Shows the cage on the grid.
    public void showCage() {
        // Gets the top left most tile.
        Tile firstTile = new Tile(100);
        for (Tile cageTile : cageTiles) {
            if (cageTile.getGridPosition() < firstTile.getGridPosition()) {
                firstTile = cageTile;
            }
        }

        // Displays the cage requirement.
        Label label = new Label(result + "," + operation);
        label.setFont(new Font(18));
        firstTile.getChildren().add(1, label);
        StackPane.setAlignment(firstTile.getChildren().get(1), Pos.TOP_LEFT);

        // Adds borders to the sides of tiles not adjacent to the cage.
        for (Tile cageTile : cageTiles) {
            String tileStyle = "-fx-border-width: " +
                    findAdjacent(false, cageTile.getGridPosition() - gridSize) +
                    findAdjacent(false, cageTile.getGridPosition() + 1) +
                    findAdjacent(false, cageTile.getGridPosition() + gridSize) +
                    findAdjacent(false, cageTile.getGridPosition() - 1);
            cageTile.addBorder(tileStyle);
        }
    }

    // Checks if there is an adjacent tile.
    public String findAdjacent(boolean found, int positionAdjacent) {
        for (Tile adjacentTile : cageTiles) {
            if (adjacentTile.getGridPosition() == positionAdjacent) {
                found = true;
                break;
            }
        }
        if (found) {
            return "1 ";
        } else {
            return "4 ";
        }
    }
}
