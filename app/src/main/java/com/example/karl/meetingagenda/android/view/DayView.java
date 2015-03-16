package com.example.karl.meetingagenda.android.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;

import com.example.karl.meetingagenda.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;
import model.Day;
import com.example.karl.meetingagenda.android.view.CustomAdapter;

/**
 * Created by fredrik-eliasson on 03/03/15.
 */

public class DayView extends Activity implements Observer {

    public View view;
    AgendaModel model;


    public DayView(View view, AgendaModel model, int currentday, LayoutInflater layoutInflater){
        this.view = view;
        this.model = model;
        this.model.addObserver(this);


        EditText start_time = (EditText) view.findViewById(R.id.editText4);
        int minutes = model.getDays().get(model.getCurrentDay()).getStart();
        int hours = minutes/60;
        String startTime = "";
        if(hours<10) {
            startTime = "0"+String.valueOf(hours)+":";
        }
        else{
            startTime = String.valueOf(hours)+":";
        }
        if((minutes-hours*60)<10){
            startTime = startTime+"0";
        }
        startTime = startTime+String.valueOf(minutes-hours*60);
        start_time.setText(startTime);

        TextView dayTitle = (TextView) view.findViewById(R.id.textView3);
        dayTitle.setText("Day " + String.valueOf(currentday+1));

        // 1. pass context and data to the custom adapter
        //CustomAdapter adapter = new CustomAdapter(this, model.getDays().get(model.getCurrentDay()).getActivities());

        // 2. Get ListView from activity_main.xml
        //ListView listView = (ListView) view.findViewById(R.id.listView);

        // 3. setListAdapter
        //listView.setAdapter(adapter);
        ArrayList<String> acts = new ArrayList<String>();
        String[] activityArr = new String[this.model.getDays().get(this.model.getCurrentDay()).getActivities().size()];
        for(int i = 0;i<activityArr.length;i++){
            String actname = this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(i).getName();
            activityArr[i] = actname;
            acts.add(actname);
        }

        com.example.karl.meetingagenda.android.view.DynamicListView dynamicListView = new DynamicListView(view.getContext());

        com.example.karl.meetingagenda.android.view.ListAdapter listAdapter = new com.example.karl.meetingagenda.android.view.ListAdapter(this,this.model.getDays().get(this.model.getCurrentDay()).getActivities(),layoutInflater);

        dynamicListView.setAdapter(listAdapter);

        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relativeLayoutDay);
        rl.addView(dynamicListView);

        //listView.setAdapter(listAdapter);
        //dynamicListView.init(view.getContext());

        dynamicListView.setCheeseList(acts);

        // load stuff into overlay
        TextView activityname = (TextView) view.findViewById(R.id.textView5);
        TextView actinfo = (TextView) view.findViewById(R.id.textView6);
        TextView actdescription = (TextView) view.findViewById(R.id.textView7);


        // if there are activities for that day
        if (this.model.getDays().get(this.model.getCurrentDay()).getActivities().size() != 0){
            model.Activity selectedactivity = this.model.getDays().get(model.getCurrentDay()).getActivities().get(this.model.getSelectedActivity());
            activityname.setText(selectedactivity.getName());
            actinfo.setText(selectedactivity.getLength() + " min " + selectedactivity.getType());
            actdescription.setText(selectedactivity.getDescription());
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}