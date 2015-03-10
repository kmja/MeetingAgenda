package com.example.karl.meetingagenda.android.view;

/**
 * Created by Karl on 2015-03-10.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.Activity;


public class CustomAdapter extends ArrayAdapter<Activity> {

    private final Context context;
    private final List<Activity> actList;

    public CustomAdapter(Context context, List<Activity> actList) {

        super(context, R.layout.row_layout, actList);

        this.context = context;
        this.actList = actList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);

        // 3. Get the two text view from the rowView
        TextView rowTime = (TextView) rowView.findViewById(R.id.rowTime);
        TextView rowTitle = (TextView) rowView.findViewById(R.id.rowTitle);

        // 4. Set the text for textView
        rowTime.setText(actList.get(position).getLength());
        rowTitle.setText(actList.get(position).getName());

        // 5. return rowView
        return rowView;
    }
}