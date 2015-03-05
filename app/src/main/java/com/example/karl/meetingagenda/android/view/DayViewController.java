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
    GestureDetector gestureDetector;

    public DayViewController(DayView view, AgendaModel model){

        this.view = view;
        this.model = model;

        this.startTime = (EditText) view.view.findViewById(R.id.editText4);

        //ListView listView =

        // add listeners to edittext
        startTime.setOnEditorActionListener(editorHandler);

        // add gesture recognition for listview

        ListView listView = (ListView) view.view.findViewById(R.id.listView);

        String[] activityArr = new String[this.model.getDays().get(0).getActivities().size()];
        for(int i = 0;i<activityArr.length;i++){

            activityArr[i] = this.model.getDays().get(0).getActivities().get(i).getName();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.view.view.getContext(),android.R.layout.simple_list_item_1,activityArr);

        listView.setAdapter(arrayAdapter);

        //gestureDetector = new GestureDetector(view.view.getContext(),gestureHandler);

        //this.view.view.setOnTouchListener(touchListener);

    }
    /*GestureDetector.SimpleOnGestureListener gestureHandler = new GestureDetector.SimpleOnGestureListener() {
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
*/


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