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

import java.util.Random;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;

    private DismissOverlayView mDismissOverlayView;
    private GestureDetector mGestureDetector;

    private TextView mCell_1;
    private TextView mCell_2;
    private TextView mCell_3;
    private TextView mCell_4;
    private TextView mCell_5;
    private TextView mCell_6;
    private TextView mCell_7;
    private TextView mCell_8;
    private TextView mCell_9;
    private TextView mCell_10;
    private TextView mCell_11;
    private TextView mCell_12;
    private TextView mCell_13;
    private TextView mCell_14;
    private TextView mCell_15;
    private TextView mCell_16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
//                mDismissOverlayView = (DismissOverlayView) findViewById(R.id.dismiss);
                mGestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
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

                mCell_1 = (TextView) stub.findViewById(R.id.cell_1);
                mCell_2 = (TextView) stub.findViewById(R.id.cell_2);
                mCell_3 = (TextView) stub.findViewById(R.id.cell_3);
                mCell_4 = (TextView) stub.findViewById(R.id.cell_4);
                mCell_5 = (TextView) stub.findViewById(R.id.cell_5);
                mCell_6 = (TextView) stub.findViewById(R.id.cell_6);
                mCell_7 = (TextView) stub.findViewById(R.id.cell_7);
                mCell_8 = (TextView) stub.findViewById(R.id.cell_8);
                mCell_9 = (TextView) stub.findViewById(R.id.cell_9);
                mCell_10 = (TextView) stub.findViewById(R.id.cell_10);
                mCell_11 = (TextView) stub.findViewById(R.id.cell_11);
                mCell_12 = (TextView) stub.findViewById(R.id.cell_12);
                mCell_13 = (TextView) stub.findViewById(R.id.cell_13);
                mCell_14 = (TextView) stub.findViewById(R.id.cell_14);
                mCell_15 = (TextView) stub.findViewById(R.id.cell_15);
                mCell_16 = (TextView) stub.findViewById(R.id.cell_16);
            }
        });

        restart();
    }

    private void restart() {
        clear();
        setup();
    }

    private void clear() {
        mCell_1.setText("");
        mCell_2.setText("");
        mCell_3.setText("");
        mCell_4.setText("");
        mCell_5.setText("");
        mCell_6.setText("");
        mCell_7.setText("");
        mCell_8.setText("");
        mCell_9.setText("");
        mCell_10.setText("");
        mCell_11.setText("");
        mCell_12.setText("");
        mCell_13.setText("");
        mCell_14.setText("");
        mCell_15.setText("");
        mCell_16.setText("");
    }

    private void setup() {
        Random r = new Random();
        int cell1 = r.nextInt(16) + 1;
        int cell2;
        do {
            cell2 = r.nextInt(16) + 1;
        } while (cell1 == cell2);


    }

}
