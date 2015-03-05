package com.example.karl.meetingagenda.android;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.DayView;
import com.example.karl.meetingagenda.android.view.DayViewController;

import java.util.List;

import model.AgendaModel;
import model.Day;

public class DayActivity extends Activity {

    AgendaModel model;
    DayView view;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
        model = new AgendaModel().getModelWithExampleData();
        List<Day> days = model.getDays();
        Day day1 = days.get(0);

        this.view = new DayView(findViewById(R.id.day_layout), this.model);
        DayViewController dayViewController = new DayViewController(this.view,this.model);


        // setup on swipe listeners and on click for add activity
        gestureDetector = new GestureDetector(this.view.view.getContext(),gestureHandler);


        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setOnTouchListener(touchListener);

        view.view.setOnTouchListener(touchListener);

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
            float diffx = e2.getX()-e1.getX();
            float diffy = e2.getY()-e1.getY();

            boolean result = false;

            if(Math.abs(diffx)>Math.abs(diffy)){
                if(Math.abs(diffx) > swipe_threshold && Math.abs(velocityX)> swipe_velocity_threshold ){
                    if(diffx<0){
                        // SWIPE RIGHT
                        System.out.println("SWIPED RIGHT TO LEFT");

                    }
                    else{
                        // SWIPE LEFT
                        System.out.println("SWIPE LEFT TO RIGHT");
                    }
                    result = true;
                }
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
                System.out.println("OnToucheEVNET");
                return true;
            }
            return false;
        }


    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day, menu);
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
