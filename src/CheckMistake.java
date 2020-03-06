import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

// The class for checking mistakes.
public class CheckMistake {
    private Grid grid;
    private boolean isChecked;

    // The check mistake class constructor.
    public CheckMistake(Grid grid) {
        this.grid = grid;
        this.isChecked = false;
    }

    // Sets the result of the mistake checkbox.
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    // Checks if it should check the grid.
    public void shouldCheck() {
        if (isChecked) {
            // Tells the user if they have won or not.
            if (checkGrid()) {
                Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
                winAlert.setTitle("Congratulations!");
                winAlert.setHeaderText("Congratulations!");
                winAlert.setContentText("You've won this game of MathDoku!");

                winAlert.show();
                correctTiles();
            }
        } else {
            correctTiles();
        }
    }

    // Corrects all the tiles.
    public void correctTiles() {
        for (Tile tile : grid.getTiles()) {
            tile.correctTile();
        }
    }

    // Checks the grid for mistakes.
    public boolean checkGrid() {
        boolean correct = true;

        // Checks cages if correct.
        for (Cage cage : grid.getCages()) {
            boolean zeroError = false;
            boolean cageError = false;

            // Creates an array list of all values.
            ArrayList<Integer> cageValues = new ArrayList<>();
            for (Tile tile : cage.getCageTiles()) {
                if (tile.getValue() != 0) {
                    cageValues.add(tile.getValue());
                } else {
                    zeroError = true;
                }
            }

            // Checks if empty.
            if (zeroError) {
                for (Tile tile : cage.getCageTiles()) {
                    tile.setMistake();
                }
                continue;
            } else {
                for (Tile tile : cage.getCageTiles()) {
                    tile.correctTile();
                }
            }

            int givenResult = 0;
            int expectedResult = cage.getResult();

            switch(cage.getOperation()) {
                // Add values.
                case "+":
                    for (int tileValue : cageValues) {
                        givenResult += tileValue;
                    }
                    if (givenResult != expectedResult) {
                        cageError = true;
                    }
                    break;
                // Multiply values.
                case "x":
                    givenResult = 1;
                    for (int tileValue : cageValues) {
                        givenResult = givenResult * tileValue;
                    }
                    if (givenResult != expectedResult) {
                        cageError = true;
                    }
                    break;
                // Minus values.
                case "-":
                    cageValues.sort(Collections.reverseOrder());
                    givenResult = cageValues.get(0) * 2;
                    for (int value : cageValues) {
                        givenResult -= value;
                    }

                    if (givenResult != expectedResult) {
                        cageError = true;
                    }
                    break;
                // Divide values.
                case "÷":
                    cageValues.sort(Collections.reverseOrder());
                    givenResult = cageValues.get(0) * cageValues.get(0);
                    for (int value : cageValues) {
                        givenResult = givenResult / value;
                    }

                    if (givenResult != expectedResult) {
                        cageError = true;
                    }
                    break;
            }

            // Marks the cage as incorrect.
            if (cageError) {
                for (Tile cageTile : cage.getCageTiles()) {
                    cageTile.setMistake();
                }
                correct = false;
            }
        }

        // Creates the expected hash.
        int[] expectedTiles = new int[grid.getSize()];
        for (int i = 0; i < grid.getSize(); i++) {
            expectedTiles[i] = i + 1;
        }
        int expectedHash = Arrays.hashCode(expectedTiles);

        // Checks rows if correct.
        for (Row row : grid.getRows()) {
            ArrayList<Tile> rowTiles = row.getRowTiles();

            int[] rowValues = new int[grid.getSize()];
            for (int i = 0; i < grid.getSize(); i++) {
                rowValues[i] = rowTiles.get(i).getValue();
            }
            Arrays.sort(rowValues);

            // Checks if the hashes match.
            if (Arrays.hashCode(rowValues) != expectedHash) {
                // Marks the row as incorrect.
                for (Tile rowTile : rowTiles) {
                    rowTile.setMistake();
                }
                correct = false;
            }
        }

        // Checks columns if correct.
        for (Column column : grid.getColumns()) {
            ArrayList<Tile> columnTiles = column.getColumnTiles();

            int[] columnValues = new int[grid.getSize()];
            for (int i = 0; i < grid.getSize(); i++) {
                columnValues[i] = columnTiles.get(i).getValue();
            }
            Arrays.sort(columnValues);

            // Checks if the hashes match.
            if (Arrays.hashCode(columnValues) != expectedHash) {
                // Marks the row as incorrect.
                for (Tile columnTile : columnTiles) {
                    columnTile.setMistake();
                }
                correct = false;
            }
        }
        return correct;
    }
}
