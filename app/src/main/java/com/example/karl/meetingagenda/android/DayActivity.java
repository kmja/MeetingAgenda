package com.example.karl.meetingagenda.android;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.DayView;
import com.example.karl.meetingagenda.android.view.DayViewController;


import java.util.List;

import model.AgendaModel;
import model.Day;

public class DayActivity extends Activity {

    AgendaModel model;
    DayView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_layout);
        model = new AgendaModel().getModelWithExampleData();
        List<Day> days = model.getDays();
        Day day1 = days.get(0);

        view = new DayView(findViewById(R.id.day_layout), this.model);
        DayViewController dayViewController = new DayViewController(this.view,this.model);


        // setup on swipe listeners and on click for add activity



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
