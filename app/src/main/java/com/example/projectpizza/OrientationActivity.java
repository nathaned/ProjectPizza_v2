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

public class OrientationActivity extends Activity implements SensorEventListener{


    TextView bearing;
    TextView inclinationText;
    Float reading_in_radians;
    SensorManager sensorManager = null;
    double inclinationValue;
    EditText orientationNotesBox;

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



}
