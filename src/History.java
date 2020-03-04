import javafx.scene.control.Button;

import java.util.Stack;

// The class for undo and redo history.
public class History {
    private Change currentChange;
    private Button undoButton;
    private Button redoButton;
    private Stack<Change> undoHistory;
    private Stack<Change> redoHistory;

    // The history class constructor.
    public History(Button undoButton, Button redoButton) {
        this.undoButton = undoButton;
        undoButton.setDisable(true);
        this.redoButton = redoButton;
        redoButton.setDisable(true);

        this.undoHistory = new Stack<>();
        this.redoHistory = new Stack<>();
    }

    // Makes a new change on the grid.
    public void addMove(Change change) {
        undoHistory.push(currentChange);
        currentChange = change;
        undoButton.setDisable(false);

        // Clears the redo history, if you've just undone.
        if (!redoHistory.empty()) {
            redoHistory.clear();
            redoButton.setDisable(true);
        }
    }

    // Undoes the last move.
    public void undo() {
        if (!undoHistory.isEmpty()) {
            currentChange.getTile().displayNumber(currentChange.getOldValue());
            redoHistory.push(currentChange);
            currentChange = undoHistory.pop();

            if (undoHistory.isEmpty()) {
                undoButton.setDisable(true);
            }
            redoButton.setDisable(false);
        }
    }

    // Redoes the last move.
    public void redo() {
        if (!redoHistory.isEmpty()) {
            undoHistory.push(currentChange);
            currentChange = redoHistory.pop();
            currentChange.getTile().displayNumber(currentChange.getNewValue());

            if (redoHistory.isEmpty()) {
                redoButton.setDisable(true);
            }
            undoButton.setDisable(false);
        }
    }

    // Clears all history.
    public void clearHistory() {
        undoHistory.clear();
        undoButton.setDisable(true);
        redoHistory.clear();
        redoButton.setDisable(true);
    }
}
