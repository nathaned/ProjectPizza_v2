package com.example.projectpizza;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projectpizza.tools.DudeMyCar;
import com.example.projectpizza.tools.SkidMark;
import com.example.projectpizza.tools.StraightArrow;
import com.example.projectpizza.tools.TwoPlaces;

public class MainActivity extends Activity implements SensorEventListener {

	TextView textLat, textLong, calculatedDistance, currentLocationTitle, calculateDistanceText, accuracyText, toolsText, incidentsText, currentBearing;
	EditText calcLat, calcLong;
	double pLatRounded, pLongRounded;
	String direction;
	double lat1, lng1,lat2, lng2;
	Location calcTo, currentLocation;
    public static final String PERFS = "save";


    @Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        Typeface robotoThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        textLat =               (TextView)findViewById(R.id.textLat);
        textLong =              (TextView)findViewById(R.id.textLong);
        currentLocationTitle =  (TextView)findViewById(R.id.currentLocationTitle);
        calculateDistanceText = (TextView)findViewById(R.id.calculateDistanceText);
        accuracyText =          (TextView)findViewById(R.id.accuracyText);
        calcLat =               (EditText)findViewById(R.id.calcLat1);
        calcLong =              (EditText)findViewById(R.id.calcLong1);
        calculatedDistance =    (TextView)findViewById(R.id.calculatedDistance);
        toolsText =             (TextView)findViewById(R.id.toolsText);
        //incidentsText =         (TextView)findViewById(R.id.incidentsText);
        currentBearing =        (TextView)findViewById(R.id.currentBearing);

        currentLocationTitle.   setTypeface(robotoThin);
        calculateDistanceText.  setTypeface(robotoThin);
        toolsText.              setTypeface(robotoThin);
        //incidentsText.          setTypeface(robotoThin);
        textLat.                setTypeface(robotoLight);
        textLong.               setTypeface(robotoLight);
        accuracyText.           setTypeface(robotoLight);
        currentBearing.         setTypeface(robotoLight);

        calcTo = new Location("Inputted Location");
		currentLocation = new Location("Current Location");

        SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);


        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		LocationListener ll = new mylocationlistener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

        lockTeamNumber();

	}
	
	public static double distFrom(double lat1, double lng1, double lat2, double lng2)
	{
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;
	    dist *=5280;
	    return dist;
	 }
	
	public void timerButtonClicked (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, com.example.projectpizza.TimerActivity.class);
        startActivityForResult(startNewActivityOpen, 0);
	}

	public void abcCirclesClicked (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, FindTheLab.class);
        startActivityForResult(startNewActivityOpen, 0);
	}

    public void lightSensorButtonClicked (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, com.example.projectpizza.LightSensorActivity.class);
        startActivityForResult(startNewActivityOpen, 0);
    }

    public void cameraButtonClicked (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, com.example.projectpizza.DistanceFinder.class);
        startActivityForResult(startNewActivityOpen, 0);
    }

    public void militaryButtonClicked (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, com.example.projectpizza.Encryption.class);
        startActivityForResult(startNewActivityOpen, 0);
    }

    public void launchSoundRecorder (View view)
    {
        Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, SoundRecorder.class);
        startActivityForResult(startNewActivityOpen, 0);
    }

    public void launchGoogleGoggles (View view)
    {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setComponent(new ComponentName("com.google.android.apps.unveil", "com.google.android.apps.unveil.CaptureActivity"));
        //i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);
    }

    public void hollaClicked (View view)
    {
        /*Intent i = new Intent(Intent.ACTION_MAIN);
        i.setComponent(new ComponentName("com.p1.chompsms", "com.p1.chompsms.activities.QuickCompose"));
        //i.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(i);*/

        //Button hollaButton = (Button) findViewById(R.id.hollaButton);

        String contactName = "BRATA";
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", getPhoneNumber(contactName));
        //smsIntent.putExtra("sms_body","YoloSwagTeam");
        startActivity(smsIntent);

    }


    public String getPhoneNumber(String name) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                + " like'%" + name + "%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER };
        Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if (ret == null)
            ret = "Unsaved";
        return ret;
    }

    public void currentButtonClicked (View view)
    {
        switch (view.getId())
        {
            case R.id.currentLatitudeButton:
                calcLat.setText(textLat.getText());
                break;

            case R.id.currentLongitudeButton:
                calcLong.setText(textLong.getText());
                break;
        }
    }

	public void orientationButtonClicked (View view)
	{
		Intent startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, com.example.projectpizza.OrientationActivity.class);
		startActivityForResult(startNewActivityOpen, 0);
	}
	
	public void calculateButtonClicked (View view)
	{

		if (calcLat.length() >= 3 && calcLong.length() >= 3  && !(textLat.getText().equals("Loading...")))
		{
			calcTo.setLatitude(Double.parseDouble(calcLat.getText().toString()));
			calcTo.setLongitude(Double.parseDouble(calcLong.getText().toString()));
			double  realDirection = Math.round((double)(calcTo.bearingTo(currentLocation))*100)/100.0;
			
			String realWordDirection;
            double realDirectionPositive = realDirection;
            if(realDirection>90) realDirectionPositive = 450 - realDirection;
            else realDirectionPositive = 90 - realDirection;
            double fixedDirection = Math.round((realDirectionPositive) * 10.0)/10.0;
			if (fixedDirection>90.0) realWordDirection="Northwest";
			else if (fixedDirection>0.0) realWordDirection="Northeast";
			else if (fixedDirection<-90.0) realWordDirection="Southwest";
			else if (fixedDirection<0.0) realWordDirection="Southeast";
			else realWordDirection="?";
			realDirection = Math.round(realDirection*100)/100.0;
			
			
			double distanceInMeters = Math.round(calcTo.distanceTo(currentLocation)*100)/100.0;
			
			//Old way that's overly complicated
			double calcLatNumber = Double.parseDouble(calcLat.getText().toString());
			double calcLongNumber = Double.parseDouble(calcLong.getText().toString());
			double calcLatDifference = (Math.round(calcLatNumber * 1000000) / 1000000.0) - pLatRounded;
			double calcLongDifference = (Math.round(calcLongNumber * 1000000) / 1000000.0) - pLongRounded;
			calcLatDifference = Math.round(calcLatDifference * 1000000) / 1000000.0;
			calcLongDifference = Math.round(calcLongDifference * 1000000) / 1000000.0;
			
			/*if (calcLatDifference > 0)
			{
				if (calcLongDifference > 0)	direction = "Northeast";
				else if (calcLongDifference < 0) direction = "Northwest";
				else  direction = "North";
			}
			else if (calcLatDifference < 0)
			{
				if (calcLongDifference > 0)	direction = "Southeast";
				else if (calcLongDifference < 0) direction = "Southwest";
				else direction = "South";
			}
			else if (calcLatDifference == 0)
			{
				if (calcLongDifference > 0) direction = "East";
				else if (calcLongDifference < 0) direction = "West";
				else direction = "?";
			} */
			double distanceInFeet = Math.round(distFrom(lat1=calcLatNumber, lng1=calcLongNumber, lat2=pLatRounded, lng2=pLongRounded)*10)/10.0;
			//float [] storeDistance= new float[3];
			//double distanceInMeters = location.distanceBetween(calcLatNumber, calcLongNumber, pLatRounded, pLongRounded, storeDistance);
			
			calculatedDistance.setText( distanceInFeet      + " feet    \n" +
										distanceInMeters    + " meters  \n"  +
										fixedDirection      + " degrees (" +  realWordDirection +  ")"  );
		} 
		
		
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
		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mainActionSettings:
                Context context = getApplicationContext();
                CharSequence text = "Settings? Really?";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void toolLaunch (View view)
    {
        Intent startNewActivityOpen;
        switch (view.getId())
        {
            case R.id.dudeMyCarButton:
                startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, DudeMyCar.class);
                startActivityForResult(startNewActivityOpen, 0);
                break;

            case R.id.straightAsButton:
                startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, StraightArrow.class);
                startActivityForResult(startNewActivityOpen, 0);
                break;

            case R.id.skidMarkButton:
                startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, SkidMark.class);
                startActivityForResult(startNewActivityOpen, 0);
                break;

            case R.id.twoPlacesButton:
                startNewActivityOpen = new Intent(com.example.projectpizza.MainActivity.this, TwoPlaces.class);
                startActivityForResult(startNewActivityOpen, 0);
                break;
        }
    }*/

    /*public void imSoLonely (View view)
    {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "3217450482");
        //smsIntent.putExtra("sms_body","YoloSwagTeam");
        startActivity(smsIntent);
    }*/

    public void quickTextDialog (View view)
    {
        final String [] items = {"GET ASSIGNMENT", "AT ASSIGNMENT, MARKER = ", "GET QUESTION", "ANSWER = "};
        final int []  messageNumber = new int[1];
        messageNumber[0]=16;

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set the title of the Alert Dialog
        alertDialogBuilder.setTitle("Here Are Some Preset Texts:");

        alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                messageNumber[0] = which;
            }
        });

        // set dialog message
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                if (messageNumber[0] != 16)
                                {
                                    String contactName = "BRATA";
                                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                                    smsIntent.setType("vnd.android-dir/mms-sms");
                                    smsIntent.putExtra("address", getPhoneNumber(contactName));
                                    smsIntent.putExtra("sms_body", items[messageNumber[0]]);
                                    startActivity(smsIntent);
                                }
                                else
                                {
                                    Context context = getApplicationContext();
                                   CharSequence text = "You didn't select a Quick Text";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


                    AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();
    }

    public void setTeamButtonClicked(View view)
    {
        EditText    teamBox = (EditText) findViewById(R.id.teamText);
        Button      setTeamButton = (Button) findViewById(R.id.setTeamButton);
        if(setTeamButton.getText().equals("Save"))
        {
            setTeamButton.setText("Edit");
            teamBox.setEnabled(false);
            teamBox.setFocusable(false);

            int teamNum = Integer.parseInt(teamBox.getText().toString());
            SharedPreferences saveTeamNum = getSharedPreferences(PERFS, 0);
            SharedPreferences.Editor editor = saveTeamNum.edit();
            editor.putInt("teamNum", teamNum);
            editor.commit();

        }
        else
        {
            setTeamButton.setText("Save");
            teamBox.setFocusableInTouchMode(true);
            teamBox.setEnabled(true);
            teamBox.setFocusable(true);
        }
    }

    public void lockTeamNumber()
    {
        EditText    teamBox = (EditText) findViewById(R.id.teamText);
        SharedPreferences getTeamNum = getSharedPreferences(PERFS, 0);
        teamBox.setText(""+getTeamNum.getInt("teamNum", 12345));
        teamBox.setEnabled(false);
        teamBox.setFocusable(false);
    }




    public void onAccuracyChanged(Sensor sensor, int accuracy) {    }

    // Arrays of sensor values for each sensor
    float[] mGravity;
    float[] mGeomagnetic;
    Float reading_in_radians;
    //double x;
    //double y;

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


                //inclinationValue = Math.toDegrees(Math.atan2(mGravity[1],mGravity[0]));

                // Insert code here
                // update UI with bearing
                float reading_in_degrees =(float) Math.toDegrees((double)reading_in_radians);
                int bearingLike = (int) reading_in_degrees;
                if(bearingLike<0)
                {
                    int temp = bearingLike;
                    bearingLike = 180 + (180 + temp);
                }
                currentBearing.setText("Current Bearing: " + bearingLike);
                //inclinationText.setText("" + (int)inclinationValue);
                //bearing.setText("" + reading_in_radians);
            }
        }
    }





}
