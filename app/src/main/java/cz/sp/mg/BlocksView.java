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

public class BlocksView extends SurfaceView implements Runnable {

    private static final String LOG_TAG = BlocksView.class.getSimpleName();

    private static final Integer DEFAULT_SPEED = 1;
    private static final Integer SPEED_MULTIPLIER = 10;
    private static final Integer FPS = 20;


    private Thread thread;
    private Boolean running;

    private Integer levelSpeed;
    private Integer fallSpeed;
    private Integer moveSpeed;

    private final Bitmap block;
    private final Paint paint;
    private final BlocksGrid grid;


    public BlocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLevelSpeed(DEFAULT_SPEED);
        setZOrderOnTop(Boolean.TRUE);
        this.block = BitmapFactory.decodeResource(getResources(), R.drawable.block);
        this.paint = new Paint();
        this.grid = new BlocksGrid();
    }

    public String getLevel() {
        setLevelSpeed((grid.getScore() / 1000) + DEFAULT_SPEED);
        return levelSpeed.toString();
    }

    public String getScore() {
        return grid.getScore().toString();
    }

    private void setLevelSpeed(Integer levelSpeed) {
        this.levelSpeed = levelSpeed;
        this.fallSpeed = levelSpeed;
        this.moveSpeed = DEFAULT_SPEED * SPEED_MULTIPLIER;
    }

    public void moveLeft() {
        grid.moveLeft();
    }

    public void moveRight() {
        grid.moveRight();
    }

    public void rotation() {
        grid.rotation();
    }

    public void fallOnly() {
        grid.fallOnly();
    }

    public void speedUp() {
        fallSpeed *= SPEED_MULTIPLIER;
    }

    public void slowDown() {
        fallSpeed = levelSpeed;
    }


    private void draw() {
        if (getHolder().getSurface().isValid()) {
            final Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            Boolean[][] cells = grid.getCells();
            for (int y = 0; y < cells.length; y++) {
                for (int x = 0; x < cells[y].length; x++) {
                    if (cells[y][x]) {
                        canvas.drawBitmap(block, x * block.getWidth(), y * block.getHeight(), paint);
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
        int fallDelay = 0;
        int moveDelay = 0;
        while (running) {
            draw();
            if (++fallDelay > (FPS / fallSpeed)) {
                try {
                    grid.fall();
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    pause(); // TODO game over
                }
                fallDelay = 0;
            }
            if (++moveDelay > (FPS / moveSpeed)) {
                grid.move();
                grid.rotate();
                moveDelay = 0;
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