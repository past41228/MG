package cz.sp.mg.blocks;

public class ZBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE},
                {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE}
        };
    }
}
