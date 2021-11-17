package cz.sp.mg.blocks;

public abstract class Block {

    public static final int START_X = 2;
    public static final int START_Y = 0;

    private Integer y;
    private Integer x;
    private Boolean[][] shape;
    private Boolean[][] rotated;

    protected Block() {
        setStartPosition();
    }

    protected abstract Boolean[][] createShape();

    public void setStartPosition() {
        this.x = START_X;
        this.y = START_Y;
        this.rotated = null;
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
        return rotated != null ? rotated : shape;
    }

    public void rotate() {
        this.rotated = rotated != null ? rotation(rotated) : rotation(shape);
    }

    private Boolean[][] rotation(Boolean[][] of) {
        return rotation(of, Boolean.TRUE);
    }

    private Boolean[][] rotation(Boolean[][] of, boolean clockwise) {
        final int m = of.length;
        final int n = of[0].length;
        Boolean[][] rotated = new Boolean[n][m];
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (clockwise) {
                    rotated[col][m - 1 - row] = of[row][col];
                } else {
                    rotated[n - 1 - col][row] = of[row][col];
                }
            }
        }
        return rotated;
    }
}
