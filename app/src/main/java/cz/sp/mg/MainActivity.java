package cz.sp.mg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private BlocksView blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.blocks = findViewById(R.id.blocks);


        findViewById(R.id.leftButton).setOnClickListener(v -> blocks.moveLeft()); // TODO touch
        findViewById(R.id.rightButton).setOnClickListener(v -> blocks.moveRight()); // TODO touch
        findViewById(R.id.upButton).setOnClickListener(v -> {
            if (blocks.isRunning()) {
                blocks.pause();
            } else {
                blocks.resume();
            }
        });
        findViewById(R.id.downButton).setOnTouchListener((v, event) -> {
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