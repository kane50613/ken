package tw.kane.ken;

public class Position {

    public int col, row;

    public Position() {
        col = 1;
        row = 1;
    }

    public Position(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void jumpTo(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void move(int col, int row) {
        this.col += col;
        this.row += row;
    }
}
