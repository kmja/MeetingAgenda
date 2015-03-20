package com.example.karl.meetingagenda.android.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;

import com.example.karl.meetingagenda.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.AgendaModel;
import model.Day;
import com.example.karl.meetingagenda.android.view.CustomAdapter;

import org.w3c.dom.Text;

/**
 * Created by fredrik-eliasson on 03/03/15.
 */

public class DayView extends Activity implements Observer {

    public View view;
    AgendaModel model;
    DragNDropListView dragNDropListView;

    public DayView(View view, AgendaModel model, int currentday, LayoutInflater layoutInflater){
        this.view = view;
        this.model = model;
        this.model.addObserver(this);


        EditText start_time = (EditText) view.findViewById(R.id.editText4);
        int minutes = model.getDays().get(model.getCurrentDay()).getStart();
        int hours = minutes/60;
        String startTime = "";
        if(hours<10) {
            startTime = "0"+String.valueOf(hours)+":";
        }
        else{
            startTime = String.valueOf(hours)+":";
        }
        if((minutes-hours*60)<10){
            startTime = startTime+"0";
        }
        startTime = startTime+String.valueOf(minutes-hours*60);
        start_time.setText(startTime);

        TextView dayTitle = (TextView) view.findViewById(R.id.textView3);
        dayTitle.setText("Day " + String.valueOf(currentday+1));

        TextView presentationShare = (TextView) view.findViewById(R.id.presentationShare);
        TextView groupShare = (TextView) view.findViewById(R.id.groupShare);
        TextView discussionShare = (TextView) view.findViewById(R.id.discussionShare);
        TextView breakShare = (TextView) view.findViewById(R.id.breakShare);

        //Create list of types to loop through
        List<TextView> typeList = new ArrayList<>();
        typeList.add(presentationShare);
        typeList.add(groupShare);
        typeList.add(discussionShare);
        typeList.add(breakShare);

        //loopa genom typerna, getta aktiviteter av typen delat på totala aktiviteter. Set weight till den andelen
        //Note: types är fortfarande 1 lägre än de borde vara. Varför? Activities på dag 2 är dessutom satta till 0 båda två,
        //oavsett vad man säger i AgendaModel
        List<model.Activity> activities = model.getDays().get(model.getCurrentDay()).getActivities();
        for(int i=0;i<=3;i++) {
            int typeCount = 0;
            for(int k=0;k<activities.size();k++){
                if(activities.get(k).getType()==i){
                    typeCount++;
                }
            }
            Float typeShare = (float) typeCount/activities.size();
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, typeShare);
            typeList.get(i).setLayoutParams(param);
        }
        // 1. pass context and data to the custom adapter
        //CustomAdapter adapter = new CustomAdapter(this, model.getDays().get(model.getCurrentDay()).getActivities());

        // 2. Get ListView from activity_main.xml
        //ListView listView = (ListView) view.findViewById(R.id.listView);

        // 3. setListAdapter
        //listView.setAdapter(adapter);
        ArrayList<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
        ArrayList<String> acts = new ArrayList<String>();
        String[] activityArr = new String[this.model.getDays().get(this.model.getCurrentDay()).getActivities().size()];

        // create the from array for adapter
        //String[] from = new String[activityArr.length];

        for(int i = 0;i<activityArr.length;i++){
            HashMap<String,Object> item = new HashMap<String,Object>();
            String actname = this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(i).getName();
            String time = String.valueOf(this.model.getDays().get(this.model.getCurrentDay()).getActivities().get(i).getLength());
            activityArr[i] = actname;
            acts.add(actname);
            item.put("name",actname);
            item.put("time",time);
            System.out.println("acti:  " + item.get("name"));
            //item.put("id",i);
            items.add(item);
            //from[i] = "name" + i;
        }


        String[] from = new String[]{"name","time"};

        // drag N drop listview
        //this.dragNDropListView =  (DragNDropListView) view.findViewById(R.id.listView);

        //RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relativeLayoutDay);
        //rl.addView(dragNDropListView);

        //dragNDropListView.setOnItemDragNDropListener(itemDragListener);
        //dragNDropListView.setOnItemClickListener(clickListener);
        //DragNDropSimpleAdapter dragNDropSimpleAdapter = new DragNDropSimpleAdapter(view.getContext(),items,R.layout.row_layout,from,new int[]{R.id.rowTitle,R.id.rowTime},R.id.rowTime);
        //dragNDropListView.setDragNDropAdapter(dragNDropSimpleAdapter);


        // load stuff into overlay
        TextView activityname = (TextView) view.findViewById(R.id.textView5);
        TextView actinfo = (TextView) view.findViewById(R.id.textView6);
        TextView actdescription = (TextView) view.findViewById(R.id.textView7);



        // if there are activities for that day
        if (this.model.getDays().get(this.model.getCurrentDay()).getActivities().size() != 0){
            model.Activity selectedactivity = this.model.getDays().get(model.getCurrentDay()).getActivities().get(this.model.getSelectedActivity());
            activityname.setText(selectedactivity.getName());
            actinfo.setText(selectedactivity.getLength() + " min " + selectedactivity.getType());
            actdescription.setText(selectedactivity.getDescription());
        }
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    DragNDropListView.OnItemDragNDropListener itemDragListener = new DragNDropListView.OnItemDragNDropListener() {
        @Override
        public void onItemDrag(DragNDropListView parent, View view, int position, long id) {
            System.out.println("DRAGGING");
        }

        @Override
        public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id) {
            // Dropping Change position of activity in model
            System.out.println("DROPPING");
        }
    };

    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            System.out.println("CLICK");
        }
        // on adapter view

    };

}