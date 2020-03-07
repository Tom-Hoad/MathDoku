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
    private String font;
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
        this.font = "Medium";

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

        try {
            for (String textCage : splitCages) {
                // Gets the result and operation.
                int cageSplit = textCage.indexOf(" ");
                String strResult = textCage.substring(0, cageSplit - 1);
                String operation = textCage.substring(cageSplit - 1, cageSplit);
                String cageDefinition = textCage.substring(cageSplit + 1);

                // Checks if the cage has been entered correctly.
                int result = Integer.parseInt(strResult);
                checkOperation(operation);

                // Creates the cage.
                ArrayList<Tile> cageTiles = new ArrayList<>();
                for (String tilePosition : cageDefinition.split(",")) {
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

            if (size <= 8 && size >= 2) {
                checkCages();
                createGrid();
                displayGrid();
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            System.out.println("Error: invalid cage(s)!");
            resetGrid();
        }
    }

    // Checks the cages for errors
    public void checkCages() throws Exception {
        // Check if overlapping cages.
        int[] positionCount = new int[size * size];
        for (Tile tile : tiles) {
            int tilePosition = tile.getGridPosition();
            positionCount[tilePosition - 1] = tilePosition;
            if (positionCount[tilePosition - 1] == 0) {
                throw new Exception();
            }
        }

        // Check for missing values.
        for (int position : positionCount) {
            if (position == 0) {
                throw new Exception();
            }
        }

        // Check if single cages.
        for (Cage cage : cages) {
            if (cage.getCageTiles().size() == 1) {
                if (cage.getResult() > 6) {
                    throw new Exception();
                }
            }
        }

        // Check for disconnected cages.
        for (Cage cage : cages) {
            int[] cagePositions = new int[cage.getCageTiles().size()];

            // Creates an array of all cage tiles.
            for (int i = 0; i < cagePositions.length; i++) {
                cagePositions[i] = cage.getCageTiles().get(i).getGridPosition();
            }

            // Checks if any of the cage positions appear in an adjacent position.
            for (Tile tile : cage.getCageTiles()) {
                boolean found = false;
                int position = tile.getGridPosition();
                int[] adjacentPositions = {position + 1, position - 1, position + size, position - size};

                for (int cagePosition : cagePositions) {
                    for (int adjacentPosition : adjacentPositions) {
                        if (cagePosition == adjacentPosition) {
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    throw new Exception();
                }
            }
        }
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
        buttonHBox.getChildren().clear();
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
    }

    // Displays the grid with cages.
    public void displayGrid() {
        gridPane.getChildren().clear();
        for (Tile tile : tiles) {
            gridPane.add(tile, tile.getColumn(), tile.getRow());
        }
        for (Cage cage : cages) {
            cage.showCage();
        }
    }

    // Resets the grid.
    public void resetGrid() {
        this.size = 0;
        this.cages = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.columns = new ArrayList<>();
        this.tiles = new ArrayList<>();

        gridPane.getChildren().clear();
        buttonHBox.getChildren().clear();
    }

    // Selects a tile.
    public void selectTile(Tile tile) {
        if (tile != null) {
            this.selectedTile = tile;
            tile.setSelected();
        }
    }

    // Checks if the operation is correct.
    public void checkOperation(String operation) throws Exception {
        if (!operation.equals("+") && !operation.equals("*") && !operation.equals("x") && !operation.equals("-") && !operation.equals("/") && !operation.equals("÷")) {
            throw new Exception();
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

    // Sets the font.
    public void setFont(String font) {
        this.font = font;

        try {
            // Sets the font size for all tile.
            for (Tile tile : tiles) {
                if (font.equals("Small")) {
                    tile.setFontSize(40);
                } else if (font.equals("Medium")) {
                    tile.setFontSize(50);
                } else {
                    tile.setFontSize(60);
                }
            }
        } catch (NullPointerException ignored) {}
    }
}
