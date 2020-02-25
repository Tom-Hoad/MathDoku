import java.util.ArrayList;

// The class for a cage on the grid.
public class Cage {
    private int result;
    private String operation;
    private ArrayList<Tile> cageTiles;
    private String cageDefinition;

    // The cage class constructor.
    public Cage(String cageDefinition) {
        this.cageDefinition = cageDefinition;
        this.result = getResult();
    }

    // Gets the cage result.
    public int getResult() {
        int nonNum = 0;
        for (int i = 0; i < cageDefinition.length(); i++) {
            try {
                Integer.valueOf(cageDefinition.charAt(i));
            } catch (NumberFormatException e) {
                nonNum = i;
            }
        }
        return nonNum;
    }
}
