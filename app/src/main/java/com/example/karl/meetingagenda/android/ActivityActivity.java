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
import android.widget.TextView;

import com.example.karl.meetingagenda.R;
import com.example.karl.meetingagenda.android.view.ActivityView;
import com.example.karl.meetingagenda.android.view.ActivityViewController;

import org.w3c.dom.Text;

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
    RadioButton radiobutton1;
    RadioButton radiobutton2;
    RadioButton radiobutton3;
    RadioButton radiobutton4;
    EditText description;
    TextView titleText;
    int currentday;
    boolean parked = false;
    boolean edit = false;

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
        if (intent.getExtras().get("edit") != null){
            edit = (boolean) intent.getExtras().get("edit");
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

        //if it's an edit of an activity, load the information in the fields
        //if it's a parked activity, load info from parkedActivities instead
        if(edit==true){
            titleText = (TextView) findViewById(R.id.textView);
            titleText.setText("Edit Activity");
            this.radiobutton1 = (RadioButton) findViewById(R.id.radioButton1);
            this.radiobutton2 = (RadioButton) findViewById(R.id.radioButton2);
            this.radiobutton3 = (RadioButton) findViewById(R.id.radioButton3);
            this.radiobutton4 = (RadioButton) findViewById(R.id.radioButton4);
            if(parked == false){
                name.setText(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getName());
                length.setText(String.valueOf(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getLength()));
                if(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getType()==1){
                    radiobutton1.setChecked(true);
                }
                else if(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getType()==2){
                    radiobutton2.setChecked(true);
                }
                else if(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getType()==3){
                    radiobutton3.setChecked(true);
                }
                else if(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getType()==4){
                    radiobutton4.setChecked(true);
                }
                description.setText(model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).getDescription());
            }
            else if(parked == true){
                name.setText(model.getParkedActivities().get(model.getSelectedParked()).getName());
                length.setText(String.valueOf(model.getParkedActivities().get(model.getSelectedParked()).getLength()));
                if(model.getParkedActivities().get(model.getSelectedParked()).getType()==1){
                    radiobutton1.setChecked(true);
                }
                else if(model.getParkedActivities().get(model.getSelectedParked()).getType()==2){
                    radiobutton2.setChecked(true);
                }
                else if(model.getParkedActivities().get(model.getSelectedParked()).getType()==3){
                    radiobutton3.setChecked(true);
                }
                else if(model.getParkedActivities().get(model.getSelectedParked()).getType()==4){
                    radiobutton4.setChecked(true);
                }
                description.setText(model.getParkedActivities().get(model.getSelectedParked()).getDescription());
            }

        }



    }

    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == cancelbtn){
                if (parked == true){
                    Intent intent = new Intent(ActivityActivity.this, ParkedActivity.class);
                    intent.putExtra("model",model);
                    intent.putExtra("day",currentday);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(ActivityActivity.this, DayActivity.class);
                    intent.putExtra("model", model);
                    intent.putExtra("day", currentday);
                    startActivity(intent);
                }
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
                        checkedtype = Integer.valueOf(String.valueOf(rbtn.getContentDescription()));
                    }
                }

                Intent intent = new Intent(ActivityActivity.this, ParkedActivity.class);

                if (parked == true && edit == false){
                    model.addParkedActivity(new Activity(String.valueOf(name.getText()), String.valueOf(description.getText()),
                            Integer.valueOf(String.valueOf(length.getText())), checkedtype));
                }
                else if(parked == false && edit == true){
                    //if it's an edit of an activity, set values of that activity to whatever's in the text fields, and return to dayView
                    model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).setName(String.valueOf(name.getText()));
                    model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).setLength(Integer.valueOf(String.valueOf(length.getText())));
                    model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).setType(checkedtype);
                    model.getDays().get(model.getCurrentDay()).getActivities().get(model.getSelectedActivity()).setDescription(String.valueOf(description.getText()));
                    intent = new Intent(ActivityActivity.this, DayActivity.class);
                }
                else if(parked == true && edit == true){
                    model.getParkedActivities().get(model.getSelectedParked()).setName(String.valueOf(name.getText()));
                    model.getParkedActivities().get(model.getSelectedParked()).setLength(Integer.valueOf(String.valueOf(length.getText())));
                    model.getParkedActivities().get(model.getSelectedParked()).setType(checkedtype);
                    model.getParkedActivities().get(model.getSelectedParked()).setDescription(String.valueOf(description.getText()));
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
