package cz.sp.mg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BlocksView blocks;

    @SuppressLint("ClickableViewAccessibility") // TODO https://developer.android.com/guide/topics/ui/accessibility/custom-views#custom-touch-events
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.blocks = findViewById(R.id.blocks);


        findViewById(R.id.leftButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return blocks.isRunning();
            }
            @Override
            protected void onPress() {
                blocks.moveLeft();
            }
            @Override
            protected void onRelease() {
                blocks.fallOnly();
            }
        });
        findViewById(R.id.rightButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return blocks.isRunning();
            }
            @Override
            protected void onPress() {
                blocks.moveRight();
            }
            @Override
            protected void onRelease() {
                blocks.fallOnly();
            }
        });
        findViewById(R.id.downButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return blocks.isRunning();
            }
            @Override
            protected void onPress() {
                blocks.speedUp();
            }
            @Override
            protected void onRelease() {
                blocks.slowDown();
            }
        });
        findViewById(R.id.upButton).setOnClickListener((View v) -> {
            if (blocks.isRunning()) {
                blocks.pause();
            } else {
                blocks.resume();
            }
        });
        findViewById(R.id.rotateButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return blocks.isRunning();
            }
            @Override
            protected void onPress() {
                blocks.rotation();
            }
            @Override
            protected void onRelease() {
                blocks.fallOnly();
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