package cz.sp.mg.blocks;

import java.util.List;

public class LBlock extends TwoRowsBlock {
    @Override
    protected void fillShape(List<List<Boolean>> shape) {
        fillFirstLine(shape);
        shape.get(1).set(0, Boolean.TRUE);
    }
}
