import java.util.ArrayList;

public class Grid {
    private ArrayList<Row> rows;
    private ArrayList<Column> columns;

    public Grid(ArrayList<Row> rows, ArrayList<Column> columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
