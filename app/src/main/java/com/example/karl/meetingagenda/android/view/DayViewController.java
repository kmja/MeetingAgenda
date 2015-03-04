package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karl.meetingagenda.R;

import model.AgendaModel;

/**
 * Created by fredrik-eliasson on 04/03/15.
 */
public class DayViewController implements View.OnFocusChangeListener {

    DayView view;
    AgendaModel model;

    EditText startTime;

    public DayViewController(DayView view, AgendaModel model){

        this.view = view;
        this.model = model;

        this.startTime = (EditText) view.view.findViewById(R.id.editText4);

        this.startTime.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) Context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        startTime.setOnFocusChangeListener(focusHandler);

        //ListView listView =

        startTime.setOnEditorActionListener(editorHandler);



    }

    EditText.OnEditorActionListener editorHandler = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handler = false;

            

            return false;
        }
    };



    View.OnFocusChangeListener focusHandler = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            EditText editText = (EditText) v.findViewById(R.id.editText4);
            if (hasFocus){
                System.out.println("har fokus!, tid: ");
                editText.setText("01:30");
            }
            else{
                System.out.println("inte fokus, tid: " + editText.getText());
            }
        }
};






    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }
}
