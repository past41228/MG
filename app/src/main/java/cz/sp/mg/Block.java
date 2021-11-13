package cz.sp.mg;

public class Block {

    private Integer row;
    private Integer col;

    Block() {
        this.row = 0;
        this.col = 2;
    }

    Block(Integer row, Integer col) {
        this.row = row;
        this.col = col;
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
}
