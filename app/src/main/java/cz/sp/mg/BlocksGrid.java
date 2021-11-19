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
        List<Block> newBlocks = new ArrayList<>();
        newBlocks.add(new LineBlock(1));
        newBlocks.add(new LineBlock(2));
        newBlocks.add(new LineBlock(3));
        newBlocks.add(new LBlock());
        newBlocks.add(new SmallLBlock());
        newBlocks.add(new MirrorLBlock());
        newBlocks.add(new TBlock());
        newBlocks.add(new ZBlock());
        newBlocks.add(new SBlock());
        newBlocks.add(new SquareBlock());
        return newBlocks;
    }

    public Boolean[][] getCells() {
        return cells;
    }

    private void moveHorizontally(Boolean toLeft) {
        placeBlock(Boolean.FALSE);
        Integer originalX = ctrl.getX();
        ctrl.setX(ctrl.getX() + (toLeft ? -1 : +1));
        if (hasNotSpace()) {
            ctrl.setX(originalX);
        }
        placeBlock(Boolean.TRUE);
    }

    private void stepLeft() {
        moveHorizontally(Boolean.TRUE);
    }

    private void stepRight() {
        moveHorizontally(Boolean.FALSE);
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
        if (rotation) {
            placeBlock(Boolean.FALSE);
            ctrl.rotate(Boolean.TRUE);
            if (hasNotSpace()) {
                Integer originalX = ctrl.getX();
                boolean spaceFound = Boolean.FALSE;
                for (int x = 0; x < ctrl.getShape()[0].length; x++) {
                    ctrl.setX(ctrl.getX() - 1);
                    spaceFound = !hasNotSpace();
                    if (spaceFound) break;
                }
                if (!spaceFound) {
                    ctrl.setX(originalX);
                    ctrl.rotate(Boolean.FALSE);
                }
            }
            placeBlock(Boolean.TRUE);
        }
    }

    private Boolean hasNotSpace() {
        Boolean[][] shape = ctrl.getShape();
        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                try {
                    if (shape[y][x] && cells[ctrl.getY() + y][ctrl.getX() + x]) {
                        return Boolean.TRUE;
                    }
                } catch (IndexOutOfBoundsException ignore) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    public void fall() throws Exception {
        placeBlock(Boolean.FALSE);
        Integer originalY = ctrl.getY();
        ctrl.setY(ctrl.getY() + 1);
        if (hasNotSpace()) {
            ctrl.setY(originalY);
            placeBlock(Boolean.TRUE);

            ctrl.setStartPosition();
            ctrl = blocks.get(random.nextInt(blocks.size()));
            removeFullRows();
            if (hasNotSpace()) {
                throw new Exception("GAME OVER");
            }
        }
        placeBlock(Boolean.TRUE);
    }

    private void removeFullRows() {
        for (int y = 0; y < cells.length; y++) {
            boolean rowFull = Boolean.TRUE;
            for (int x = 0; x < cells[y].length; x++) {
                rowFull = cells[y][x];
                if (!rowFull) break;
            }
            if (rowFull) {
                for (int shiftY = y; shiftY > 0; shiftY--) {
                    System.arraycopy(cells[shiftY - 1], 0, cells[shiftY], 0, cells[shiftY].length);
                }
            }
        }
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
