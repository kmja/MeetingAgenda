package com.example.karl.meetingagenda.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ActivityView;
import com.example.karl.meetingagenda.android.view.ActivityViewController;

import model.Activity;
import model.AgendaModel;


public class ActivityActivity extends android.app.Activity implements View.OnClickListener {

    ActivityView view;
    AgendaModel model;
    Button savebtn;
    Button cancelbtn;
    EditText name;
    EditText length;
    RadioGroup radioGroup;
    EditText description;
    int currentday;
    RadioButton radioButton;
    boolean parked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        // create the model. This is temporary since were going to pass model from other activites here
        //this.model = new AgendaModel().getModelWithExampleData();

        Intent intent = getIntent();
        this.model = (AgendaModel) intent.getExtras().get("model");
        this.currentday = (int) intent.getExtras().get("day");


        if (intent.getExtras().get("parked") != null){
            parked = (boolean) intent.getExtras().get("parked");
        }



        // Create the Activity View
        this.view = new ActivityView(findViewById(R.id.activity_layout),this.model);

        ActivityViewController activityViewController = new ActivityViewController(this.view,this.model);

        // Setup on click listeners for save and cancel activity


        cancelbtn = (Button) findViewById(R.id.button);
        savebtn = (Button) findViewById(R.id.button2);

        cancelbtn.setOnClickListener(clickHandler);
        savebtn.setOnClickListener(clickHandler);

        // Setup input fields

        name = (EditText) findViewById(R.id.editText);
        length = (EditText) findViewById(R.id.editText2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        description = (EditText) findViewById(R.id.editText3);





    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == cancelbtn){
                Intent intent = new Intent(ActivityActivity.this, DayActivity.class);
                intent.putExtra("model", model);
                intent.putExtra("day",currentday);
                startActivity(intent);
            }
            else if(v == savebtn){
                // get user input and add to parked activities
                //int buttonid = radioGroup.getCheckedRadioButtonId();
                //radioGroup.getCheckedRadioButtonId();
                //System.out.println(buttonid);
                //RadioButton radioBtn = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                //String typee = String.valueOf(radioBtn.getText());
                //int radioID = Integer.valueOf(String.valueOf(radioBtn.getContentDescription()));
                int checkedtype = 0;
                for(int i = 1; i<=4;i++){

                    String viewname = "radioButton" + i;
                    int viewID = getResources().getIdentifier(viewname,"id",getPackageName());
                    RadioButton rbtn = (RadioButton) findViewById(viewID);
                    if (rbtn.isChecked()){
                        checkedtype = Integer.valueOf(String.valueOf(rbtn.getContentDescription()))-1;
                    }
                }

                Intent intent = new Intent(ActivityActivity.this, ParkedActivity.class);
                
                if (parked == true){
                    model.addParkedActivity(new Activity(String.valueOf(name.getText()), String.valueOf(description.getText()),
                            Integer.valueOf(String.valueOf(length.getText())));
                }
                else {

                    model.addActivity(new Activity(String.valueOf(name.getText()), String.valueOf(description.getText()),
                    Integer.valueOf(String.valueOf(length.getText())), checkedtype), model.getDays().get(currentday), -1);
                    intent = new Intent(ActivityActivity.this, DayActivity.class);
                }
                intent.putExtra("model", model);
                intent.putExtra("day",currentday);
                startActivity(intent);
            }
        }
    };


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




    @Override
    public void onClick(View v) {

    }
}
