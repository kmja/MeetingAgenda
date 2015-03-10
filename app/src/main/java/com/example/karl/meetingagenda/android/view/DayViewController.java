package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.Activity;
import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */

public class DayViewController implements View.OnFocusChangeListener {

    DayView view;
    AgendaModel model;
    EditText startTime;
    GestureDetector gestureDetector;
    ListView listView;
    int currentday;

    public DayViewController(DayView view, AgendaModel model,int currentday){

        this.view = view;
        this.model = model;
        this.currentday = currentday;

        this.startTime = (EditText) view.view.findViewById(R.id.editText4);

        //ListView listView =

        this.listView = (ListView) view.view.findViewById(R.id.listView);
        listView.setOnItemClickListener(itemclickhandler);

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

    AdapterView.OnItemClickListener itemclickhandler = new android.widget.AdapterView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // handle clicked item.
            Activity act = model.getDays().get(currentday).getActivities().get(position);

            // use a field like selectedactivity to know wich activity should be loaded in overlay

            // set visibility of the overlay to visible
            View overlay = (View) view.findViewById(R.id.overlay);
            overlay.setVisibility(View.VISIBLE);


        }
    };


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