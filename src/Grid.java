import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// The class for a grid.
public class Grid {
    private int size;
    private GridPane gridPane;
    private HBox buttonHBox;
    private ArrayList<Row> rows;
    private ArrayList<Column> columns;
    private ArrayList<Cage> cages;
    private ArrayList<Tile> tiles;
    private Tile selectedTile;
    private CheckMistake checkMistake;
    private History history;

    // The grid class constructor.
    public Grid(GridPane gridPane, HBox buttonHBox, History history) {
        this.gridPane = gridPane;
        this.buttonHBox = buttonHBox;

        this.selectedTile = null;
        this.checkMistake = new CheckMistake(this);
        this.history = history;
    }

    // Displays the cages from a file.
    public void readFile(File selectedFile) throws IOException {
        if (selectedFile != null) {
            ArrayList<String> splitFile = new ArrayList<>();

            Scanner reader = new Scanner(selectedFile);
            while (reader.hasNextLine()) {
                splitFile.add(reader.nextLine());
            }

            translateCages(splitFile);
        }
    }

    // Displays the cages from text.
    public void readText(String gameText) {
        if (!gameText.isEmpty()) {
            String[] splitText = gameText.split("\\n");

            translateCages(new ArrayList<>(Arrays.asList(splitText)));
        }
    }

    // Translates the text and converts it into cages.
    public void translateCages(ArrayList<String> splitCages) {
        this.cages = new ArrayList<>();
        this.tiles = new ArrayList<>();

        for (String textCage : splitCages) {
            // Gets the result and operation.
            int cageSplit = textCage.indexOf(" ");
            int result = Integer.parseInt(textCage.substring(0, cageSplit - 1));
            String operation = textCage.substring(cageSplit - 1, cageSplit);

            // Creates the cage.
            ArrayList<Tile> cageTiles = new ArrayList<>();
            for (String tilePosition : textCage.substring(cageSplit + 1).split(",")) {
                Tile tile = new Tile(Integer.parseInt(tilePosition));
                tile.setPrefSize(100, 100);
                tile.setDefault();

                // Event handler code for click a tile.
                tile.setOnMouseClicked(mouseEvent -> {
                    try {
                        // Defaults the selected tile and selects the new tile.
                        selectedTile.setDefault();
                        selectTile(tile);
                    } catch (NullPointerException e) {
                        selectTile(tile);
                    }
                });

                tiles.add(tile);
                cageTiles.add(tile);
            }
            cages.add(new Cage(this, result, operation, cageTiles));
        }

        // Determines the size of the array.
        int maxPosition = 1;
        for (Tile tile : tiles) {
            if (tile.getGridPosition() > maxPosition) {
                maxPosition = tile.getGridPosition();
            }
        }
        this.size = (int) Math.sqrt(maxPosition);

        createGrid();
    }

    // Creates the grid on the pane.
    public void createGrid() {
        // Creates the rows and columns.
        rows = new ArrayList<>();
        columns = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            rows.add(new Row(new ArrayList<>()));
            columns.add(new Column(new ArrayList<>()));
        }

        // Adds the tiles to the rows and columns.
        for (Tile tile : tiles) {
            tile.setRow((int) Math.floor(((double) tile.getGridPosition() - 1) / size));
            tile.setColumn((tile.getGridPosition() - 1) % size);

            rows.get(tile.getRow()).addTo(tile);
            columns.get(tile.getRow()).addTo(tile);
        }

        // Adds number buttons to below the grid.
        for (int i = 0; i < size; i++) {
            Button numButton = new Button(String.valueOf(i + 1));
            numButton.setFont(new Font(25));
            buttonHBox.getChildren().add(numButton);

            // The event handler code for pressing a number button.
            numButton.setOnMouseClicked(mouseEvent -> {
                // Displays the number on the tile.
                try {
                    history.addMove(new Change(selectedTile, Integer.parseInt(numButton.getText())));
                    selectedTile.displayNumber(Integer.parseInt(numButton.getText()));
                    checkMistake.shouldCheck();
                    selectTile(selectedTile);
                } catch (NullPointerException e) {
                    System.out.println("No tile has been selected.");
                }
            });
        }

        displayGrid();
    }

    // Displays the grid.
    public void displayGrid() {
        for (Tile tile : tiles) {
            gridPane.add(tile, tile.getRow(), tile.getColumn());
        }
    }

    // Selects a tile.
    public void selectTile(Tile tile) {
        if (tile != null) {
            this.selectedTile = tile;
            tile.setSelected();
        }
    }

    // Gets the size of the grid.
    public int getSize() {
        return size;
    }

    // Gets the selected tile.
    public Tile getSelected() {
        return selectedTile;
    }

    // Gets all the rows in the grid.
    public ArrayList<Row> getRows() {
        return rows;
    }

    // Gets all the columns in the grid.
    public ArrayList<Column> getColumns() {
        return columns;
    }

    // Gets all the cages in the grid.
    public ArrayList<Cage> getCages() {
        return cages;
    }

    // Gets all the tiles in the grid.
    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    // Gets the mistake checking class.
    public CheckMistake getCheckMistake() {
        return checkMistake;
    }

    // Gets the history class.
    public History getHistory() {
        return history;
    }

    // Removes all the cages.
    public void removeCages() {
        for (Cage cage : cages) {
            cage.removeCage();
        }
        this.cages = new ArrayList<>();
    }
}
