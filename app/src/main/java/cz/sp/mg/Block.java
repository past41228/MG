package cz.sp.mg;

public class Block {

    public static final int ROW_START = 0;
    public static final int COL_START = 2;

    private Integer row;
    private Integer col;

    Block() {
        setStartPosition();
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public void setStartPosition() {
        this.row = ROW_START;
        this.col = COL_START;
    }
}
