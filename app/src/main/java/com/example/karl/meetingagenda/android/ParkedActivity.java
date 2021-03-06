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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ParkedView;
import com.example.karl.meetingagenda.android.view.ParkedViewController;

import model.Activity;
import model.AgendaModel;



public class ParkedActivity extends android.app.Activity {

    AgendaModel model;
    ParkedView view;
    GestureDetector gestureDetector;
    TextView arrowRightbtn;
    Button addButton;
    int currentday;
    View overlay;
    Button cancel;
    Button edit;
    Button move;
    // set these to visible
    TextView addtoday;
    EditText chooseday;


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

        // get overlay and buttons
        this.overlay = findViewById(R.id.overlay);
        this.edit = (Button) findViewById(R.id.button5);
        edit.setOnClickListener(clickHandler);
        this.move = (Button) findViewById(R.id.button6);
        move.setOnClickListener(clickHandler);
        move.setText("Move");

        // edittext for
        this.chooseday = (EditText) findViewById(R.id.editText5);
        this.addtoday  = (TextView) findViewById(R.id.textView11);
        chooseday.setVisibility(View.VISIBLE);
        addtoday.setVisibility(View.VISIBLE);

        //setup arrow button
        this.arrowRightbtn = (TextView) findViewById(R.id.arrowRight);
        arrowRightbtn.setOnClickListener(clickHandler);
        //setup add activity button
        this.addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(clickHandler);
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ParkedActivity.this,ActivityActivity.class);
            boolean parked = false;
            if (v == arrowRightbtn){
                intent = new Intent(ParkedActivity.this, DayActivity.class);
                // put extra. model and currentday
                intent.putExtra("model", model);
                intent.putExtra("day", currentday);
                startActivity(intent);
            }
            else if(v == addButton){
                intent = new Intent(ParkedActivity.this, ActivityActivity.class);
                intent.putExtra("model", model);
                intent.putExtra("day", currentday);
                parked = true;
                intent.putExtra("parked",parked);
                startActivity(intent);
            }
            else if (v == move){
                intent = new Intent(ParkedActivity.this,ParkedActivity.class);
                intent.putExtra("model",model);
                intent.putExtra("day",currentday);

                // check so that the day entered in the edittext exists and if so move activity from parked to that day.
                int day = Integer.valueOf(String.valueOf(chooseday.getText()));
                if(day <= model.getDays().size() && day != 0){
                    System.out.println("day: " + day);
                    model.addActivity(model.getParkedActivities().get(model.getSelectedParked()),model.getDays().get(day-1),-1);
                    model.removeParkedActivity(model.getSelectedParked());
                    startActivity(intent);
                }

            }
            else if(v == edit){
                intent.putExtra("model",model);
                intent.putExtra("day",currentday);
                intent.putExtra("parked",true);
                intent.putExtra("edit",true);
                startActivity(intent);
            }
        }
    };

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