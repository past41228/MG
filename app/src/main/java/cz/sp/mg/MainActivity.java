package cz.sp.mg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    BlocksView blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        blocks = findViewById(R.id.blocks);
        findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blocks.moveLeft();
            }
        });
        findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blocks.moveRight();
            }
        });
        findViewById(R.id.upButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (blocks.getRunning()) {
                    blocks.pause();
                } else {
                    blocks.resume();
                }
            }
        });
        findViewById(R.id.downButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BlocksView.DEFAULT_SPEED.equals(blocks.getSpeed())) {
                    blocks.setSpeed(BlocksView.DEFAULT_SPEED / 2);
                } else {
                    blocks.setSpeed(BlocksView.DEFAULT_SPEED);
                }
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