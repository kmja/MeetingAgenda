package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.karl.meetingagenda.R;

import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 03/03/15.
 */
public class ActivityView implements Observer {

    View view;
    AgendaModel model;


    public ActivityView(View view, AgendaModel model){
        this.view = view;
        this.model = model;

        this.model.addObserver(this);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.getCheckedRadioButtonId();
        RadioButton button = (RadioButton) view.findViewById(R.id.radioButton);
        // Preselect to avoid case when no button is selected
        button.setChecked(true);



    }


    @Override
    public void update(Observable observable, Object data) {

    }
}
