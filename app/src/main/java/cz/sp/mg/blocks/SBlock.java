package cz.sp.mg.blocks;

import java.util.List;

public class SBlock extends TwoRowsBlock {
    @Override
    protected void fillShape(List<List<Boolean>> shape) {
        shape.get(0).set(1, Boolean.TRUE);
        shape.get(0).set(2, Boolean.TRUE);
        shape.get(1).set(0, Boolean.TRUE);
        shape.get(1).set(1, Boolean.TRUE);
    }
}
