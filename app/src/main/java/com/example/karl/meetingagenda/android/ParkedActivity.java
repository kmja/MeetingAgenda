package com.example.karl.meetingagenda.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ParkedView;
import com.example.karl.meetingagenda.android.view.ParkedViewController;

import model.Activity;
import model.AgendaModel;

public class ParkedActivity extends android.app.Activity {

    AgendaModel model;
    ParkedView view;
    GestureDetector gestureDetector;
    int currentday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked_layout);

        Intent intent = getIntent();
        this.model = (AgendaModel) intent.getExtras().get("model");
        this.currentday = (int) intent.getExtras().get("day");

        // Create view and controller
        ParkedView parkedView = new ParkedView(findViewById(R.id.parked_layout),this.model);
        ParkedViewController parkedViewController = new ParkedViewController(parkedView,this.model);

        // Create gesturedetector object
        gestureDetector = new GestureDetector(parkedView.view.getContext(),gestureHandler);

        // get listview and add on touch listener to recognize swipe
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setOnTouchListener(touchListener);
        parkedView.view.setOnTouchListener(touchListener);
    }

    GestureDetector.SimpleOnGestureListener gestureHandler = new GestureDetector.SimpleOnGestureListener() {
        private static final int swipe_threshold = 100;
        private static final int swipe_velocity_threshold = 100;
        @Override
        public boolean onDown(MotionEvent e) {
            System.out.println("DOWN");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffx = e2.getX() - e1.getX();
            float diffy = e2.getY() - e1.getY();

            boolean result = false;

            if (Math.abs(diffx) > Math.abs(diffy)) {
                if (Math.abs(diffx) > swipe_threshold && Math.abs(velocityX) > swipe_velocity_threshold) {
                    if (diffx < 0) {
                        // SWIPE RIGHT
                        System.out.println("SWIPED RIGHT TO LEFT");

                        Intent intent = new Intent(ParkedActivity.this, DayActivity.class);
                        // put extra. model and currentday
                        intent.putExtra("model", model);
                        intent.putExtra("day", currentday);
                        startActivity(intent);
                    }
                }
                result = true;
            }
            return false;
        }
    };

    View.OnTouchListener touchListener = new View.OnTouchListener()  {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            System.out.println("onTouch");

            if (gestureDetector.onTouchEvent(event))
            {
                System.out.println("OnTouchEVENT");
                return true;
            }
            return false;
        }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parked, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
