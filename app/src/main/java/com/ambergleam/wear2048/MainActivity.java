package com.ambergleam.wear2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener {

    @SuppressWarnings("unused")
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    private DismissOverlayView mDismissOverlayView;
    private GestureDetectorCompat mDetector;

    private ArrayList<TextView> cells;
    private boolean mPlaying;
    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss);
        mDetector = new GestureDetectorCompat(this, this);

        cells = new ArrayList<TextView>();
        cells.add((TextView) findViewById(R.id.cell_1));
        cells.add((TextView) findViewById(R.id.cell_2));
        cells.add((TextView) findViewById(R.id.cell_3));
        cells.add((TextView) findViewById(R.id.cell_4));
        cells.add((TextView) findViewById(R.id.cell_5));
        cells.add((TextView) findViewById(R.id.cell_6));
        cells.add((TextView) findViewById(R.id.cell_7));
        cells.add((TextView) findViewById(R.id.cell_8));
        cells.add((TextView) findViewById(R.id.cell_9));
        cells.add((TextView) findViewById(R.id.cell_10));
        cells.add((TextView) findViewById(R.id.cell_11));
        cells.add((TextView) findViewById(R.id.cell_12));
        cells.add((TextView) findViewById(R.id.cell_13));
        cells.add((TextView) findViewById(R.id.cell_14));
        cells.add((TextView) findViewById(R.id.cell_15));
        cells.add((TextView) findViewById(R.id.cell_16));

        restart();
    }

    private void restart() {
        clear();
        setup();
        mPlaying = true;
        mScore = 0;
    }

    private void clear() {
        for (TextView cell : cells) {
            setValue(cell, null);
        }
    }

    private void setup() {
        spawn();
        spawn();
    }

    private void gameover() {
        mPlaying = false;
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Score:\n" + mScore)
                .setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        restart();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setValue(TextView cell, Integer value) {
        if (value == null) {
            cell.setText(R.string.empty);
        } else {
            cell.setText(value.toString());
        }
    }

    private void spawn() {
        Random r = new Random();
        int cell;
        do {
            cell = r.nextInt(16);
        } while (cells.get(cell).getText() != getString(R.string.empty));
        int value = r.nextInt(4);
        setValue(cells.get(cell), (value == 0) ? 4 : 2);
    }

    private void onMove(int points) {
        mScore += points;
        spawn();
        if (!doValidMovesExist()) {
            int x = 0;
            for (TextView cell : cells) {
                Log.i(TAG, "Cell " + x + ": " + cell.getText());
                x++;
            }
            gameover();
        }
    }

    private void onLeftSwipe() {
        if (isLeftValid()) {
            onMove(moveLeft());
        }
    }

    private void onRightSwipe() {
        if (isRightValid()) {
            onMove(moveRight());
        }
    }

    private void onUpSwipe() {
        if (isUpValid()) {
            onMove(moveUp());
        }
    }

    private void onDownSwipe() {
        if (isDownValid()) {
            onMove(moveDown());
        }
    }

    private boolean doValidMovesExist() {
        if (!isLeftValid() && !isRightValid() && !isUpValid() && !isDownValid()) {
            return false;
        }
        return true;
    }

    private boolean isLeftValid() {
        for (int i = 0; i < cells.size(); i++) {
            if (canMoveLeft(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRightValid() {
        for (int i = 0; i < cells.size(); i++) {
            if (canMoveRight(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpValid() {
        for (int i = 0; i < cells.size(); i++) {
            if (canMoveUp(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDownValid() {
        for (int i = 0; i < cells.size(); i++) {
            if (canMoveDown(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveLeft(int position) {
        if (position % 4 == 0 || cells.get(position).getText().toString().equals(getString(R.string.empty))) {
            return false;
        } else {
            int leftPosition = position - 1;
            if (cells.get(leftPosition).getText().toString().equals(getString(R.string.empty)) || cells.get(leftPosition).getText().toString().equals(cells.get(position).getText().toString())) {
                return true;
            }
            if (canMoveLeft(leftPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveRight(int position) {
        if (position % 4 == 3 || cells.get(position).getText().toString().equals(getString(R.string.empty))) {
            return false;
        } else {
            int rightPosition = position + 1;
            if (cells.get(rightPosition).getText().toString().equals(getString(R.string.empty)) || cells.get(rightPosition).getText().toString().equals(cells.get(position).getText().toString())) {
                return true;
            }
            if (canMoveRight(rightPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveUp(int position) {
        if (position <= 3 || cells.get(position).getText().toString().equals(getString(R.string.empty))) {
            return false;
        } else {
            int upPosition = position - 4;
            if (cells.get(upPosition).getText().toString().equals(getString(R.string.empty)) || cells.get(upPosition).getText().toString().equals(cells.get(position).getText().toString())) {
                return true;
            }
            if (canMoveUp(upPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean canMoveDown(int position) {
        if (position >= 12 || cells.get(position).getText().toString().equals(getString(R.string.empty))) {
            return false;
        } else {
            int downPosition = position + 4;
            if (cells.get(downPosition).getText().toString().equals(getString(R.string.empty)) || cells.get(downPosition).getText().toString().equals(cells.get(position).getText().toString())) {
                return true;
            }
            if (canMoveDown(downPosition)) {
                return true;
            }
        }
        return false;
    }


    private int moveLeft() {
        int points = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int position = j * 4 + i;
                points += moveLeft(position);
            }
        }
        return points;
    }

    private int moveLeft(int position) {
        if (!canMoveLeft(position)) {
            return 0;
        }
        String image = cells.get(position).getText().toString();
        int leftPosition = position - 1;
        if (cells.get(leftPosition).getText().toString().equals(getString(R.string.empty))) {
            cells.get(position).setText(R.string.empty);
            cells.get(leftPosition).setText(image);
            return moveLeft(leftPosition);
        } else if (cells.get(leftPosition).getText().toString().equals(image)) {
            int amount = Integer.parseInt(image) * 2;
            cells.get(position).setText(R.string.empty);
            cells.get(leftPosition).setText(((Integer) amount).toString());
            return amount;
        }
        return 0;
    }

    private int moveRight() {
        int points = 0;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                int position = j * 4 + i;
                points += moveRight(position);
            }
        }
        return points;
    }

    private int moveRight(int position) {
        if (!canMoveRight(position)) {
            return 0;
        }
        String image = cells.get(position).getText().toString();
        int rightPosition = position + 1;
        if (cells.get(rightPosition).getText().toString().equals(getString(R.string.empty))) {
            cells.get(position).setText(R.string.empty);
            cells.get(rightPosition).setText(image);
            return moveRight(rightPosition);
        } else if (cells.get(rightPosition).getText().toString().equals(image)) {
            int amount = Integer.parseInt(image) * 2;
            cells.get(position).setText(R.string.empty);
            cells.get(rightPosition).setText(((Integer) amount).toString());
            return amount;
        }
        return 0;
    }

    private int moveUp() {
        int points = 0;
        for (int i = 0; i < 16; i++) {
            points += moveUp(i);
        }
        return points;
    }

    private int moveUp(int position) {
        if (!canMoveUp(position)) {
            return 0;
        }
        String image = cells.get(position).getText().toString();
        int upPosition = position - 4;
        if (cells.get(upPosition).getText().toString().equals(getString(R.string.empty))) {
            cells.get(position).setText(R.string.empty);
            cells.get(upPosition).setText(image);
            return moveUp(upPosition);
        } else if (cells.get(upPosition).getText().toString().equals(image)) {
            int amount = Integer.parseInt(image) * 2;
            cells.get(position).setText(R.string.empty);
            cells.get(upPosition).setText(((Integer) amount).toString());
            return amount;
        }
        return 0;
    }

    private int moveDown() {
        int points = 0;
        for (int i = 15; i >= 0; i--) {
            points += moveDown(i);
        }
        return points;
    }

    private int moveDown(int position) {
        if (!canMoveDown(position)) {
            return 0;
        }
        String image = cells.get(position).getText().toString();
        int downPosition = position + 4;
        if (cells.get(downPosition).getText().toString().equals(getString(R.string.empty))) {
            cells.get(position).setText(R.string.empty);
            cells.get(downPosition).setText(image);
            return moveDown(downPosition);
        } else if (cells.get(downPosition).getText().toString().equals(image)) {
            int amount = Integer.parseInt(image) * 2;
            cells.get(position).setText(R.string.empty);
            cells.get(downPosition).setText(((Integer) amount).toString());
            return amount;
        }
        return 0;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (!mPlaying) {
            return true;
        }
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
                return false;
            }
            // left swipe -> right to left
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                onLeftSwipe();
            }
            // right swipe -> left to right
            else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                onRightSwipe();
            }
            // up swipe -> down to up
            else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onUpSwipe();
            }
            // down swipe -> up to down
            else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                onDownSwipe();
            }
        } catch (Exception e) {
            Log.i(TAG, "Exception: " + e.toString());
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mDismissOverlayView.show();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

}
