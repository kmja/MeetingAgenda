package com.example.karl.meetingagenda.android.view;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

    ListView listView;


    public ParkedViewController(ParkedView view, AgendaModel model){
        this.model = model;
        this.view = view;

        this.cancel = (Button) view.view.findViewById(R.id.button4);
        cancel.setOnClickListener(onClickListener);
        this.overlay = view.view.findViewById(R.id.overlay);

        // get subviews in overlay
        this.name = (TextView) view.view.findViewById(R.id.textView5);
        this.info = (TextView) view.view.findViewById(R.id.textView6);
        this.description = (TextView) view.view.findViewById(R.id.textView7);

        this.listView = (ListView) view.view.findViewById(R.id.listView2);
        listView.setOnItemClickListener(onItemClickListener);

    }


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // ladda overlay med selectedparked
            Activity selectedact = model.getParkedActivities().get(position);
            //set activity as selected parked activity
            model.setSelectedParked(position);
            //Load overlay fields
            name.setText(selectedact.getName());
            String infoString = selectedact.getLength()+" min ";
            if(selectedact.getType()==1){
                infoString = infoString+"presentation";
            }
            else if(selectedact.getType()==2){
                infoString = infoString+"group work";
            }
            else if(selectedact.getType()==3){
                infoString = infoString+"discussion";
            }
            else if(selectedact.getType()==4){
                infoString = infoString+"break";
            }
            info.setText(infoString);
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
