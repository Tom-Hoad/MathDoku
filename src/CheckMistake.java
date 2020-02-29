import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CheckMistake {
    private int gridSize;
    private ArrayList<Tile> tiles;
    private ArrayList<Cage> cages;
    private ArrayList<String> strPermutations;
    private ArrayList<ArrayList<Integer>> permutations;

    public CheckMistake(int gridSize, ArrayList<Tile> tiles, ArrayList<Cage> cages) {
        this.gridSize = gridSize;
        this.tiles = tiles;
        this.cages = cages;
        this.strPermutations = new ArrayList<>();
        this.permutations = new ArrayList<>();
    }

    public boolean checkGrid() {
        boolean correct = true;

        // Checks cages if correct.
        for (Cage cage : cages) {
            boolean zeroError = false;

            // Creates an array list of all values.
            ArrayList<Integer> cageValues = new ArrayList<>();
            for (Tile tile : cage.getCageTiles()) {
                if (tile.getValue() != 0) {
                    cageValues.add(tile.getValue());
                } else {
                    zeroError = true;
                }
            }

            // Skips to the next cage.
            if (zeroError) {
                for (Tile tile : cage.getCageTiles()) {
                    tile.mistakeTile();
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
                        correct = false;
                    }
                    break;
                // Multiply values.
                case "x":
                    givenResult = 1;
                    for (int tileValue : cageValues) {
                        givenResult = givenResult * tileValue;
                    }
                    if (givenResult != expectedResult) {
                        correct = false;
                    }
                    break;
                // Minus values.
                case "-":
                    // Creates a class to check cage mistakes.
                    heapsAlgorithm(cageValues.size(), cageValues);

                    if (!subtractValues(expectedResult)) {
                        correct = false;
                    }
                    break;
                // Divide values.
                case "÷":
                    // Creates a class to check cage mistakes.
                    heapsAlgorithm(cageValues.size(), cageValues);

                    if (!divideValues(expectedResult)) {
                        correct = false;
                    }
                    break;
            }
        }

        // Creates the expected hash.
        int[] expectedTiles = new int[gridSize];
        for (int i = 0; i < gridSize; i++) {
            expectedTiles[i] = i + 1;
        }
        int expectedHash = Arrays.hashCode(expectedTiles);

        // Check rows if correct.
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Tile> rowTiles = new ArrayList<>(tiles.subList((i * gridSize), ((i + 1) * gridSize)));

            // Gets values of the row.
            int[] actualRow = new int[rowTiles.size()];
            for (int j = 0; j < actualRow.length; j++) {
                actualRow[j] = rowTiles.get(j).getValue();
            }
            Arrays.sort(actualRow);

            // Checks if the hashes match.
            if (Arrays.hashCode(actualRow) != expectedHash) {
                // Marks the row as incorrect.
                for (Tile rowTile : rowTiles) {
                    rowTile.mistakeTile();
                }
                correct = false;
                break;
            }
        }

        // Checks columns if correct.
        for (int i = 0; i < gridSize; i++) {
            // Gets the arrays of columns.
            ArrayList<Tile> columnTiles = new ArrayList<>();
            int[] actualColumn = new int[gridSize];
            int count = 0;
            for (Tile columnTile : tiles) {
                if ((columnTile.getGridPosition() - 1) % gridSize == i) {
                    actualColumn[count] = columnTile.getValue();
                    columnTiles.add(columnTile);
                    count++;
                }
            }
            Arrays.sort(actualColumn);

            // Checks if the hashes match.
            if (Arrays.hashCode(actualColumn) != expectedHash) {
                // Marks the column as incorrect.
                for (Tile columnTile : columnTiles) {
                    columnTile.mistakeTile();
                }
                correct = false;
                break;
            }
        }
        return correct;
    }

    // A recursive algorithm to find every permutations of an array.
    public ArrayList<Integer> heapsAlgorithm(int size, ArrayList<Integer> cageValues) {
        // Finds the result of the permutation based on the given operation.
        if (size == 1) {
            strPermutations.add(cageValues.toString());
            return cageValues;
        } else {
            for (int i = 0; i < size; i++) {
                heapsAlgorithm(size - 1, cageValues);

                if (size % 2 == 0) {
                    Collections.swap(cageValues, i, size - 1);
                } else {
                    Collections.swap(cageValues, 0, size - 1);
                }
            }
        }
        splitPermutations();
        return null;
    }

    // Converts the array list of string permutations to an array list of an array list of integers of permutations.
    private ArrayList<ArrayList<Integer>> splitPermutations() {
        for (String strPermutation : strPermutations) {
            strPermutation = strPermutation.substring(1, strPermutation.length() - 1);
            String[] splitPerms = strPermutation.split(", ");


            ArrayList<Integer> perms = new ArrayList<>();
            for (String splitPerm : splitPerms) {
                perms.add(Integer.parseInt(splitPerm));
            }
            permutations.add(perms);
        }
        return null;
    }

    // Subtracts the values of the array list, finding every value permutation.
    public boolean subtractValues(int expectedValue) {
        ArrayList<Integer> subtractPermutations = new ArrayList<>();

        // Finds every possible result of subtracting permutations.
        for (ArrayList<Integer> permutation : permutations) {
            int givenResult = permutation.get(0);
            for (int i = 1; i < permutation.size(); i++) {
                givenResult -= permutation.get(i);
            }
            subtractPermutations.add(givenResult);
        }

        // Finds the correct result.
        for (int subtractPermutation : subtractPermutations) {
            if (subtractPermutation == expectedValue) {
                return true;
            }
        }
        return false;
    }

    // Divides the values of the array list, finding every value permutation.
    public boolean divideValues(int expectedValue) {
        ArrayList<Integer> dividePermutations = new ArrayList<>();

        // Finds every possible result of subtracting permutations.
        for (ArrayList<Integer> permutation : permutations) {
            int givenResult = permutation.get(0);
            for (int i = 1; i < permutation.size(); i++) {
                givenResult = givenResult / permutation.get(i);
            }
            dividePermutations.add(givenResult);
        }

        // Finds the correct result.
        for (int dividePermutation : dividePermutations) {
            if (dividePermutation == expectedValue) {
                return true;
            }
        }
        return false;
    }
}
