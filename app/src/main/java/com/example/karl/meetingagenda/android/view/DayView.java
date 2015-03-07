package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 03/03/15.
 */

public class DayView implements Observer {

    public View view;
    AgendaModel model;


    public DayView(View view, AgendaModel model, int currentday){
        this.view = view;
        this.model = model;
        this.model.addObserver(this);

        EditText start_time = (EditText) view.findViewById(R.id.editText4);
        int minutes = model.getDays().get(currentday).getStart();
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

        TextView daytitle = (TextView) view.findViewById(R.id.textView3);
        daytitle.setText("Day " + String.valueOf(currentday+1));


        ListView listView = (ListView) view.findViewById(R.id.listView);

        String[] activityArr = new String[this.model.getDays().get(currentday).getActivities().size()];
        for(int i = 0;i<activityArr.length;i++){

            activityArr[i] = this.model.getDays().get(currentday).getActivities().get(i).getName();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this.view.getContext(),android.R.layout.simple_list_item_1,activityArr);

        listView.setAdapter(arrayAdapter);




    }


    @Override
    public void update(Observable observable, Object data) {

    }
}
