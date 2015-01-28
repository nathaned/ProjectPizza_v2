package com.example.projectpizza;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LightSensorActivity extends Activity implements SensorEventListener{

    //ImageView needle;
    ProgressBar lightBar;
    TextView lightText;
    Button buttonStartStop;
    Button buttonSubmit;
    EditText editSubmission;
    private SensorManager sm;
    private Sensor mLight;
    double currentLux;
    boolean sensorStarted = false;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        buttonStartStop = (Button)findViewById(R.id.buttonStartStop);
        lightBar = (ProgressBar) findViewById(R.id.ProgressLight);
        lightText = (TextView) findViewById(R.id.TextLightReading);

    }

    public void startButtonClicked (View view)
    {
        if (sensorStarted)
        {
            sm.unregisterListener(LightSensorActivity.this);
            buttonStartStop.setText("Start");
            sensorStarted = false;
        }
        else
        {
            sm.registerListener(LightSensorActivity.this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
            buttonStartStop.setText("Stop");
            sensorStarted = true;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if( event.sensor.getType() == Sensor.TYPE_LIGHT)
        {
            currentLux =  (int)event.values[0];
            lightText.setText("" + currentLux);


            // The light intensity value which varies exponentially is transformed
            // into a simpler gauge value which varies linearly from 0-100
            float gauge_value = (float) (Math.log10((double)currentLux) * 25.0f);
            if(gauge_value > 100)
                gauge_value = 100;
            if(gauge_value < 0)
                gauge_value = 0;

            // Sets progress bar to reflect light intensity
            lightBar.setProgress((int) gauge_value);

        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.light_sensor, menu);
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

    @Override
    protected void onDestroy() {
        // Stop sensor from updating in the future and unregister for light sensor updates
        sensorStarted = false;
        sm.unregisterListener(LightSensorActivity.this);
        super.onDestroy();
    }
    /**
     * This function is used to update the layout elements with a new intensity value
     */
    private final Runnable update = new Runnable() {
        public void run() {
            if(sensorStarted)
            {
                // The light intensity value which varies exponentially is transformed
                // into a simpler gauge value which varies linearly from 0-100
                float gauge_value = (float) (Math.log10((double)currentLux) * 25.0f);
                if(gauge_value > 100)
                    gauge_value = 100;
                if(gauge_value < 0)
                    gauge_value = 0;

                // The gauge value is used to determine the angle of the needle image
                // which is tuned to line up and rotate correctly with the gauge image
                // A simple way to rotate an image to to use the RotationAnimation with 0 delay
                float gauge_angle = gauge_value / 100.0f * (96)  -48;
                RotateAnimation anim = new RotateAnimation(gauge_angle, gauge_angle,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,1.09f);
                // Set options to adjust the animation
                anim.setDuration(0);
                anim.setFillEnabled(true);
                anim.setFillBefore(true);
                anim.setFillAfter(true);
                //needle.startAnimation(anim);

                // Also set progress bar to reflect light intensity
                lightBar.setProgress((int) gauge_value);

                // Display the actual light intensity value in a text view
                lightText.setText(String.valueOf((int)currentLux));

                // Post this function to to the thread to update the UI again after 80 milliseconds
                handler.postDelayed(update, 80);
            }
        }
    };

}
