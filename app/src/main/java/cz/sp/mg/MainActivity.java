package cz.sp.mg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BlocksView blocks;

    @SuppressLint("ClickableViewAccessibility") // TODO https://developer.android.com/guide/topics/ui/accessibility/custom-views#custom-touch-events
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.blocks = findViewById(R.id.blocks);


        findViewById(R.id.leftButton).setOnClickListener((View v) -> {
            if (blocks.isRunning()) blocks.moveLeft();
        });
        findViewById(R.id.rightButton).setOnClickListener((View v) -> {
            if (blocks.isRunning()) blocks.moveRight();
        });
        findViewById(R.id.upButton).setOnClickListener((View v) -> {
            if (blocks.isRunning()) {
                blocks.pause();
            } else {
                blocks.resume();
            }
        });
        findViewById(R.id.downButton).setOnTouchListener((View v, MotionEvent event) -> {
            if (!blocks.isRunning()) {
                return false;
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    blocks.speedUp();
                    return true;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    blocks.slowDown();
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        blocks.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        blocks.resume();
    }
}