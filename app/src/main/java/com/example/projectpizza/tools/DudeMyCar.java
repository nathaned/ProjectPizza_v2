package com.example.projectpizza.tools;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectpizza.R;

public class DudeMyCar extends Activity {

    EditText angleInput, peekHeightInput, wallHeightInput, dudeNotesBox;
    TextView calculateCarResult;
    public static final String PERFS = "save";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dude_my_car);

        angleInput =            (EditText) findViewById(R.id.angleInput);
        peekHeightInput =       (EditText) findViewById(R.id.peekHeightInput);
        wallHeightInput =       (EditText) findViewById(R.id.wallHeightInput);
        dudeNotesBox     =      (EditText) findViewById(R.id.dudeNotesBox);
        calculateCarResult =    (TextView) findViewById(R.id.calculateCarResult);

        SharedPreferences load = getSharedPreferences(PERFS, 0);
        dudeNotesBox.setText(load.getString("dudeNotes", "nope"));

    }


    public void calculateCarButtonClick (View view)
    {
        if( !angleInput.getText().toString().equals("") &&  !peekHeightInput.getText().toString().equals("") && !wallHeightInput.getText().toString().equals(""))
        {
            calculateCarResult.setText("D= " + findDistance(Double.parseDouble(angleInput.getText().toString()),
                                                                Double.parseDouble(peekHeightInput.getText().toString()),
                                                                Double.parseDouble(wallHeightInput.getText().toString())));
        }
        else calculateCarResult.setText("put in values");
    }
    public static double findDistance(double angle, double peekHeight, double wallHeight)
    {
        double acceleration=32.17*Math.sin(angle);
        double horizontalAcceleration=acceleration*Math.cos(angle);
        double verticalAcceleration=acceleration*Math.sin(angle);
        double time=Math.sqrt((2*peekHeight)/verticalAcceleration);
        double horizontalVelocity=horizontalAcceleration*time;
        double verticalVelocity=verticalAcceleration*time;
        double timeFreefall=(-1 * verticalVelocity + Math.sqrt(verticalVelocity*verticalVelocity-4*(.5*32.17)*-1*wallHeight))/(32.17);
        double distance=horizontalAcceleration*timeFreefall;
        return Math.round(distance*100.0)/100.0;
    }

    public void dudeNotesSaveClick (View view)
    {
        String message = dudeNotesBox.getText().toString();
        SharedPreferences saveDudeNotes = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveDudeNotes.edit();
        editor.putString("dudeNotes", message);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dude_my_car, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
