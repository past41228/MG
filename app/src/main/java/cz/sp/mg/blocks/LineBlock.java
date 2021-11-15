package cz.sp.mg.blocks;

import java.util.ArrayList;
import java.util.List;

public class LineBlock extends Block {

    private final Integer length;

    public LineBlock(int length) {
        super();
        this.length = length;
    }

    @Override
    protected List<List<Boolean>> createShape() {
        final List<List<Boolean>> rows = new ArrayList<>(1);
        final List<Boolean> cols = new ArrayList<>(length);
        for (int x = 0; x < length; x++) {
            cols.add(Boolean.TRUE);
        }
        rows.add(cols);
        return rows;
    }
}
