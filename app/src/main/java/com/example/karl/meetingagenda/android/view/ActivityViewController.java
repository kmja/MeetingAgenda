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
public class ActivityViewController implements RadioGroup.OnCheckedChangeListener {

    AgendaModel model;
    ActivityView view;


    public ActivityViewController(ActivityView view, AgendaModel model) {
        this.view = view;
        this.model = model;



        // Set up radio buttons

        RadioGroup radioGroup = (RadioGroup) view.view.findViewById(R.id.radioGroup);
        radioGroup.getCheckedRadioButtonId();
        //RadioButton button = (RadioButton) view.view.findViewById(R.id.radioButton1);
        // Preselect to avoid case when no button is selected
        //button.setChecked(true);

        //radioGroup.setOnCheckedChangeListener(checkedHandler);


        for(int i = 1; i<=4;i++){
            // create radiobuttons
            String btnName = "radioButton" + i;
            int btnid= view.view.getResources().getIdentifier(btnName, "id", view.view.getContext().getPackageName());
            RadioButton rbtn = (RadioButton) view.view.findViewById(btnid);
            rbtn.setOnCheckedChangeListener(checkedChangeListener);

        }


    }

    RadioButton.OnCheckedChangeListener checkedChangeListener = new RadioButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            for(int i = 1; i<=2;i++){
                String groupname = "radioGroup" + i;
                int groupid= view.view.getResources().getIdentifier(groupname, "id", view.view.getContext().getPackageName());
                RadioGroup rgroup = (RadioGroup) view.view.findViewById(groupid);
                rgroup.clearCheck();
            }

            buttonView.setChecked(true);

            /*for(int i = 1; i<=4;i++){
                // create radiobuttons

                String btnName = "radioButton" + i;

                int btnid= view.view.getResources().getIdentifier(btnName, "id", view.view.getContext().getPackageName());
                RadioButton rbtn = (RadioButton) view.view.findViewById(btnid);
                rbtn.setChecked(false);
                System.out.println("ButtonName: " + btnName + " ischecked= " + rbtn.isChecked());

*/

                /*if(rbtn == buttonView){
                    rbtn.setChecked(true);
                }else{rbtn.setChecked(false);}*/
            }

    };


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
