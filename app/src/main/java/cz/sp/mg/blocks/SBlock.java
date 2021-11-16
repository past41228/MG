package cz.sp.mg.blocks;

public class SBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.FALSE, Boolean.TRUE, Boolean.TRUE},
                {Boolean.TRUE, Boolean.TRUE, Boolean.FALSE}
        };
    }
}
