package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.karl.meetingagenda.R;

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

        ListView parkedList = (ListView) view.findViewById(R.id.listView);

        parkedList.


    }





    @Override
    public void update(Observable observable, Object data) {

    }
}
