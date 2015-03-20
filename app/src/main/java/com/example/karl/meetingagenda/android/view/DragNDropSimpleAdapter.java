package com.example.karl.meetingagenda.android.view;
/*
 * Copyright 2012 Terlici Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//package com.terlici.dragndroplist;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.Activity;

public class DragNDropSimpleAdapter extends SimpleAdapter implements DragNDropAdapter {
	int mPosition[];
	int mHandler;
	List<Activity> activities;

	public DragNDropSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to, int handler, List<Activity> activities) {
		super(context, data, resource, from, to);

        this.activities = activities;

		mHandler = handler;
		setup(data.size());
	}

	private void setup(int size) {
		mPosition = new int[size];
		
		for (int i = 0; i < size; ++i) mPosition[i] = i;
	}
	
	@Override
	public View getDropDownView(int position, View view, ViewGroup group) {
		return super.getDropDownView(mPosition[position], view, group);
	}
	
	@Override
	public Object getItem(int position) {
		return super.getItem(mPosition[position]);
	}
	
	@Override
	public int getItemViewType(int position) {
		return super.getItemViewType(mPosition[position]);
	}
	
	@Override
	public long getItemId(int position) {
		return super.getItemId(mPosition[position]);
	}
	
	@Override
	public View getView(int position, View view, ViewGroup group) {
        View row = super.getView(mPosition[position], view, group);
        LinearLayout background =(LinearLayout) row.findViewById(R.id.rowBackground);
        TextView time = (TextView) row.findViewById(R.id.rowTime);

        // Det borde gå att lägga in starttider på aktiviteter rätt lätt om man
        // bara kan ladda in model på något sätt

        if(activities.get(position).getType() == 1){
            background.setBackgroundColor(Color.parseColor("#00628b"));
            background.setAlpha(0.8f);
        }else if(activities.get(position).getType() == 2){
            background.setBackgroundColor(Color.parseColor("#29aba4"));
            background.setAlpha(0.8f);
        }
        else if(activities.get(position).getType() == 3){
            background.setBackgroundColor(Color.parseColor("#eb7260"));
            background.setAlpha(0.8f);
        }
        else if(activities.get(position).getType() == 4){
            background.setBackgroundColor(Color.parseColor("#3a9ad9"));
            background.setAlpha(0.8f);
        }

        return row;
    }
	
	@Override
	public boolean isEnabled(int position) {
		return super.isEnabled(mPosition[position]);
	}

	@Override
	public void onItemDrag(DragNDropListView parent, View view, int position, long id) {
		
	}

	@Override
	public void onItemDrop(DragNDropListView parent, View view, int startPosition, int endPosition, long id) {
		int position = mPosition[startPosition];
		
		if (startPosition < endPosition)
			for(int i = startPosition; i < endPosition; ++i)
				mPosition[i] = mPosition[i + 1];
		else if (endPosition < startPosition)
			for(int i = startPosition; i > endPosition; --i)
				mPosition[i] = mPosition[i - 1];
		
		mPosition[endPosition] = position;
	}

	@Override
	public int getDragHandler() {
		return mHandler;
	}
}
