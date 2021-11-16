package cz.sp.mg;

import java.util.Arrays;
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
    private final Block[] blocks;
    private final Boolean[][] cells;
    private Boolean moveLeft;

    public BlocksGrid() {
        this.blocks = new Block[] {
                new LineBlock(3),
                new TBlock(),
                new LBlock(),
                new MirrorLBlock(),
                new ZBlock(),
                new SBlock(),
                new SquareBlock()
        };
        this.random = new Random();
        this.ctrl = blocks[random.nextInt(blocks.length)];

        this.cells = new Boolean[ROWS_COUNT][COLS_COUNT];
        for (Boolean[] row : this.cells) {
            Arrays.fill(row, Boolean.FALSE);
        }
        placeBlock(Boolean.TRUE);
    }

    public Boolean[][] getCells() {
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
        Boolean[][] shape = ctrl.getShape();
        for (int y = 0; y < shape.length; y++) {
            xMin = shape[y].length;
            xMax = 0;
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
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
            Boolean notEmpty = cells[ctrl.getY() + y][ctrlX];
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
        Boolean[][] shape = ctrl.getShape();
        for (int x = 0; x < shape[0].length; x++) {
            yMax = 0;
            for (int y = 0; y < shape.length; y++) {
                if (shape[y][x]) {
                    if (y > yMax) yMax = y;
                }
            }
            boolean bottomBorder = (ctrl.getY() + yMax) >= (ROWS_COUNT - 1);
            if (bottomBorder) return false;
            Boolean notEmpty = cells[ctrl.getY() + yMax + 1][ctrl.getX() + x];
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
            ctrl = blocks[random.nextInt(blocks.length)];
        }
        placeBlock(Boolean.TRUE);
    }

    private void placeBlock(Boolean here) {
        Boolean[][] shape = ctrl.getShape();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x]) {
                    cells[y + ctrl.getY()][x + ctrl.getX()] = here;
                }
            }
        }
    }
}
