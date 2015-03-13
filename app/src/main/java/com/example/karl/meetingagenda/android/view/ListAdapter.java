package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import java.util.List;

import model.Activity;

/**
 * Created by fredrik-eliasson on 12/03/15.
 */
public class ListAdapter extends BaseAdapter
{
    LayoutInflater inflater;
    List<Activity> activities;


    public ListAdapter(Context context,List<Activity> activities, LayoutInflater layoutInflater){
        layoutInflater.getContext();
        this.inflater = layoutInflater;
        this.activities = activities;

    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null){
            view = inflater.inflate(R.layout.row_layout,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView)view.findViewById(R.id.rowTitle);
            holder.time = (TextView)view.findViewById(R.id.rowTime);
            holder.background = (LinearLayout)view.findViewById(R.id.rowBackground);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        holder.name.setText(activities.get(position).getName());

        // Calculates start time of each activity
        int activityStart = 480; // måste hämta från currentday istället för att hårdkoda.
        for(int i=0; i>position; i++){
            activityStart += activities.get(i).getLength(); //funkar inte efter första activityn. Ingen aning varför. Nu på slutet verkar det inte funka någonstans.
            System.out.println("Adding activity "+activities.get(position).getName()+": "+activities.get(i).getLength()+" minutes.");
            System.out.println("Position = "+position);
        }
        // Convert minutes into hour:minute format
        int hours = activityStart/60;
        String startTime = "";
        if(hours<10) {
            startTime = "0"+String.valueOf(hours)+":";
        }
        else{
            startTime = String.valueOf(hours)+":";
        }
        if((activityStart-hours*60)<10){
            startTime = startTime+"0";
        }
        startTime = startTime+String.valueOf(activityStart-hours*60);
        holder.time.setText(startTime);

        // Setting background color based on activity type
        if(activities.get(position).getType()==1-1){ //alla typer är 1 lägre än de ska vara - varför??
            holder.background.setBackgroundColor(Color.parseColor("#00628b"));
            //holder.background.setAlpha((float) 0.6);
        }
        else if(activities.get(position).getType()==2-1){
            holder.background.setBackgroundColor(Color.parseColor("#29aba4"));
            //holder.background.setAlpha((float) 0.6);
        }
        else if(activities.get(position).getType()==3-1){
            holder.background.setBackgroundColor(Color.parseColor("#eb7260"));
            //holder.background.setAlpha((float) 0.6);
        }
        else if(activities.get(position).getType()==4-1){
            holder.background.setBackgroundColor(Color.parseColor("#3a9ad9"));
            //holder.background.setAlpha((float) 0.6);
        }

        return view;
    }
    private class ViewHolder{
        public TextView name, time;
        public LinearLayout background;
    }
}
