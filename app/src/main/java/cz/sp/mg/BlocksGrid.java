package cz.sp.mg;

import java.util.ArrayList;
import java.util.List;

public class BlocksGrid {

    private static final Integer ROWS_COUNT = 15;
    private static final Integer COLS_COUNT = 8;

    private Block ctrl;
    private Boolean moveLeft;
    private final List<List<Boolean>> cells;

    public BlocksGrid() {
        this.ctrl = new Block();
        this.cells = new ArrayList<>(ROWS_COUNT);
        for (int y = 0; y < ROWS_COUNT; y++) {
            final ArrayList<Boolean> cols = new ArrayList<>(COLS_COUNT);
            for (int x = 0; x < COLS_COUNT; x++) {
                cols.add(ctrl.getCol().equals(x) && ctrl.getRow().equals(y));
            }
            cells.add(cols);
        }

    }

    public List<List<Boolean>> getCells() {
        return cells;
    }

    private void moveHorizontally(Boolean toLeft) {
        cells.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
        ctrl.setCol(ctrl.getCol() + (toLeft ? -1 : +1));
        cells.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
    }

    private boolean isColFree(Boolean onLeft) {
        return !cells.get(ctrl.getRow()).get(ctrl.getCol() + (onLeft ? -1 : +1));
    }

    private void stepLeft() {
        if (ctrl.getCol() > 0 && isColFree(Boolean.TRUE)) {
            moveHorizontally(Boolean.TRUE);
        }
    }

    private void stepRight() {
        if (ctrl.getCol() < (COLS_COUNT - 1) && isColFree(Boolean.FALSE)) {
            moveHorizontally(Boolean.FALSE);
        }
    }

    public void moveLeft() {
        moveLeft = Boolean.TRUE;
    }

    public void moveRight() {
        moveLeft = Boolean.FALSE;
    }

    public void justFall() {
        moveLeft = null;
    }

    public void move() {
        if (moveLeft == null) {
            return;
        }
        if (moveLeft) {
            stepLeft();
        } else {
            stepRight();
        }
    }

    public void fall() {
        if (ctrl.getRow() < (ROWS_COUNT - 1) && !cells.get(ctrl.getRow() + 1).get(ctrl.getCol())) {
            cells.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
            ctrl.setRow(ctrl.getRow() + 1);
            cells.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
        } else {
            ctrl = new Block();
            cells.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
        }
    }
}
