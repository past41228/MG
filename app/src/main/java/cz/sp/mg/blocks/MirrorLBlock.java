package cz.sp.mg.blocks;

public class MirrorLBlock extends Block {
    @Override
    protected Boolean[][] createShape() {
        return new Boolean[][] {
                {Boolean.TRUE, Boolean.TRUE, Boolean.TRUE},
                {Boolean.TRUE, Boolean.FALSE, Boolean.FALSE}
        };
    }
}
