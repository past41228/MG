package cz.sp.mg;

import android.view.MotionEvent;
import android.view.View;

public abstract class HoldButtonListener implements View.OnTouchListener {

    protected abstract Boolean performAction();
    protected abstract void onPress();
    protected abstract void onRelease();

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!performAction()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onPress();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                onRelease();
                return true;
            default:
                return false;
        }
    }
}
