package cz.sp.mg;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private BlocksView blocks;
    private TextView level;
    private TextView score;

    @SuppressLint("ClickableViewAccessibility") // TODO https://developer.android.com/guide/topics/ui/accessibility/custom-views#custom-touch-events
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.blocks = findViewById(R.id.blocks);
        this.level = findViewById(R.id.level);
        this.score = findViewById(R.id.score);
        update();

        findViewById(R.id.leftButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return update();
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
                return update();
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
                return update();
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
        View upButton = findViewById(R.id.upButton);
        upButton.setOnClickListener((View v) -> {
            if (update()) {
                blocks.pause();
                upButton.setBackgroundResource(R.drawable.play);
            } else {
                blocks.resume();
                upButton.setBackgroundResource(R.drawable.pause);
            }
        });
        findViewById(R.id.rotateButton).setOnTouchListener(new HoldButtonListener() {
            @Override
            protected Boolean performAction() {
                return update();
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

    private Boolean update() {
        level.setText(blocks.getLevel());
        score.setText(blocks.getScore());
        return blocks.isRunning();
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