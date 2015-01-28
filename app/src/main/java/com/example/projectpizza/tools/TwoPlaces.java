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

public class TwoPlaces extends Activity {

    boolean infoShowing=false;
    TextView showTwoInfoText;
    Button showTwoInfoButton;
    String docInfoText;
    EditText timelineText, path1Text, path2Text, path3Text;

    public static final String PERFS = "save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_places);

        showTwoInfoButton = (Button) findViewById(R.id.showInfoTwoButton);
        showTwoInfoText = (TextView) findViewById(R.id.showInfoTwoText);
        timelineText = (EditText) findViewById(R.id.timelineBox);
        path1Text = (EditText) findViewById(R.id.path1Text);
        path2Text = (EditText) findViewById(R.id.path2Text);
        path3Text = (EditText) findViewById(R.id.path3Text);

        SharedPreferences load = getSharedPreferences(PERFS, 0);
        String previousTimeline = load.getString("timeline", "nope");
        timelineText.setText(previousTimeline);

        path1Text.setText(load.getString("path1", "nope"));
        path2Text.setText(load.getString("path2", "nope"));
        path3Text.setText(load.getString("path3", "nope"));

        setdocInfoText();
    }

    public void saveTimelineClick (View view)
    {
        String message = timelineText.getText().toString();
        SharedPreferences saveTimeline = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveTimeline.edit();
        editor.putString("timeline", message);
        editor.commit();

    }

    public void savePath1Click (View view)
    {
        String message = path1Text.getText().toString();
        SharedPreferences savePath1 = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = savePath1.edit();
        editor.putString("path1", message);
        editor.commit();
    }

    public void savePath2Click (View view)
    {
        String message = path2Text.getText().toString();
        SharedPreferences savePath2 = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = savePath2.edit();
        editor.putString("path2", message);
        editor.commit();
    }

    public void savePath3Click (View view)
    {
        String message = path3Text.getText().toString();
        SharedPreferences savePath3 = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = savePath3.edit();
        editor.putString("path3", message);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.two_places, menu);
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

    public void showInfoTwoClick (View view)
    {
        if (!infoShowing)
        {
            showTwoInfoText.setText(docInfoText);
            showTwoInfoButton.setText("Hide info");
            infoShowing=true;
        }
        else
        {
            showTwoInfoText.setText("");
            showTwoInfoButton.setText("Show info");
            infoShowing=false;
        }
    }

    private void setdocInfoText()
    {
        docInfoText = "5.3.1  The Scene \n" +
                "When you arrive at the incident scene at location  A, you find that a valuable item was stolen " +
                "from a locked safe at location B. The prime suspect, Wyle E. Coyote, was hosting a party at his " +
                "house  at  location  A.  Several  witnesses  confirmed  the  suspect  was  indeed  hosting  the  party, " +
                "though he was not necessarily visible and accounted for at all times. \n\n" +
                "Several facts are confirmed by the officer (your escort) on the scene. These facts include that " +
                "the  suspect  had  access  to  the  safe,  knows  the  safe’s  combination,  and  the  security  code  at " +
                "location  Bas he works there during the day. Only key personnel are cleared to deactivate the " +
                "system. Other Forensic Teams are questioning them. \n\n" +
                "The suspect has a confirmed “foot injury” and is unable to walk very fast or far. Both the location " +
                "of the party,  A, and the crime scene,  B, are in a “car-less community” where the only allowed " +
                "modes of travel are by foot, bike, or golf cart, and only on designated paved paths. Note that the " +
                "landscaping makes it impossible to use a bike or golf cart except on the paved pathways. " +
                "The object was discovered missing this morning. Your escort will give you the time of the theft " +
                "last  evening  as  projected  by  when  the  security  system  was  turned  off—there  was  no  alarm " +
                "raised and no investigation begun until earlier this morning. \n\n" +
                "The suspect does not own a golf cart, but there are three (3) golf carts owned by neighbors less " +
                "than 1 minute’s walking time away. Call this time  W, in seconds, which will be given to you by " +
                "your escort. Even with a foot injury, the suspect could reach any of the golf carts in time W. (See " +
                "Figure 5-4.) \n\n" +
                "Each golf cart is a different make/model. The maximum and typical speed for each golf cart can " +
                "be found on the manufacturer’s website, accessed bya QR Code on each golf cart. " +
                "There are different pathways between each golf cart parking spot and location B. " +
                "No golf carts were reported missing or moved this morning. " +
                "A Forensic Team at location B reports that the estimated time required at the crime scene is: " +
                "• Security system could be disabled in X seconds using the security code \n" +
                "• The safe could be opened and emptied in Y seconds \n" +
                "• Time from the building entrance to the safe (one way) is estimated at Z seconds at a " +
                "slow (i.e., injured foot) walk The values for X, Y, and Z, in seconds, will be given to you by your escort. \n\n" +
                "There  is  one more  time  value  that  you  must  calculate—time  T. This  is the  shortest time  you " +
                "estimate that the suspect requires to drive a golf cart from its parked location to the crime scene " +
                "at location  B. Factors used to compute T include the distance from each golf cart to the crime " +
                "scene, and the maximum speed of each golf cart. Thus time value T becomes a distance-rate-time problem:\n" +
                "distance = rate x time \n OR \n time = distance/time\n\n" +
                "Witnesses are available to take short, quick statements. It is hoped that their statements will \n" +
                "indicate when the suspect was visible and when he was unaccounted for. If a period of being \n" +
                "unaccounted for overlaps with the time of the crime and permits the suspect to travel to and \n" +
                "from the crime scene, then he should be more thoroughly questioned. \n\n" +
                "5.3.2  Objective \n" +
                "Using all the information already known, you will need to determine if the suspect could have left" +
                "the party at location  A, traveled to the crime scene  B, committed the theft, and then traveled " +
                "back to the party within the times given by witnesses. \n" +
                "Objective #1is to collect witness statements that identify when the suspect was seen and/or " +
                "accounted for. These times give an “alibi” for the  suspect. For instance, a witness may say, “I " +
                "was talking to Mr. Coyote from around 7:50 PM to 8:00 PM, you know, when the game started.” " +
                "Another witness may state, “I think I saw him walk  into the kitchen at a quarter to 8:00.” You " +
                "may wish to compute the “gaps” or duration time between witness sightings. Then, compare the " +
                "gaps to see if the time of the crime occurs within one of these gaps. If not, the suspect is likely " +
                "innocent. However, if the crime took place during a period when the suspect was unaccounted " +
                "for, continue to  Objective #2. Hint: Use the Recording Tool to record witness statements and " +
                "play them back later. \n" +
                "Objective #2is to calculate how long the suspect needed to leave the party and travel to the" +
                "crime scene and back. Obviously, less time away from the party is the goal. Once the time is" +
                "estimated,  it  can  be  compared  to  the  gaps  discovered  in  Objective  #1.  If  the  shortest  time " +
                "required to commit the crime is longer than the time from  Objective #1, the suspect is likely " +
                "completely innocent. If not, however, he should be brought in for further questioning. \n\n" +
                "5.3.3  Method \n" +
                "One value you must compute is T, the shortest estimated time required to drive a golf cart from " +
                "its parked location to the crime scene. You can assume that the return trip from the crime scene " +
                "back to the parked location is the same. " +
                "Research will tell you the typical and maximum speed of each golf cart. This will be provided on " +
                "the websites in miles-per-hour, or MPH. What’s missing is the  distance from each parked golf " +
                "cart and the crime scene at location B. \n\n" +
                "The paths from each parked golf cart will be clearly laid out on the course. You must measure " +
                "each leg of each path to determine an estimated total distance to location B. Use whichever tool " +
                "is best suited to calculate/measure distances. \n\n" +
                "5.3.3.1  Path Details \n" +
                "Refer to Figure 5-4, which is an  example layout of paths—it is representative only and should " +
                "not be considered how the real paths will be on the course. There are one or more legs to each " +
                "path. You won’t know the number of legs prior to observing the course. Each leg is straight, i.e., " +
                "it is  not curved. The intersection from one leg to another is clearly defined so you can easily " +
                "determine where each leg starts and ends. Each leg  is likely to be a different length with the " +
                "shortest length being no less than 15 feet. \n\n" +
                "5.3.3.2  Approach \n" +
                "You will use your Navigation and/or Distance Tools  to measure each path leg. Your FAT need " +
                "not track or record each leg—just total distance from each golf cart to location B. Thus, you can " +
                "record and add up distances on a piece of paper, then enter the three totals into your FAT. " +
                "Once each distance is known, associate the speed of each golf cart (Miles-per-Hour or MPH) " +
                "with the shortest path a given golf cart would take(feet). This should allow you to compute the " +
                "three  times  and  then  pick  the  smallest  value  and  assign  to  time  T (seconds).  Note  the  unit " +
                "conversions that are necessary! \n\n" +
                "5.3.3.3  Timeline \n" +
                "The key to solving this incident scene is to create a master timeline. Take what you know to \n" +
                "start such a timeline as shown in Figure 5-5 with example values for W, X, Y, Z, andT.";
    }


}
