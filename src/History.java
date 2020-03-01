import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Grid grid;
    private Stack<Change> undoHistory;
    private Stack<Change> redoHistory;

    // The history class constructor.
    public History(Grid grid) {
        this.grid = grid;
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
        if (!undoHistory.isEmpty()) {
            Change lastMove = undoHistory.pop();
            lastMove.getTile().displayNumber(grid, lastMove.getValue(), grid.getMistakesCheck());
            redoHistory.push(lastMove);
        }
    }

    // Redoes the last move.
    public void redo() {
        undoHistory.push(redoHistory.pop());
    }
}
