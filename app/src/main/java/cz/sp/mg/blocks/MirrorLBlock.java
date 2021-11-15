package cz.sp.mg.blocks;

import java.util.List;

public class MirrorLBlock extends TwoRowsBlock {
    @Override
    protected void fillShape(List<List<Boolean>> shape) {
        fillFirstLine(shape);
        shape.get(1).set(2, Boolean.TRUE);
    }
}
