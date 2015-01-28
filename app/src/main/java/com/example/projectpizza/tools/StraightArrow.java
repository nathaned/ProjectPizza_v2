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


public class StraightArrow extends Activity {

    boolean infoShowing=false;
    TextView showStraightInfoText;
    Button showStraightInfoButton;
    String docInfoText;
    EditText straightNotesText;

    public static final String PERFS = "save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_straight_arrow);

        showStraightInfoButton = (Button) findViewById(R.id.showInfoStraightButton);
        showStraightInfoText = (TextView) findViewById(R.id.showInfoStraightText);
        straightNotesText = (EditText) findViewById(R.id.striaghtNotesBox);

        SharedPreferences load = getSharedPreferences(PERFS, 0);
        straightNotesText.setText(load.getString("straightNotes", "nope"));

        setdocInfoText();
    }

    public void straightNotesSaveClick (View view)
    {
        String message = straightNotesText.getText().toString();
        SharedPreferences saveStraightNotes = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveStraightNotes.edit();
        editor.putString("straightNotes", message);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.straight_arrow, menu);
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

    public void showInfoStraightClick (View view)
    {
        if (!infoShowing)
        {
            showStraightInfoText.setText(docInfoText);
            showStraightInfoButton.setText("Hide info");
            infoShowing=true;
        }
        else
        {
            showStraightInfoText.setText("");
            showStraightInfoButton.setText("Show info");
            infoShowing=false;
        }
    }

    public void setdocInfoText()
    {
        docInfoText = "5.1.1  The Scene \n" +
                "An archery arrow  is lodged in a door or  wall on theside of a house. Rightly so, the irritated " +
                "owner  is  concerned  and  wishes  to  press  charges  against  the  perpetrator  who  launched  the " +
                "arrow. \n" +
                "It  appears  that  the  arrow  flew  over  a  tall  obstacle before  striking  the  house.  This  obstacle " +
                "obscures a direct line-of-sight from the arrow to possible points of origin. See Figure 5-1 which " +
                "shows a  view  looking down on the  whole  incident scene and Figure 5-2 which shows a side " +
                "view with possible arrow trajectories over the obstacle. \n\n" +
                "If you were to walk around the obstacle and away from the arrow, you would find three (3) sites " +
                "of  interest,  all  of  them  roughly  in  the  direction  from  which  the  arrow  was  launched,  and  at " +
                "different distances from the arrow in the door. At  each site there is an archery bow. Each bow " +
                "has a QR Code on it. There are also “clues” that potentially identify a suspect. These clues may " +
                "be foot prints or a witness who saw the suspect and can implicate them. \n\n" +
                "5.1.2  Objective \n" +
                "You must locate the most likely point of origin from which the arrow was launched. Then, using evidence found at the scene, identify the culprit.\n\n" +
                "5.1.3  Method \n" +
                "Measure the compass bearing along the shaft of the arrow to determine the direction from which " +
                "the arrow was launched. You must take into account  only the horizontal direction element, or " +
                "azimuth, of the arrow, disregarding any vertical direction element, or elevation, of the arrow. " +
                "Since there is an obstacle blocking the line-of-sight, it is suggested that you use your Navigation " +
                "Tool in the “how far have I gone” mode. This tool should allow you to set or mark your current " +
                "location (at the arrow) and then walk around the obstacle to follow the same bearing away from " +
                "the arrow as was measured previously. \n\n" +
                "If  your  Navigation  Tool  and  initial  bearing  measurement  are  accurate  enough,  you  should " +
                "encounter one of the three sites with an archery bow. Your Navigation Tool should also provide " +
                "you with the distance from the arrow to the site. \n\n" +
                "Use your QR Code Reader Tool to decode the QR Code  on the bow, and then visit the bow " +
                "manufacture’s website to research the typical distance that this bow can launch an arrow. With " +
                "proper observations and measurements, you should beable to confirm which site the arrow was " +
                "launched from. \n\n" +
                "Use  the  other  clues  at  the  site  to  determine  the  suspect.  This  may  involve  taking  witness " +
                "testimony. \n\n" +
                "5.1.4  Answer \n" +
                "When you have enough information to answer the question posed by the dispatcher, send your " +
                "answer or best guess.";
    }

}
