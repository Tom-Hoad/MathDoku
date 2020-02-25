import javafx.scene.control.Label;

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
        firstTile.getChildren().add(1, label);

        // Displays the border of the cage.
        for (Tile cageTile : cageTiles) {
            StringBuilder tileStyle = new StringBuilder("-fx-border-color: black;" +
                                                        "-fx-border-width: ");

            // Adds borders to the sides of tiles not adjacent to the cage.
            tileStyle.append(findAdjacent(false, cageTile.getGridPosition() - gridSize));
            tileStyle.append(findAdjacent(false, cageTile.getGridPosition() + 1));
            tileStyle.append(findAdjacent(false, cageTile.getGridPosition() + gridSize));
            tileStyle.append(findAdjacent(false, cageTile.getGridPosition() - 1));

            cageTile.setStyle(tileStyle.toString());
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
