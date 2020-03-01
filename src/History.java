import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Grid grid;
    private CheckMistake checkMistake;
    private Change currentChange;
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
        currentChange = change;

        // Clears the redo history, if you've just undone.
        if (!redoHistory.empty()) {
            redoHistory.clear();
        }
    }

    // Undoes the last move.
    public void undo() {
        if (!undoHistory.isEmpty()) {
            Change lastMove = undoHistory.pop();
            lastMove.getTile().displayNumber(grid, checkMistake, lastMove.getValue());
            redoHistory.push(currentChange);
            currentChange = lastMove;
        }
    }

    // Redoes the last move.
    public void redo() {
        if (!redoHistory.isEmpty()) {
            Change nextMove = redoHistory.pop();
            nextMove.getTile().displayNumber(grid, checkMistake, nextMove.getValue());
            undoHistory.push(currentChange);
            currentChange = nextMove;
        }
    }
}
