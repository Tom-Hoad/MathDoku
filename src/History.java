import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Grid grid;
    private CheckMistake checkMistake;
    private Stack<Change> undoHistory;
    private Stack<Change> redoHistory;

    // The history class constructor.
    public History(Grid grid, CheckMistake checkMistake) {
        this.grid = grid;
        this.checkMistake = checkMistake;
        this.undoHistory = new Stack<>();
        this.redoHistory = new Stack<>();
    }

    // Makes a new change on the grid.
    public void addMove(Change change) {
        undoHistory.push(change);

        // Clears the redo history, if you've just undone.
        if (!redoHistory.empty()) {
            redoHistory.clear();
        }
    }

    // Undoes the last move.
    public void undo() {
        System.out.println(undoHistory);

        if (!undoHistory.isEmpty()) {
            Change lastMove = undoHistory.pop();
            lastMove.getTile().displayNumber(grid, checkMistake, lastMove.getValue());
            redoHistory.push(lastMove);
        }
    }

    // Redoes the last move.
    public void redo() {
        undoHistory.push(redoHistory.pop());
    }
}
