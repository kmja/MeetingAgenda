package com.example.karl.meetingagenda.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.CustomAdapter;
import com.example.karl.meetingagenda.android.view.DayView;
import com.example.karl.meetingagenda.android.view.DayViewController;

import java.util.List;
import java.util.zip.Inflater;

import model.AgendaModel;
import model.Day;

public class DayActivity extends Activity {

    AgendaModel model;
    DayView view;
    GestureDetector gestureDetector;
    int currentday = 0;
    List<Day> days;
    Button addactivitybtn;
    Button parkbtn;
    Button cancelbtn;
    Button editbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);

        Context context = this.getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        // check if intent getextras exist
        Intent intent = getIntent();
        if(intent.getExtras() == null){
            // Means app is started for first time so create new agendamodel
            this.model = new AgendaModel().getModelWithExampleData();
        }else
        {
            this.model = (AgendaModel) intent.getExtras().get("model");
            currentday = (int) intent.getExtras().get("day");
        }
        this.days = model.getDays();
        this.view = new DayView(findViewById(R.id.day_layout), this.model,model.getCurrentDay(),inflater);
        DayViewController dayViewController = new DayViewController(this.view,this.model,model.getCurrentDay());



        // setup on swipe listeners and on click for add activity
        gestureDetector = new GestureDetector(this.view.view.getContext(),gestureHandler);

        ListView listView = (ListView) findViewById(R.id.listView);

        // setup buttons
        this.addactivitybtn = (Button) findViewById(R.id.button3);
        this.parkbtn = (Button) findViewById(R.id.button6);
        this.editbtn = (Button) findViewById(R.id.button5);

        addactivitybtn.setOnClickListener(clickHandler);
        parkbtn.setOnClickListener(clickHandler);
        editbtn.setOnClickListener(clickHandler);
        listView.setOnTouchListener(touchListener);
        view.view.setOnTouchListener(touchListener);
    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            if (v == parkbtn){
                    intent  = new Intent(DayActivity.this,ParkedActivity.class);
                    model.addParkedActivity(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()));
            }else if(v == editbtn){
                    // load activity view with selected activity
                    intent = new Intent(DayActivity.this,ActivityActivity.class);
                    // send additional information in intent!
                    // intent.putextra("activity",activity);

            }
            else{
                    intent = new Intent(DayActivity.this, ActivityActivity.class);
            }
            intent.putExtra("model", model);
            intent.putExtra("day",currentday);
            startActivity(intent);
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
            float diffx = e2.getX()-e1.getX();
            float diffy = e2.getY()-e1.getY();

            boolean result = false;

            if(Math.abs(diffx)>Math.abs(diffy)){
                if(Math.abs(diffx) > swipe_threshold && Math.abs(velocityX)> swipe_velocity_threshold ){
                    if(diffx<0){
                        // SWIPE RIGHT
                        System.out.println("SWIPED RIGHT TO LEFT");
                        if(model.getCurrentDay()+1!=days.size()){
                        model.setCurrentDay(model.getCurrentDay()+1);
                        Intent intent = new Intent(DayActivity.this,DayActivity.class);
                        // put extra. model and currentday
                        intent.putExtra("model",model);
                        intent.putExtra("day",currentday+1);
                        startActivity(intent);
                        }else{
                            model.addDay(8,0);
                            Intent intent = new Intent(DayActivity.this,DayActivity.class);
                            model.setCurrentDay(model.getCurrentDay()+1);
                            // put extra. model and currentday
                            intent.putExtra("model",model);
                            intent.putExtra("day",currentday+1);
                            startActivity(intent);
                        }
                    }
                    else{
                        // SWIPE LEFT
                        System.out.println("SWIPE LEFT TO RIGHT");
                        // to avoid a left swipe when we are on the first screen, perhaps add parked acts here?
                        if(currentday==0) {
                            Intent intent = new Intent(DayActivity.this,ParkedActivity.class);
                            // put extra. model and currentday
                            intent.putExtra("model",model);
                            intent.putExtra("day",currentday);
                            startActivity(intent);
                        }
                        else if(currentday>0){
                        Intent intent = new Intent(DayActivity.this,DayActivity.class);
                        model.setCurrentDay(model.getCurrentDay()-1);
                        // put extra. model and currentday
                        intent.putExtra("model",model);
                        intent.putExtra("day",currentday-1);
                        startActivity(intent);
                        }
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
                System.out.println("OnTouchEVENT");
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
