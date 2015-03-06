package com.example.karl.meetingagenda.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ParkedView;

import model.Activity;
import model.AgendaModel;

public class ParkedActivity extends android.app.Activity {

    AgendaModel model;
    ParkedView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parked_layout);

        Intent intent = getIntent();
        this.model = (AgendaModel) intent.getExtras().get("model");

        // Create view and controller
        //ParkedView parkedView = new ParkedView()


        // load parkedactivities into listview.




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_parked, menu);
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
