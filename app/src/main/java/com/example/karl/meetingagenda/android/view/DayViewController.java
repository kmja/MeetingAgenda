package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.ActivityActivity;
import com.example.karl.meetingagenda.android.ParkedActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Activity;
import model.AgendaModel;
import model.Day;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */

public class DayViewController implements View.OnFocusChangeListener {

    DayView view;
    AgendaModel model;
    EditText startTime;
    GestureDetector gestureDetector;
    ListView listView;
    int currentday;
    Button cancelbtn;
    TextView nameText;
    TextView infoText;
    TextView descriptionText;

    View overlay;


    public DayViewController(DayView view, AgendaModel model,int currentday){

        this.view = view;
        this.model = model;
        this.currentday = currentday;

        this.startTime = (EditText) view.view.findViewById(R.id.editText4);

        //ListView listView =
        //this.listView = (ListView) view.view.findViewById(R.id.listView);
        //listView.setOnItemClickListener(itemclickhandler);

        // add listeners to edittext
        startTime.setOnEditorActionListener(editorHandler);

        this.overlay = (View) view.view.findViewById(R.id.overlay);

        cancelbtn = (Button) view.view.findViewById(R.id.button4);
        cancelbtn.setOnClickListener(clickHandler);

        //Setup text fields in overlay
        this.nameText = (TextView) view.view.findViewById(R.id.textView5);
        this.infoText = (TextView) view.view.findViewById(R.id.textView6);
        this.descriptionText = (TextView) view.view.findViewById(R.id.textView7);


        // instantiate dragdroplistview

        ArrayList<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        String[] activityArr = new String[this.model.getDays().get(this.model.getCurrentDay()).getActivities().size()];

        //String[] from = new String[activityArr.length];

        for(int i = 0;i<activityArr.length;i++){
            HashMap<String,Object> item = new HashMap<String,Object>();
            String actname = this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(i).getName();
            String time = String.valueOf(this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(i).getLength());
            // Calculates start time of each activity
            int dayStartTime = this.model.getDays().get(this.model.getCurrentDay()).getStart();
            for(int k=0; k<i; k++){
                dayStartTime += this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(k).getLength();
            }
            // Convert minutes into hour:minute format
            int hours = dayStartTime/60;
            String startTime = "";
            if(hours<10) {
                startTime = "0"+String.valueOf(hours)+":";
            }
            else{
                startTime = String.valueOf(hours)+":";
            }
            if((dayStartTime-hours*60)<10){
                startTime = startTime+"0";
            }
            startTime = startTime+String.valueOf(dayStartTime-hours*60);
            System.out.println(startTime);
            activityArr[i] = actname;
            item.put("name",actname);
            item.put("time",startTime);
            items.add(item);
        }


        String[] from = new String[]{"name","time"};

        // drag N drop listview
        DragNDropListView dragNDropListView = (DragNDropListView) view.view.findViewById(R.id.listView);

        DragNDropSimpleAdapter dragNDropSimpleAdapter = new DragNDropSimpleAdapter(view.view.getContext(),items,R.layout.row_layout,from,new int[]{R.id.rowTitle,R.id.rowTime},R.id.rowMove,model.getDays().get(model.getCurrentDay()).getActivities());

        //RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relativeLayoutDay);
        //rl.addView(dragNDropListView);

        dragNDropListView.setOnItemDragNDropListener(itemDragListener);
        dragNDropListView.setOnItemClickListener(itemclickhandler);
        //DragNDropSimpleAdapter dragNDropSimpleAdapter = new DragNDropSimpleAdapter(view.view.getContext(),items,R.layout.row_layout,from,new int[]{R.id.rowTitle,R.id.rowTime},R.id.rowTime);
        dragNDropListView.setDragNDropAdapter(dragNDropSimpleAdapter);

    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == cancelbtn){
                // hide overlay
                //View overlay = (View) view.findViewById(R.id.overlay);
                overlay.setVisibility(View.GONE);
            }
        }
    };

    DragNDropListView.OnItemDragNDropListener itemDragListener = new DragNDropListView.OnItemDragNDropListener() {
        @Override
        public void onItemDrag(DragNDropListView parent, View view, int position, long id) {
            System.out.println("DRAGGING");
        }

        @Override
        public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id) {
            // Dropping Change position of activity in model
            System.out.println("DROPPING" + "positions: " + startPosition + " " + endPosition);
            model.getDays().get(model.getCurrentDay()).moveActivity(startPosition,endPosition);

        }
    };

    AdapterView.OnItemClickListener itemclickhandler = new android.widget.AdapterView.OnItemClickListener() {


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // handle clicked item.
            Activity act = model.getDays().get(model.getCurrentDay()).getActivities().get(position);

            model.setSelectedact(position);
            // use a field like selectedactivity to know wich activity should be loaded in overlay

            // set visibility of the overlay to visible
            //View overlay = (View) view.findViewById(R.id.overlay);

            //Setting up text on overlay
            nameText.setText(act.getName());
            String infoString = String.valueOf(act.getLength()+" min ");
            if(act.getType()==1){
                infoString = infoString+"presentation";
            }
            else if(act.getType()==2){
                infoString = infoString+"group work";
            }
            else if(act.getType()==3){
                infoString = infoString+"discussion";
            }
            else if(act.getType()==4){
                infoString = infoString+"break";
            }
            infoText.setText(infoString);

            descriptionText.setText(act.getDescription());


            overlay.setVisibility(View.VISIBLE);

        }
    };

    EditText.OnEditorActionListener editorHandler = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handler = false;
            EditText editText = (EditText) view.view.findViewById(R.id.editText4);
            if (actionId == EditorInfo.IME_ACTION_DONE){
                System.out.println("TIME: " + editText.getText());
                InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);

                handler = true;

            }
            return handler;
        }
    };

    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }
}