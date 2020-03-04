import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Change currentChange;
    private Stack<Change> undoHistory;
    private Stack<Change> redoHistory;

    // The history class constructor.
    public History() {
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

    // Clears all history.
    public void clearHistory() {
        undoHistory.clear();
        redoHistory.clear();
    }
}
