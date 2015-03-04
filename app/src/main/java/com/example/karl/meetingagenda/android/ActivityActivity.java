package com.example.karl.meetingagenda.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ActivityView;

import model.AgendaModel;


public class ActivityActivity extends android.app.Activity {

    ActivityView view;
    AgendaModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        // create the model. This is temporary since were going to pass model from other activites here
        this.model = new AgendaModel().getModelWithExampleData();

        // Create the Activity View
        this.view = new ActivityView(findViewById(R.id.activity_layout),this.model);







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_, menu);
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
