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
