package cz.sp.mg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class BlocksView extends SurfaceView implements Runnable {

    public static final Long DEFAULT_SPEED = 1000L;
    public static final Integer STEP = 32;
    public static final Integer ROWS_COUNT = 15;
    public static final Integer COLS_COUNT = 6;

    private Thread thread;
    private Boolean running;
    private Long speed;
    private Bitmap block;
    private Paint paint;
    private List<List<Boolean>> grid;
    Block ctrl;
    List<Boolean> movingLine;

    public BlocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.speed = DEFAULT_SPEED;
        this.block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        this.block = Bitmap.createScaledBitmap(block, STEP - 2, STEP - 2,false);
        this.paint = new Paint();

        this.grid = new ArrayList<>(ROWS_COUNT);
        for (int i = 0; i < ROWS_COUNT; i++) {
            ArrayList<Boolean> columns = new ArrayList<>(COLS_COUNT);
            for (int j = 0; j < COLS_COUNT; j++) {
                columns.add(Boolean.FALSE);
            }
            grid.add(columns);
        }
        this.ctrl = new Block();
    }

    public Boolean getRunning() {
        return running;
    }

    public Long getSpeed() {
        return speed;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    private void moveHorizontally(int i) {
        grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
        ctrl.setCol(ctrl.getCol() + i);
        grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
    }

    private boolean isFree(int i) {
        return !grid.get(ctrl.getRow()).get(ctrl.getCol() + i);
    }

    public void moveLeft() {
        if (ctrl.getCol() > 0 && isFree(-1)) {
            moveHorizontally(- 1);
        }
    }

    public void moveRight() {
        if (ctrl.getCol() < (COLS_COUNT - 1) && isFree(+1)) {
            moveHorizontally(+1);
        }
    }

    @Override
    public void run() {
        while (running) {
            draw();
            moveDown();
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            for (int i = 0; i < grid.size(); i++) {
                List<Boolean> line = grid.get(i);
                for (int j = 0; j < line.size(); j++) {
                    Boolean column = line.get(j);
                    if (column) {
                        canvas.drawBitmap(block, j * STEP, i * STEP, paint);
                    }
                }
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void moveDown() {
        if (ctrl.getRow() < (ROWS_COUNT - 1) && !grid.get(ctrl.getRow() + 1).get(ctrl.getCol())) {
            grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
            ctrl.setRow(ctrl.getRow() + 1);
            grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
        } else {
            ctrl = new Block();
        }
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = Boolean.TRUE;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            running = Boolean.FALSE;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
