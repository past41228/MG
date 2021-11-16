package cz.sp.mg.blocks;

public class LineBlock extends Block {

    private final Integer length;

    public LineBlock(int length) {
        super();
        this.length = length;
    }

    @Override
    protected Boolean[][] createShape() {
        final Boolean[][] shape = new Boolean[1][length];
        for (int x = 0; x < length; x++) {
            shape[0][x] = Boolean.TRUE;
        }
        return shape;
    }
}
