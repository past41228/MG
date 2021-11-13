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

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer STEP = 32;
    private static final Integer FPS = 20;
    private static final Integer ROWS_COUNT = 15;
    private static final Integer COLS_COUNT = 8;

    private Thread thread;
    private Boolean running;
    private Integer currentSpeed;
    private Integer levelSpeed;
    private Bitmap block;
    private Paint paint;
    private List<List<Boolean>> grid;
    private Block ctrl;

    public BlocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.levelSpeed = DEFAULT_SPEED;
        this.currentSpeed = levelSpeed;
        this.block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        this.block = Bitmap.createScaledBitmap(block, STEP - 2, STEP - 2,false);
        this.paint = new Paint();

        this.grid = new ArrayList<>(ROWS_COUNT);
        for (int y = 0; y < ROWS_COUNT; y++) {
            ArrayList<Boolean> columns = new ArrayList<>(COLS_COUNT);
            for (int x = 0; x < COLS_COUNT; x++) {
                columns.add(Boolean.FALSE);
            }
            grid.add(columns);
        }
        this.ctrl = new Block();
    }

    private void moveHorizontally(int i) {
        grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
        ctrl.setCol(ctrl.getCol() + i);
        grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
    }

    private boolean isColFree(int i) {
        return !grid.get(ctrl.getRow()).get(ctrl.getCol() + i);
    }

    public void moveLeft() {
        if (ctrl.getCol() > 0 && isColFree(-1)) {
            moveHorizontally(- 1);
        }
    }

    public void moveRight() {
        if (ctrl.getCol() < (COLS_COUNT - 1) && isColFree(+1)) {
            moveHorizontally(+1);
        }
    }

    public void speedUp() {
        currentSpeed *= 10;
    }

    public void slowDown() {
        currentSpeed = levelSpeed;
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            for (int y = 0; y < grid.size(); y++) {
                List<Boolean> row = grid.get(y);
                for (int x = 0; x < row.size(); x++) {
                    if (row.get(x)) {
                        canvas.drawBitmap(block, x * STEP, y * STEP, paint);
                    }
                }
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
        try {
            Thread.sleep(1000 / FPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fall() {
        if (ctrl.getRow() < (ROWS_COUNT - 1) && !grid.get(ctrl.getRow() + 1).get(ctrl.getCol())) {
            grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.FALSE);
            ctrl.setRow(ctrl.getRow() + 1);
            grid.get(ctrl.getRow()).set(ctrl.getCol(), Boolean.TRUE);
        } else {
            ctrl = new Block();
        }
    }

    @Override
    public void run() {
        Integer counter = 0;
        while (running) {
            draw();
            if (++counter > (FPS / currentSpeed)) {
                fall();
                counter = 0;
            }
        }
    }

    public Boolean isRunning() {
        return running;
    }

    public void pause() {
        try {
            running = Boolean.FALSE;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = Boolean.TRUE;
        thread = new Thread(this);
        thread.start();
    }
}