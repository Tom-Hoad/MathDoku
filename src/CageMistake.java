import java.util.ArrayList;
import java.util.Collections;

// The class for checking cage mistakes.
public class CageMistake {
    private ArrayList<String> strPermutations;
    private ArrayList<ArrayList<Integer>> permutations;

    // The cage mistake class constructor.
    public CageMistake() {
        this.strPermutations = new ArrayList<>();
        this.permutations = new ArrayList<>();
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
