package cz.sp.mg;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import cz.sp.mg.blocks.Block;
import cz.sp.mg.blocks.LBlock;
import cz.sp.mg.blocks.LineBlock;
import cz.sp.mg.blocks.MirrorLBlock;
import cz.sp.mg.blocks.SBlock;
import cz.sp.mg.blocks.SmallLBlock;
import cz.sp.mg.blocks.SquareBlock;
import cz.sp.mg.blocks.TBlock;
import cz.sp.mg.blocks.ZBlock;

public class BlocksGrid {

    private static final Integer ROWS_COUNT = 15;
    private static final Integer COLS_COUNT = 8;

    private final Random random;
    private final List<Block> blocks;
    private final Boolean[][] cells;

    private Block ctrl;
    private Boolean moveLeft;
    private Boolean rotation;


    public BlocksGrid() {
        this.blocks = createBlocks();
        this.random = new Random();
        this.ctrl = blocks.get(random.nextInt(blocks.size()));
        this.moveLeft = null;
        this.rotation = Boolean.FALSE;
        this.cells = new Boolean[ROWS_COUNT][COLS_COUNT];
        for (Boolean[] row : this.cells) {
            Arrays.fill(row, Boolean.FALSE);
        }
        placeBlock(Boolean.TRUE);
    }

    @NonNull
    private List<Block> createBlocks() {
        List<Block> b = new ArrayList<>();
        b.add(new LineBlock(1));
        b.add(new LineBlock(2));
        b.add(new LineBlock(3));
        b.add(new LBlock());
        b.add(new SmallLBlock());
        b.add(new MirrorLBlock());
        b.add(new TBlock());
        b.add(new ZBlock());
        b.add(new SBlock());
        b.add(new SquareBlock());
        return b;
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

    public void rotation() {
        rotation = Boolean.TRUE;
    }

    public void fallOnly() {
        moveLeft = null;
        rotation = Boolean.FALSE;
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

    public void rotate() {
        if (rotation && isRowEmpty()) {
            if (!isColEmpty(Boolean.FALSE)) {
                if (isColEmpty(Boolean.TRUE)) {
                    placeBlock(Boolean.FALSE);
                    ctrl.setX(ctrl.getX() - 1);
                } else {
                    return;
                }
            } else {
                placeBlock(Boolean.FALSE);
            }
            ctrl.rotate();
            placeBlock(Boolean.TRUE);
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
            ctrl = blocks.get(random.nextInt(blocks.size()));
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
