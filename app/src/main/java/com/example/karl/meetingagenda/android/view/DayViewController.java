package com.example.karl.meetingagenda.android.view;

import android.content.Context;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

        //ListView listView =

        startTime.setOnEditorActionListener(editorHandler);



    }

    EditText.OnEditorActionListener editorHandler = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handler = false;
            EditText editText = (EditText) view.view.findViewById(R.id.editText4);
            if (actionId == EditorInfo.IME_ACTION_DONE){
                System.out.println("TIME: " + editText.getText());
                InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromInputMethod(editText.getWindowToken(),0);

                handler = true;
            }


            return handler;
        }
    };



/*    View.OnFocusChangeListener focusHandler = new View.OnFocusChangeListener() {
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
};*/






    @Override
    public void onFocusChange(View v, boolean hasFocus) {


    }
}
