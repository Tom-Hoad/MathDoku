import java.util.ArrayList;

// The class for a cage on the grid.
public class Cage {
    private String result;
    private String operation;
    private String cageTiles;

    // The cage class constructor.
    public Cage(String cageDefinition) {
        int cageSplit = cageDefinition.indexOf(" ");
        this.result = cageDefinition.substring(0, cageSplit - 1);
        this.operation = cageDefinition.substring(cageSplit - 1, cageSplit);
        this.cageTiles = cageDefinition.substring(cageSplit + 1);
    }

    // Gets the expected result.
    public String getResult() {
        return result;
    }

    // Gets the operation.
    public String getOperation() {
        return operation;
    }

    // Gets list of tiles in the cage.
    public String getCageTiles() {
        return cageTiles;
    }
}
