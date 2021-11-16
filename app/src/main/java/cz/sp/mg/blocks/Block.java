package cz.sp.mg.blocks;

public abstract class Block {

    public static final int START_X = 2;
    public static final int START_Y = 0;

    private Integer y;
    private Integer x;
    private Boolean[][] shape;

    protected Block() {
        setStartPosition();
    }

    protected abstract Boolean[][] createShape();

    public void setStartPosition() {
        this.x = START_X;
        this.y = START_Y;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Boolean[][] getShape() {
        if (shape == null) {
            shape = createShape();
        }
        return shape;
    }
}
