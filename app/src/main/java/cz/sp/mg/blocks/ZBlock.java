package cz.sp.mg.blocks;

import androidx.annotation.NonNull;

import java.util.List;

public class ZBlock extends TwoRowsBlock {
    @Override
    protected void fillShape(@NonNull List<List<Boolean>> shape) {
        shape.get(0).set(0, Boolean.TRUE);
        shape.get(0).set(1, Boolean.TRUE);
        shape.get(1).set(1, Boolean.TRUE);
        shape.get(1).set(2, Boolean.TRUE);
    }
}
