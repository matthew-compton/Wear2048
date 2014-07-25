package com.ambergleam.wear2048;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.wearable.view.DismissOverlayView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    private DismissOverlayView mDismissOverlayView;
    private GestureDetector mGestureDetector;

    private ArrayList<TextView> cells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss);
                mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        Log.i(TAG, "onDown()");
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        Log.i(TAG, "onLongPress()");
                        mDismissOverlayView.show();
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        Log.i(TAG, "onFling()");
                        return true;
                    }
                });

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
        });
    }

    private void restart() {
        clear();
        setup();
    }

    private void clear() {
        for (TextView cell : cells) {
            setValue(cell, null);
        }
    }

    private void setup() {
        Random r = new Random();
        int cell1 = r.nextInt(16) + 1;
        int cell2;
        do {
            cell2 = r.nextInt(16) + 1;
        } while (cell1 == cell2);
        int value1 = r.nextInt(4) + 1;
        int value2 = r.nextInt(4) + 1;
        setValue(cells.get(cell1), (value1 == 1) ? 4 : 2);
        setValue(cells.get(cell2), (value2 == 1) ? 4 : 2);
    }

    private void setValue(TextView cell, Integer value) {
        if (value == null) {
            cell.setText(R.string.empty);
        } else {
            cell.setText(value.toString());
        }
    }


}
