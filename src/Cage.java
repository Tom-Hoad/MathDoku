import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.ArrayList;

// The class for a cage on the grid.
public class Cage {
    private Grid grid;
    private int result;
    private String operation;
    private ArrayList<Tile> cageTiles;

    // The cage class constructor.
    public Cage(Grid grid, int result, String operation, String[] tilePositions) {
        this.grid = grid;
        this.result = result;
        this.operation = operation;

        this.cageTiles = new ArrayList<>();
        for (String tilePosition : tilePositions) {
            cageTiles.add(grid.getTiles().get(Integer.parseInt(tilePosition) - 1));
        }
    }

    // Shows the cage on the grid.
    public void showCage() {
        // Gets the top left most tile.
        Tile firstTile = cageTiles.get(0);

        // Displays the cage requirement.
        Label label = new Label(result + "," + operation);
        label.setFont(new Font(18));
        firstTile.getChildren().add(1, label);
        StackPane.setAlignment(firstTile.getChildren().get(1), Pos.TOP_LEFT);

        // Adds borders to the sides of tiles not adjacent to the cage.
        for (Tile cageTile : cageTiles) {
            String tileStyle = "-fx-border-width: " +
                    findAdjacent(false, cageTile.getGridPosition() - grid.getSize()) +
                    findAdjacent(false, cageTile.getGridPosition() + 1) +
                    findAdjacent(false, cageTile.getGridPosition() + grid.getSize()) +
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

    // Gets the desired result of a cage.
    public int getResult() {
        return result;
    }

    // Gets the operation of the cage.
    public String getOperation() {
        return operation;
    }

    // Gets the array of tiles in the cage.
    public ArrayList<Tile> getCageTiles() {
        return cageTiles;
    }
}
