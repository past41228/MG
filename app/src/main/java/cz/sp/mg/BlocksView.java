package cz.sp.mg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.List;

public class BlocksView extends SurfaceView implements Runnable {

    private static final String LOG_TAG = BlocksView.class.getSimpleName();

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer STEP = 32;
    private static final Integer FPS = 20;

    private static final Integer BLOCK_SIZE = STEP - 2;
    private static final Integer OFFSET = 8;


    private Thread thread;
    private Boolean running;

    private Integer currentSpeed;
    private Integer levelSpeed;

    private Bitmap block;
    private Paint paint;
    private BlocksGrid grid;


    public BlocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.levelSpeed = DEFAULT_SPEED;
        this.currentSpeed = levelSpeed;

        this.block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        this.block = Bitmap.createScaledBitmap(block, BLOCK_SIZE, BLOCK_SIZE,false);
        this.paint = new Paint();
        this.grid = new BlocksGrid();
    }

    public void setLevelSpeed(Integer levelSpeed) {
        this.levelSpeed = levelSpeed;
        this.currentSpeed = levelSpeed;
    }

    public void moveLeft() {
        grid.moveLeft();
    }

    public void moveRight() {
        grid.moveRight();
    }

    public void speedUp() {
        currentSpeed *= 10;
    }

    public void slowDown() {
        currentSpeed = levelSpeed;
    }


    private void draw() {
        if (getHolder().getSurface().isValid()) {
            final Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            for (int y = 0; y < grid.getCells().size(); y++) {
                final List<Boolean> row = grid.getCells().get(y);
                for (int x = 0; x < row.size(); x++) {
                    if (row.get(x)) {
                        canvas.drawBitmap(block, x * STEP + OFFSET, y * STEP + OFFSET, paint);
                    }
                }
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
        try {
            Thread.sleep(1000 / FPS);
        } catch (InterruptedException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        int counter = 0;
        while (running) {
            draw();
            if (++counter > (FPS / currentSpeed)) {
                grid.fall();
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
            Log.e(LOG_TAG, e.getMessage(), e);
        }
    }

    public void resume() {
        running = Boolean.TRUE;
        thread = new Thread(this);
        thread.start();
    }
}