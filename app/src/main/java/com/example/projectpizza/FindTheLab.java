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

    TextView textLat, textLong, currentLocationTitle, accuracyText, locationsTitle, notesTitle, fiTitle,
            pointAText, pointBText, pointCText, intersectionSolution;
    EditText latA, latB, latC, longA, longB, longC, notesBox;
    double pLatRounded, pLongRounded;
    //Location currentLocation;

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
        pointAText =            (TextView)findViewById(R.id.pointA);
        pointBText =            (TextView)findViewById(R.id.pointB);
        pointCText =            (TextView)findViewById(R.id.pointC);
        intersectionSolution =  (TextView)findViewById(R.id.intersectionSolution);

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

        notesBox = (EditText) findViewById(R.id.notesBox);
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

        setPoints();

    }


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
        editor.apply();
    }

    public void solveIntersection(View view)
    {
        EditText radiusA = (EditText)findViewById(R.id.radiusA);
        EditText radiusB = (EditText)findViewById(R.id.radiusB);
        EditText radiusC = (EditText)findViewById(R.id.radiusC);

        if ( pointAText.getText().toString().equals("") || pointBText.getText().toString().equals("") || pointCText.getText().toString().equals("") ||
                radiusA.getText().toString().equals("") || radiusB.getText().toString().equals("") || radiusC.getText().toString().equals(""))
        {
            intersectionSolution.setText("complete all fields");
            return;
        }
        setPoints();
        Vector2 vecA = new Vector2(0, 0);
        double [] temp = getPoint(1);
        Vector2 vecB = new Vector2(temp[0], temp[1]);
        temp = getPoint(2);
        Vector2 vecC = new Vector2(temp[0], temp[1]);

        Circle circleA = new Circle(vecA, Double.parseDouble(radiusA.getText().toString()));
        Circle circleB = new Circle(vecB, Double.parseDouble(radiusB.getText().toString()));
        Circle circleC = new Circle(vecC, Double.parseDouble(radiusC.getText().toString()));

        CircleCircleIntersection intersectAB = new CircleCircleIntersection(circleA, circleB);
        CircleCircleIntersection intersectAC = new CircleCircleIntersection(circleA, circleC);
        CircleCircleIntersection intersectBC = new CircleCircleIntersection(circleB, circleC);

        String text = "";
        text+= "AB: " + intersectionsToString(intersectAB);
        text+= "\nAC: " + intersectionsToString(intersectAC);
        text+= "\nBC: " + intersectionsToString(intersectBC);

        intersectionSolution.setText(text);
    }

    public String intersectionsToString(CircleCircleIntersection intersection)
    {
        int count = intersection.type.getIntersectionPointCount();
        if(count==-1) return "same circle";
        if(count==0) return "no intersections";

        Vector2[] points = intersection.getIntersectionPoints();
        double x = Math.round(points[0].getX() * 10.0)/10.0;
        double y = Math.round(points[0].getY() * 10.0)/10.0;
        String toReturn = ("(" + x + ", " + y + ")");
        if(count==2)
        {
            double x2 = Math.round(points[1].getX() * 10.0)/10.0;
            double y2 = Math.round(points[1].getY() * 10.0)/10.0;
            toReturn += " and (" + x2 + ", " + y2 + ")";
        }
        return toReturn;
    }

    public void setPoints()
    {
        if( !(latA.getText().toString().equals("")) && !(longA.getText().toString().equals("")) )
        {
            Location locA = new Location("A");
            locA.setLatitude(Double.parseDouble(latA.getText().toString()));
            locA.setLongitude(Double.parseDouble(longA.getText().toString()));
            pointAText.setText("(0, 0)");

            if(!(latB.getText().toString().equals("")) && !(longB.getText().toString().equals("")))
            {
                String bean = "(";

                Location locB = new Location("B");
                locB.setLatitude(Double.parseDouble(latB.getText().toString()));
                locB.setLongitude(Double.parseDouble(longB.getText().toString()));

                Location temp = new Location("temp");
                /*temp.setLongitude(locA.getLongitude());
                temp.setLatitude(locB.getLatitude());
                if(locA.getLongitude()>locB.getLongitude()) bean += "-";*/
                temp.setLatitude(locA.getLatitude());
                temp.setLongitude(locB.getLongitude());
                if (locA.getLongitude()>locB.getLongitude()) bean += "-";
                bean += "" + (Math.round(locA.distanceTo(temp) * 10.0) /10.0) + ", ";

                /*temp.setLatitude(locA.getLatitude());
                temp.setLongitude(locB.getLongitude());
                if(locA.getLatitude()>locB.getLatitude()) bean += "-";*/
                temp.setLongitude(locA.getLongitude());
                temp.setLatitude(locB.getLatitude());
                if (locA.getLatitude()>locB.getLatitude()) bean += "-";
                bean += "" + (Math.round(locA.distanceTo(temp) * 10.0) /10.0) + ")";
                pointBText.setText(bean);

            }
            if(!(latC.getText().toString().equals("")) && !(longC.getText().toString().equals("")))
            {
                String bean = "(";

                Location locC = new Location("C");
                locC.setLatitude(Double.parseDouble(latC.getText().toString()));
                locC.setLongitude(Double.parseDouble(longC.getText().toString()));

                Location temp = new Location ("temp");
                /*temp.setLongitude(locA.getLongitude());
                temp.setLatitude(locC.getLatitude());
                if(locA.getLongitude()>locC.getLongitude()) bean += "-";*/
                temp.setLatitude(locA.getLatitude());
                temp.setLongitude(locC.getLongitude());
                if (locA.getLongitude()>locC.getLongitude()) bean += "-";
                bean += "" + (Math.round(locA.distanceTo(temp) * 10.0 ) /10.0) + ", ";

                /*temp.setLatitude(locA.getLatitude());
                temp.setLongitude(locC.getLongitude());
                if(locA.getLatitude()>locC.getLatitude()) bean += "-";*/
                temp.setLongitude(locA.getLongitude());
                temp.setLatitude(locC.getLatitude());
                if (locA.getLatitude()>locC.getLatitude()) bean += "-";
                bean += "" + (Math.round(locA.distanceTo(temp) * 10.0) /10.0) + ")";

                pointCText.setText(bean);
            }


        }

    }

    public double[] getPoint(int point)
    {
        double[] toReturn = new double[2];
        if (point == 0) //point A
        {
            toReturn[0] = 0;
            toReturn[1] = 0;
        }
        else if (point == 1) //point B
        {
            toReturn[0] = Double.parseDouble(pointBText.getText().toString().substring(1, pointBText.getText().toString().indexOf(",")));
            toReturn[1] = Double.parseDouble(pointBText.getText().toString().substring(pointBText.getText().toString().indexOf(" ") + 1, pointBText.getText().toString().length()-1));
        }
        else if (point == 2) //point C
        {
            toReturn[0] = Double.parseDouble(pointCText.getText().toString().substring(1, pointCText.getText().toString().indexOf(",")));
            toReturn[1] = Double.parseDouble(pointCText.getText().toString().substring(pointCText.getText().toString().indexOf(" ") + 1, pointCText.getText().toString().length()-1));
        }
        return toReturn;
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

                //currentLocation.set(location);

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
