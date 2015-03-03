package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.EditText;

import com.example.karl.meetingagenda.R;

import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 03/03/15.
 */

public class DayView implements Observer {

    View view;
    AgendaModel model;


    public DayView(View view, AgendaModel model){
        this.view = view;
        this.model = model;
        this.model.addObserver(this);

        EditText start_time = (EditText) view.findViewById(R.id.editText4);




    }


    @Override
    public void update(Observable observable, Object data) {

    }
}
