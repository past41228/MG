package cz.sp.mg.blocks;

public class SquareBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.TRUE, Boolean.TRUE},
                {Boolean.TRUE, Boolean.TRUE}
        };
    }
}
