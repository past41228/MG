package cz.sp.mg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.sp.mg.blocks.Block;
import cz.sp.mg.blocks.LBlock;
import cz.sp.mg.blocks.LineBlock;
import cz.sp.mg.blocks.MirrorLBlock;
import cz.sp.mg.blocks.SBlock;
import cz.sp.mg.blocks.SquareBlock;
import cz.sp.mg.blocks.TBlock;
import cz.sp.mg.blocks.ZBlock;

public class BlocksGrid {

    private static final Integer ROWS_COUNT = 15;
    private static final Integer COLS_COUNT = 8;

    private Block ctrl;
    private final Random random;
    private final List<Block> blocks;
    private final List<List<Boolean>> cells;
    private Boolean moveLeft;

    public BlocksGrid() {
        this.blocks = new ArrayList<>();
        this.blocks.add(new LineBlock(3));
        this.blocks.add(new TBlock());
        this.blocks.add(new LBlock());
        this.blocks.add(new MirrorLBlock());
        this.blocks.add(new ZBlock());
        this.blocks.add(new SBlock());
        this.blocks.add(new SquareBlock());
        this.random = new Random();
        this.ctrl = blocks.get(random.nextInt(blocks.size()));

        this.cells = new ArrayList<>(ROWS_COUNT);
        for (int y = 0; y < ROWS_COUNT; y++) {
            final List<Boolean> cols = new ArrayList<>(COLS_COUNT);
            for (int x = 0; x < COLS_COUNT; x++) {
                cols.add(Boolean.FALSE);
            }
            cells.add(cols);
        }
        placeBlock(Boolean.TRUE);
    }

    public List<List<Boolean>> getCells() {
        return cells;
    }

    private void moveHorizontally(Boolean toLeft) {
        placeBlock(Boolean.FALSE);
        ctrl.setX(ctrl.getX() + (toLeft ? -1 : +1));
        placeBlock(Boolean.TRUE);
    }

    private boolean isColEmpty(Boolean onLeft) {
        int xMin;
        int xMax;
        for (int y = 0; y < ctrl.getShape().size(); y++) {
            xMin = ctrl.getShape().get(y).size();
            xMax = 0;
            for (int x = 0; x < ctrl.getShape().get(y).size(); x++) {
                if (ctrl.getShape().get(y).get(x)) {
                    if (x < xMin) xMin = x;
                    if (x > xMax) xMax = x;
                }
            }
            int ctrlX;
            if (onLeft) {
                boolean leftBorder = ctrl.getX() <= 0;
                if (leftBorder) return false;
                ctrlX = ctrl.getX() + xMin - 1;
            } else {
                boolean rightBorder = ctrl.getX() + xMax >= (COLS_COUNT - 1);
                if (rightBorder) return false;
                ctrlX = ctrl.getX() + xMax + 1;
            }
            Boolean notEmpty = cells.get(ctrl.getY() + y).get(ctrlX);
            if (notEmpty) return false;
        }
        return true;
    }

    private void stepLeft() {
        if (isColEmpty(Boolean.TRUE)) {
            moveHorizontally(Boolean.TRUE);
        }
    }

    private void stepRight() {
        if (isColEmpty(Boolean.FALSE)) {
            moveHorizontally(Boolean.FALSE);
        }
    }

    public void moveLeft() {
        moveLeft = Boolean.TRUE;
    }

    public void moveRight() {
        moveLeft = Boolean.FALSE;
    }

    public void fallOnly() {
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

    private boolean isRowEmpty() {
        int yMax;
        for (int x = 0; x < ctrl.getShape().get(0).size(); x++) {
            yMax = 0;
            for (int y = 0; y < ctrl.getShape().size(); y++) {
                if (ctrl.getShape().get(y).get(x)) {
                    if (y > yMax) yMax = y;
                }
            }
            boolean bottomBorder = ctrl.getY() + yMax >= (ROWS_COUNT - 1);
            if (bottomBorder) return false;
            int ctrlY = ctrl.getY() + yMax + 1;
            Boolean notEmpty = cells.get(ctrlY).get(ctrl.getX() + x);
            if (notEmpty) return false;
        }
        return true;
    }

    public void fall() {
        if (isRowEmpty()) {
            placeBlock(Boolean.FALSE);
            ctrl.setY(ctrl.getY() + 1);
        } else {
            ctrl.setStartPosition();
            ctrl = blocks.get(random.nextInt(blocks.size()));
        }
        placeBlock(Boolean.TRUE);
    }

    private void placeBlock(Boolean here) {
        for (int y = 0; y < ctrl.getShape().size(); y++) {
            for (int x = 0; x < ctrl.getShape().get(y).size(); x++) {
                if (ctrl.getShape().get(y).get(x)) {
                    cells.get(y + ctrl.getY()).set(x + ctrl.getX(), here);
                }
            }
        }
    }
}
