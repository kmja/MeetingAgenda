package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */

public class DayViewController implements View.OnFocusChangeListener {

    DayView view;
    AgendaModel model;

    EditText startTime;

    public DayViewController(DayView view, AgendaModel model){

        this.view = view;
        this.model = model;

        this.startTime = (EditText) view.view.findViewById(R.id.editText4);

        //ListView listView =

        // add listeners to edittext
        startTime.setOnEditorActionListener(editorHandler);


        // add gesture recognition for listview

        ListView listView = (ListView) view.view.findViewById(R.id.listView);

        String[] activityArr = new String[10];


        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,this.model.getDays().)


        GestureDetector.OnGestureListener gestureHandler = new GestureDetector.OnGestureListener() {
            private static final int swipe_threshold = 100;
            private static final int swipe_velocity_threshold = 100;
            @Override
            public boolean onDown(MotionEvent e) {
                System.out.println("DOWN");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                System.out.println("SHOW PRESS");

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                System.out.println("SINGLE TAP UP");
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                System.out.println("SCROLL");
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                System.out.println("LONG PRESS");

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
                            System.out.println("SWIPE RIGHT");

                        }
                        else{
                            // SWIPE LEFT
                            System.out.println("SWIPE LEFT");
                        }
                        result = true;

                    }

                }

                return false;
            }
        };

        GestureDetector gestureDetector = new GestureDetector(view.view.getContext(),gestureHandler);




    }

    EditText.OnEditorActionListener editorHandler = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handler = false;
            EditText editText = (EditText) view.view.findViewById(R.id.editText4);
            if (actionId == EditorInfo.IME_ACTION_DONE){
                System.out.println("TIME: " + editText.getText());
                InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);

                handler = true;

            }

            return handler;
        }
    };



    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }
}
