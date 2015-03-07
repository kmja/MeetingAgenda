package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karl.meetingagenda.R;

import java.lang.reflect.Array;
import java.util.Observable;
import java.util.Observer;

import model.Activity;
import model.AgendaModel;

/**
 * Created by Karl on 2015-03-05.
 */
public class ParkedView implements Observer {

    public View view;
    AgendaModel model;
    ListView listView;

    public ParkedView(View view, AgendaModel model) {
        this.view = view;
        this.model = model;

        this.model.addObserver(this);

     /*   ListView listView = (ListView) view.findViewById(R.id.listView);

        String[] parkedList = new String[model.getParkedActivities().size()];
        for(int i=0; i<=parkedList.length; i++){
            parkedList[i] = model.getParkedActivities().get(i).getName();

        }*/

        // load parkedactivities into listview.
        this.listView = (ListView) view.findViewById(R.id.listView2);


        String[] activityname = new String[this.model.getParkedActivities().size()];
        int i = 0;
        for (Activity act: this.model.getParkedActivities()){
            activityname[i] = act.getName();
            i++;
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this.view.getContext(),android.R.layout.simple_list_item_1,activityname);

        //ArrayAdapter listAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_list_item_1, parkedList);

        listView.setAdapter(arrayAdapter);


    }





    @Override
    public void update(Observable observable, Object data) {

    }
}
