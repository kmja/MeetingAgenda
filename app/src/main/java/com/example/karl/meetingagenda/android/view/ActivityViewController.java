package com.example.karl.meetingagenda.android.view;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.karl.meetingagenda.R;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */
public class ActivityViewController implements RadioGroup.OnCheckedChangeListener {

    AgendaModel model;
    ActivityView view;


    public ActivityViewController(ActivityView view, AgendaModel model) {
        this.view = view;
        this.model = model;



        // Set up radio buttons

        RadioGroup radioGroup = (RadioGroup) view.view.findViewById(R.id.radioGroup);
        radioGroup.getCheckedRadioButtonId();
        RadioButton button = (RadioButton) view.view.findViewById(R.id.radioButton);
        // Preselect to avoid case when no button is selected
        button.setChecked(true);


        radioGroup.setOnCheckedChangeListener(checkedHandler);



    }


    RadioGroup.OnCheckedChangeListener checkedHandler = new RadioGroup.OnCheckedChangeListener(){


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            System.out.println(group.getCheckedRadioButtonId());
            System.out.println("Radio BUTTON");



        }


    };





    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {



    }
}
