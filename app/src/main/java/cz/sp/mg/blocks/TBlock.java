package cz.sp.mg.blocks;

public class TBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
                {Boolean.FALSE, Boolean.TRUE, Boolean.FALSE}
        };
    }
}
