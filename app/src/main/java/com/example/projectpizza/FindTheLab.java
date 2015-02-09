package com.example.projectpizza;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class FindTheLab extends Activity {

    TextView textLat, textLong, currentLocationTitle, accuracyText, locationsTitle, notesTitle, fiTitle;
    EditText latA, latB, latC, longA, longB, longC, notesBox;
    double pLatRounded, pLongRounded;
    Location currentLocation;

    public static final String PERFS = "save";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_the_lab);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        textLat =               (TextView)findViewById(R.id.textLat);
        textLong =              (TextView)findViewById(R.id.textLong);
        currentLocationTitle =  (TextView)findViewById(R.id.currentLocationTitle);
        accuracyText =          (TextView)findViewById(R.id.accuracyText);
        locationsTitle =        (TextView)findViewById(R.id.locationsTitle);
        notesTitle =            (TextView)findViewById(R.id.notesTitle);
        fiTitle =               (TextView)findViewById(R.id.findIntersectionTitle);

        currentLocationTitle.   setTypeface(robotoThin);
        locationsTitle.         setTypeface(robotoThin);
        notesTitle.             setTypeface(robotoThin);
        fiTitle.                setTypeface(robotoThin);
        textLat.                setTypeface(robotoLight);
        textLong.               setTypeface(robotoLight);
        accuracyText.           setTypeface(robotoLight);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

        notesBox = (EditText) findViewById(R.id.orientationNotesBox);
        SharedPreferences load = getSharedPreferences(PERFS, 0);
        notesBox.setText(load.getString("labNotes", "nope"));

        loadLocations();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_the_lab, menu);
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

    public void loadLocations()
    {
        SharedPreferences locations = getSharedPreferences(PERFS, 0);
        latA =  (EditText)findViewById(R.id.latTextA);
        latB =  (EditText)findViewById(R.id.latTextB);
        latC =  (EditText)findViewById(R.id.latTextC);
        longA = (EditText)findViewById(R.id.longTextA);
        longB = (EditText)findViewById(R.id.longTextB);
        longC = (EditText)findViewById(R.id.longTextC);

        latA.setText("" + locations.getString("latA", ""));
        latB.setText("" + locations.getString("latB", ""));
        latC.setText("" + locations.getString("latC", ""));
        longA.setText("" + locations.getString("longA", ""));
        longB.setText("" + locations.getString("longB", ""));
        longC.setText("" + locations.getString("longC", ""));

        latA.setEnabled(false);     latA.setFocusable(false);
        latB.setEnabled(false);     latB.setFocusable(false);
        latC.setEnabled(false);     latC.setFocusable(false);
        longA.setEnabled(false);    longA.setFocusable(false);
        longB.setEnabled(false);    longB.setFocusable(false);
        longC.setEnabled(false);    longC.setFocusable(false);

    }

    /*public void setCurrent(View view)
    {
        String lat = textLat.getText().toString();
        String lon = textLong.getText().toString();
        //int angle = Integer.parseInt(inclinationText.getText().toString());
        SharedPreferences locations = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = locations.edit();
        switch (view.getId())
        {
            case R.id.currentA:
                editor.putString("latA", lat);
                editor.putString("longA", lon);
                latA.setText("" + lat);
                longA.setText("" + lon);
                break;
            case R.id.currentB:
                editor.putString("latB", lat);
                editor.putString("longB", lon);
                latB.setText("" + lat);
                longB.setText("" + lon);
                break;
            case R.id.currentC:
                editor.putString("latC", lat);
                editor.putString("longC", lon);
                latC.setText("" + lat);
                longC.setText("" + lon);
                break;
        }
        editor.apply();
    }*/

    public void editLocation(View view)
    {
        Button button;
        SharedPreferences save = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = save.edit();
        switch (view.getId())
        {
            case R.id.editA:
                button = (Button) findViewById(R.id.editA);
                if(button.getText().toString().equals("Save"))
                {
                    button.setText("Edit");
                    latA.setEnabled(false);     latA.setFocusable(false);
                    longA.setEnabled(false);    longA.setFocusable(false);

                    editor.putString("latA", latA.getText().toString());
                    editor.putString("longA", longA.getText().toString());
                    editor.apply();
                }
                else
                {
                    button.setText("Save");
                    latA.setFocusableInTouchMode(true);  latA.setEnabled(true);   latA.setFocusable(true);
                    longA.setFocusableInTouchMode(true); longA.setEnabled(true);  longA.setFocusable(true);
                }
                break;

            case R.id.editB:
                button = (Button) findViewById(R.id.editB);
                if(button.getText().toString().equals("Save"))
                {
                    button.setText("Edit");
                    latB.setEnabled(false);     latB.setFocusable(false);
                    longB.setEnabled(false);    longB.setFocusable(false);

                    editor.putString("latB", latB.getText().toString());
                    editor.putString("longB", longB.getText().toString());
                    editor.apply();
                }
                else
                {
                    button.setText("Save");
                    latB.setFocusableInTouchMode(true);  latB.setEnabled(true);   latB.setFocusable(true);
                    longB.setFocusableInTouchMode(true); longB.setEnabled(true);  longB.setFocusable(true);
                }
                break;

            case R.id.editC:
                button = (Button) findViewById(R.id.editC);
                if(button.getText().toString().equals("Save"))
                {
                    button.setText("Edit");
                    latC.setEnabled(false);     latC.setFocusable(false);
                    longC.setEnabled(false);    longC.setFocusable(false);

                    editor.putString("latC", latC.getText().toString());
                    editor.putString("longC", longC.getText().toString());
                    editor.apply();
                }
                else
                {
                    button.setText("Save");
                    latC.setFocusableInTouchMode(true);  latC.setEnabled(true);   latC.setFocusable(true);
                    longC.setFocusableInTouchMode(true); longC.setEnabled(true);  longC.setFocusable(true);
                }
                break;
        }
    }

    public void saveNotes(View view)
    {
        String message = notesBox.getText().toString();
        SharedPreferences save = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = save.edit();
        editor.putString("labNotes", message);
        editor.commit();
    }


    class mylocationlistener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            if(location != null)
            {
                double pLat = location.getLatitude();
                double pLong = location.getLongitude();

                pLatRounded = Math.round(pLat * 1000000) /1000000.0;
                pLongRounded = Math.round(pLong * 1000000) /1000000.0;

                currentLocation.set(location);

                textLat.setText(Double.toString(pLatRounded));
                textLong.setText(Double.toString(pLongRounded));

                accuracyText.setText("" + (int)location.getAccuracy() + " meters");
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status,
                                    Bundle extras) {
            // TODO Auto-generated method stub

        }


    }
}
