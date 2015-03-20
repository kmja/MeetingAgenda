package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.Activity;
import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 05/03/15.
 */
public class ParkedViewController {

    AgendaModel model;
    ParkedView view;
    Button cancel;
    View overlay;
    TextView name;
    TextView info;
    TextView description;
    Activity selectedactivity;



    public ParkedViewController(ParkedView view, AgendaModel model){
        this.model = model;
        this.view = view;

        this.cancel = (Button) view.view.findViewById(R.id.button4);
        this.overlay = view.view.findViewById(R.id.overlay);

        // get subviews in overlay
        this.name = (TextView) view.view.findViewById(R.id.textView5);
        this.info = (TextView) view.view.findViewById(R.id.textView6);
        this.description = (TextView) view.view.findViewById(R.id.textView7);



    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ladda overlay med selectedparked
            Activity selectedact = model.getParkedActivities().get(position);
            name.setText(selectedact.getName());
            info.setText(selectedact.getLength() + " min");
            description.setText(selectedact.getDescription());
            overlay.setVisibility(View.VISIBLE);
        }
    };


    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (v == cancel){
                overlay.setVisibility(View.GONE);
            }
        }
    };

}
