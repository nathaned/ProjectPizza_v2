package com.example.projectpizza.tools;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectpizza.R;

public class SkidMark extends Activity {

    TextView showInfoText, energyResult, workResult, distanceResult, velocityResult;
    String docInfoText;
    Button showInfoButton;
    boolean infoShowing=false;
    EditText  energyMInput, energyVInput, workMinput, workWinput, workUimput, distanceVinput, distanceUinput, velocityUinput, velocityDinput, skidNotes;

    public static final String PERFS = "save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skid_mark);


        showInfoButton =    (Button)    findViewById(R.id.showInfoSkidButton);
        showInfoText =      (TextView)  findViewById(R.id.showInfoSkidText);
        energyResult =      (TextView)  findViewById(R.id.energyResult);
        distanceResult =    (TextView)  findViewById(R.id.distanceResult);
        workResult =        (TextView)  findViewById(R.id.workResult);
        velocityResult =    (TextView)  findViewById(R.id.velocityResult);
        energyMInput =      (EditText)  findViewById(R.id.energyMInput);
        energyVInput =      (EditText)  findViewById(R.id.energyVInput);
        workMinput =        (EditText)  findViewById(R.id.workMInput);
        workUimput=         (EditText)  findViewById(R.id.workUInput);
        workWinput =        (EditText)  findViewById(R.id.workWInput);
        distanceUinput =    (EditText)  findViewById(R.id.distanceUInput);
        distanceVinput =    (EditText)  findViewById(R.id.distanceVInput);
        velocityDinput =    (EditText)  findViewById(R.id.velocityDInput);
        velocityUinput =    (EditText)  findViewById(R.id.velocityUInput);
        skidNotes =         (EditText)  findViewById(R.id.skidNotesBox);

        SharedPreferences load = getSharedPreferences(PERFS, 0);
        skidNotes.setText(load.getString("skidNotes", "nope"));

        setdocInfoText();
    }


    public void showInfoSkidClick (View view)
    {
        if (!infoShowing)
        {
            showInfoText.setText(docInfoText);
            showInfoButton.setText("Hide info");
            infoShowing=true;
        }
        else
        {
            showInfoText.setText("");
            showInfoButton.setText("Show info");
            infoShowing=false;
        }
    }

    public void skidNotesSaveClick (View view)
    {
        String message = skidNotes.getText().toString();
        SharedPreferences saveSkidNotes = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveSkidNotes.edit();
        editor.putString("skidNotes", message);
        editor.commit();
    }

    public void solveEnergyClick (View view)
    {
        if( !energyMInput.getText().toString().equals("") &&  !energyVInput.getText().toString().equals(""))
        {
            double result = 0.5 * Double.parseDouble(energyMInput.getText().toString()) * (Double.parseDouble(energyVInput.getText().toString()) * Double.parseDouble(energyVInput.getText().toString())) ;
            energyResult.setText("E= " + Math.round(result*100.0)/100.0);
        }
        else energyResult.setText("enter values silly");
    }

    public void solveWorkClick (View view)
    {
        if( !workUimput.getText().toString().equals("") &&  !workWinput.getText().toString().equals("")&&  !workMinput.getText().toString().equals(""))
        {
            double result = (Double.parseDouble(workWinput.getText().toString()) ) / ( (Double.parseDouble(workUimput.getText().toString()) * Double.parseDouble(workMinput.getText().toString())) * 9.81) ;
            workResult.setText("d= " + Math.round(result*100.0)/100.0);
        }
        else workResult.setText("enter values silly");
    }

    public void solveDistanceClick (View view)
    {
        if( !distanceUinput.getText().toString().equals("") &&  !distanceVinput.getText().toString().equals(""))
        {
            double result = ((Double.parseDouble(distanceVinput.getText().toString())) * (Double.parseDouble(distanceVinput.getText().toString())))   / (2*Double.parseDouble(distanceUinput.getText().toString())*9.81) ;
            distanceResult.setText("d= " + Math.round(result*100.0)/100.0);
        }
        else distanceResult.setText("enter values silly");
    }

    public void solveVelocityClick (View view)
    {
        if( !velocityUinput.getText().toString().equals("") &&  !velocityDinput.getText().toString().equals(""))
        {
            double result = Math.sqrt(2*Double.parseDouble(velocityUinput.getText().toString())*9.81*Double.parseDouble(velocityDinput.getText().toString())) ;
            velocityResult.setText("v= " + Math.round(result*100.0)/100.0);
        }
        else velocityResult.setText("enter values silly");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.skid_mark, menu);
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

    public void setdocInfoText()
    {
        docInfoText= "5.2.1  The Scene \n" +
                "You arrive at the incident scene and find that a vehicle has struck a stationary object, causing " +
                "some  damage  to  the  object  and  vehicle.  The  vehicle  has  left  skid  marks,  indicating  that  the " +
                "driver (who was not seriously injured) applied full brakes in an attempt to stop before striking the " +
                "object. (See Figure 5-3 for an example scene layout.) \n" +
                "There  is  a  “speed  limit”  sign  nearby  indicating  the maximum  safe  driving  speed  for  vehicles " +
                "under all conditions. The road may be described by  your escort as dry, wet, or icy. There is a " +
                "QR Code on the vehicle to help you do your research.\n\n" +
                "5.2.2  Objective \n" +
                "You must determine if the driver of the vehicle was speeding prior to applying the full brakes. \n\n" +
                "5.2.3  Method \n" +
                "Skid  marks  are  often  used  by  real  law  enforcement  forensic  teams  to  determine  estimated \n" +
                "vehicle velocity. The concept depends on the ability of the vehicle’s tires to positively grip the \n" +
                "road surface without losing traction. A spinning tire (when accelerating) or skidding tire (when \n" +
                "decelerating) has lost traction, and the intended action (to accelerate or decelerate) is not being \n" +
                "accomplished efficiently. \n" +
                "Traction is expressed as the “coefficient of friction”, a unit-less value that describes the “grip” of\n" +
                "a  tire  against  the  road  surface.  The  value  is  typically  between  0.0  (no  traction)  and  1.0  (full \n" +
                "traction),  but  may  be  greater  than  1.0  for  “sticky” tires.  You  are  encouraged  to  do  some \n" +
                "independent research for yourself. \n" +
                "The  formulas  for  determining  the  “braking  distance” of  a  vehicle  may  be  found  at \n" +
                "http://en.wikipedia.org/wiki/Braking_distance, and repeated below. \n" +
                "Energy equation\n" +
                "The  theoretical  braking  distance  can  be  found  by  determining  the  work  required  to \n" +
                "dissipate the vehicle's kinetic energy. \n" +
                "The kinetic energy Eis given by the formula: \n\n" +
                "---    E = (1/2) m * v^2 --- \n\n"  +
                "where \"m\" is the vehicle's mass and \"v\" is the speed at the start of braking. \n" +
                "The work \"W\" done by braking is given by: \n\n" +
                "---   W = µ * m * g * d    ---\n\n" +
                "where  \"µ\"  is  the  coefficient  of  friction  between  the  road  surface  and  the  tires,  \"g\" is  the \n" +
                "gravity of Earth, and dis the distance travelled. \n\n" +
                "The braking distance (which is commonly measured as the skid length) given an initial \n" +
                "driving speed vis then found by putting W= E, from which it follows that \n\n" +
                "---  d = (v^2) / (2 * µ * g)  ---\n\n" +
                "The maximum speed given an available braking distance dis given by: \n\n" +
                "---  v = sqrt(2 * µ  * g * d)--- \n\n" +
                "Note  that  by  equating  E to  W,  the  mass  m of  the  vehicle  cancels  out.  You  are  left  with  the \n" +
                "gravitational  constant  g =  9.81  m/s^2" +
                " or  32.17  ft/s^2 ,  the  coefficient  of  friction  µ,  and  braking distance d. \n" +
                "For  µ,  you  will  need  to  research  the  vehicle  using  the  website  found  by  the  QR  Code.  That \n" +
                "website will provide you with the values of \"µ\" under different road conditions. \n" +
                "For  d,  the  braking  distance,  you  will  need  to  measure  the  length  of  the  skid  marks.  Use " +
                "whatever method provides the most accurate distance. Likely, this will be the value obtained by your Distance Tool, though you will have to determine if appropriate. \n" +
                "Entering the distance and other parameters into the last equation, and performing appropriate conversions, you should get an approximate speed or velocity of the vehicle. However," +
                " recall that  in  this  incident  scene  the  vehicle  was  still  traveling  at  some  velocity  before  it  struck  the " +
                "object. Thus, the computed velocity is less than the velocity at the application of the brakes. \n" +
                "See Appendix C – Skid Mark Example for two worked examples. \n\n" +
                "5.2.4  Answer \n" +
                "When you have taken the measurements, performed the research, and calculated a velocity, \n" +
                "you should be able to answer the question posed by  the dispatcher. Send your answer or best \n" +
                "guess. \n\n\n" +
                "--CONVERSIONSSSS--\n" +
                "1 meter = 3.2808399 feet\t\t\t\t" +
                "1 foot = 0.3048 meters\n" +
                "1 mile   = 1.609344 kilometers\t\t" +
                "1 km   = 0.621371192 miles\n\n" +
                "1 kg = 2.20462262 pounds";
    }


}
