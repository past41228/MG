package cz.sp.mg.blocks;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class TwoRowsBlock extends Block {

    private static final Integer ROWS_COUNT = 2;
    private static final Integer COLS_COUNT = 3;

    @Override
    protected List<List<Boolean>> createShape() {
        final List<List<Boolean>> rows = new ArrayList<>(ROWS_COUNT);
        for (int y = 0; y < ROWS_COUNT; y++) {
            final List<Boolean> cols = new ArrayList<>(COLS_COUNT);
            for (int x = 0; x < COLS_COUNT; x++) {
                cols.add(Boolean.FALSE);
            }
            rows.add(cols);
        }
        fillShape(rows);
        return rows;
    }

    protected abstract void fillShape(List<List<Boolean>> shape);

    protected final void fillFirstLine(@NonNull List<List<Boolean>> shape) {
        for (int x = 0; x < shape.get(0).size(); x++) {
            shape.get(0).set(x, Boolean.TRUE);
        }
    }
}
