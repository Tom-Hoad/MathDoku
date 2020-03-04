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
        undoHistory.push(currentChange);
        currentChange = change;

        // Clears the redo history, if you've just undone.
        if (!redoHistory.empty()) {
            redoHistory.clear();
        }
    }

    // Undoes the last move.
    public void undo() {
        if (!undoHistory.isEmpty()) {
            currentChange.getTile().displayNumber(currentChange.getOldValue());
            redoHistory.push(currentChange);
            currentChange = undoHistory.pop();
        }
    }

    // Redoes the last move.
    public void redo() {
        if (!redoHistory.isEmpty()) {
            undoHistory.push(currentChange);
            currentChange = redoHistory.pop();
            currentChange.getTile().displayNumber(currentChange.getNewValue());
        }
    }
}
