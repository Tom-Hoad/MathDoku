import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Stack<Integer> undoHistory;
    private Stack<Integer> redoHistory;

    // The history class constructor.
    public History() {
        this.undoHistory = new Stack<>();
        this.redoHistory = new Stack<>();
    }

    // Makes a new change on the grid.
    public void addMove(int move) {
        undoHistory.push(move);

        // Clears the redo history, if you've just undone.
        if (!redoHistory.empty()) {
            redoHistory.clear();
        }
    }

    // Undoes the last move.
    public void undo() {
        redoHistory.push(undoHistory.pop());
    }

    // Redoes the last move.
    public void redo() {
        undoHistory.push(redoHistory.pop());
    }
}
