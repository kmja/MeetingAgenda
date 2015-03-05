package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.karl.meetingagenda.R;

import java.lang.reflect.Array;
import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;

/**
 * Created by Karl on 2015-03-05.
 */
public class ParkedView implements Observer {

    View view;
    AgendaModel model;


    public ParkedView(View view, AgendaModel model) {
        this.view = view;
        this.model = model;

        this.model.addObserver(this);

        ListView listView = (ListView) view.findViewById(R.id.listView);

        String[] parkedList = new String[model.getParkedActivities().size()];
        for(int i=0; i<=parkedList.length; i++){
            parkedList[i] = model.getParkedActivities().get(i).getName();

        }




        ArrayAdapter listAdapter = new ArrayAdapter<String>(this.view.getContext(), android.R.layout.simple_list_item_1, parkedList);


        listView.setAdapter(listAdapter);


    }





    @Override
    public void update(Observable observable, Object data) {

    }
}
