package com.example.projectpizza;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class OrientationActivity extends Activity implements SensorEventListener{


    TextView bearing;
    TextView inclinationText;
    Float reading_in_radians;
    SensorManager sensorManager = null;
    double inclinationValue;
    EditText orientationNotesBox;
    TextView angleText1, angleText2, angleText3, angleText4, angleText5;

    public static final String PERFS = "save";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        // there are a few sensors on the phone.  This guy manages them all.
        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        bearing = (TextView)findViewById(R.id.bearing);
        inclinationText = (TextView)findViewById(R.id.incination);
        // You have set the event listener up, now just need to register this with the
        // sensor manager along with the sensor wanted.
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);

        orientationNotesBox = (EditText) findViewById(R.id.orientationNotesBox);
        SharedPreferences load = getSharedPreferences(PERFS, 0);
        orientationNotesBox.setText(load.getString("orientationNotes", "nope"));

        loadAngles();
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // do nothing here.
    }

    // Arrays of sensor values for each sensor
    float[] mGravity;
    float[] mGeomagnetic;
    double x;
    double y;

    public void onSensorChanged(SensorEvent event) {

        // Save accelerometer sensor update
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            mGravity = event.values;
            //double x = event.values[0];
            //double y = event.values[1];
        }

        // Save magnetic sensor update
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            mGeomagnetic = event.values;
        }

        // If data is available for both sensors
        // This logic uses Android rotation and orientation functions
        // to derive the rotation in radians relative to the north pole
        if (mGravity != null && mGeomagnetic != null)
        {
            float rotation[] = new float[9];
            float inclination[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(rotation, inclination, mGravity, mGeomagnetic);
            if (success)
            {
                float orientation[] = new float[3];
                SensorManager.getOrientation(rotation, orientation);
                reading_in_radians = orientation[0];


                inclinationValue = Math.toDegrees(Math.atan2(mGravity[1],mGravity[0]));

                // Insert code here
                // update UI with bearing
                float reading_in_degrees =(float) Math.toDegrees((double)reading_in_radians);
                bearing.setText("" + (int)reading_in_degrees);
                inclinationText.setText("" + (int)inclinationValue);
                //bearing.setText("" + reading_in_radians);
            }
        }
    }


    public void orientationNotesSaveClick (View view)
    {
        String message = orientationNotesBox.getText().toString();
        SharedPreferences saveOrientationNotes = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveOrientationNotes.edit();
        editor.putString("orientationNotes", message);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.orientation, menu);
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

    public void loadAngles()
    {
        SharedPreferences angles = getSharedPreferences(PERFS, 0);
        angleText1 = (TextView) findViewById(R.id.angle1Text);
        angleText2 = (TextView) findViewById(R.id.angle2Text);
        angleText3 = (TextView) findViewById(R.id.angle3Text);
        angleText4 = (TextView) findViewById(R.id.angle4Text);
        angleText5 = (TextView) findViewById(R.id.angle5Text);

        angleText1.setText(""+angles.getInt("angle1", 0));
        angleText2.setText(""+angles.getInt("angle2", 0));
        angleText3.setText(""+angles.getInt("angle3", 0));
        angleText4.setText(""+angles.getInt("angle4", 0));
        angleText5.setText(""+angles.getInt("angle5", 0));
    }

    public void angleClick(View view)
    {
        int angle = Integer.parseInt(inclinationText.getText().toString());
        SharedPreferences saveAngles = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveAngles.edit();
        switch (view.getId())
        {
            case R.id.updateButton1:
                editor.putInt("angle1", angle);
                angleText1.setText(""+angle);
                break;
            case R.id.updateButton2:
                editor.putInt("angle2", angle);
                angleText2.setText(""+angle);
                break;
            case R.id.updateButton3:
                editor.putInt("angle3", angle);
                angleText3.setText(""+angle);
                break;
            case R.id.updateButton4:
                editor.putInt("angle4", angle);
                angleText4.setText(""+angle);
                break;
            case R.id.updateButton5:
                editor.putInt("angle5", angle);
                angleText5.setText(""+angle);
                break;
        }
        editor.commit();
    }

    public void crackTheSafe(View view) {
        EditText editText = (EditText) findViewById(R.id.keyText);
        String key = editText.getText().toString();
        char last = key.charAt(3);
        String file;
        if (Character.isUpperCase(last)) file = "upper" + last + ".txt";
        else file = "lower" + last + ".txt";

        String text = giveRow(file, key);

        TextView codeOut = (TextView) findViewById(R.id.codeOutText);
        codeOut.setText(text);
    }



    private String giveRow(String fileName, String key)
    {
        BufferedReader in = null;
        try
        {
            in = new BufferedReader(new InputStreamReader(getAssets().open("keys/" + fileName)));
            String line;
            //final StringBuilder buffer = new StringBuilder();
            while ((line = in.readLine()) != null)
            {
                //buffer.append(line).append(System.getProperty("line.separator"));
                if(line.substring(0,4).equals(key)) return line;
            }
            //return buffer.toString();
        }
        catch (final IOException e)
        {
            return "";
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                // ignore //
            }
        }
        return "";
    }



}
