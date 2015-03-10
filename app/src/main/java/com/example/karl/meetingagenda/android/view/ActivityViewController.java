package com.example.karl.meetingagenda.android.view;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.karl.meetingagenda.R;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */
public class ActivityViewController  {

    AgendaModel model;
    ActivityView view;

    public ActivityViewController(ActivityView view, AgendaModel model) {
        this.view = view;
        this.model = model;

        for(int i = 1; i<=4;i++){
            // create radiobuttons
            String btnName = "radioButton" + i;
            int btnid= view.view.getResources().getIdentifier(btnName, "id", view.view.getContext().getPackageName());
            RadioButton rbtn = (RadioButton) view.view.findViewById(btnid);
            rbtn.setOnClickListener(clickListener);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            for (int i = 1; i <= 4; i++) {
                // create radiobuttons
                String btnName = "radioButton" + i;
                int btnid = view.view.getResources().getIdentifier(btnName, "id", view.view.getContext().getPackageName());
                RadioButton rbtn = (RadioButton) view.view.findViewById(btnid);
                if(v.getId() == rbtn.getId()){
                    rbtn.setChecked(true);
                }else{rbtn.setChecked(false);}
            }
        }
    };

   }
