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

        /*ListView listView = (ListView) view.view.findViewById(R.id.listView);

        String[] activityArr = new String[this.model.getDays().get(0).getActivities().size()];
        for(int i = 0;i<activityArr.length;i++){

            activityArr[i] = this.model.getDays().get(0).getActivities().get(i).getName();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.view.view.getContext(),android.R.layout.simple_list_item_1,activityArr);

        listView.setAdapter(arrayAdapter);*/

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