package cz.sp.mg.blocks;

public class SmallLBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.TRUE, Boolean.TRUE},
                {Boolean.FALSE, Boolean.TRUE}
        };
    }
}
