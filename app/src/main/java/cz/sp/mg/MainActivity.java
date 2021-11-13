package cz.sp.mg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BlocksView blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.blocks = findViewById(R.id.blocks);

        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() { // TODO touch
            @Override
            public void onClick(View v) {
                blocks.moveLeft();
            }
        });
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() { // TODO touch
            @Override
            public void onClick(View v) {
                blocks.moveRight();
            }
        });
        findViewById(R.id.upButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blocks.isRunning()) {
                    blocks.pause();
                } else {
                    blocks.resume();
                }
            }
        });
        findViewById(R.id.downButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        blocks.speedUp();
                        return true;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        blocks.slowDown();
                        return true;
                }
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