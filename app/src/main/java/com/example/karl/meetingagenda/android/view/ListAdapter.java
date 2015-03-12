package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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


    public ListAdapter(Context context,List<Activity> activities){

        this.inflater = LayoutInflater.from(context);
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
            holder.length = (TextView)view.findViewById(R.id.rowTime);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        holder.name.setText(activities.get(position).getName());
        holder.length.setText(activities.get(position).getLength());

        return view;
    }
    private class ViewHolder{
        public TextView name, length;
    }
}
