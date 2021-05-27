package tw.kane.ken;

public class Position {

    int col, row, index;

    public Position() {
        col = 1;
        row = 1;
        index = 0;
    }

    public Position jumpTo(int col, int row, int index) {
        this.col = col;
        this.row = row;
        this.index = index;
        return this;
    }
}
