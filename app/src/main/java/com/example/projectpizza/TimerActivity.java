package com.example.projectpizza;

import java.util.Date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TimerActivity extends Activity {
	TextView timer;
    Button button_reset;
    Button button_start_stop;
    Button button_submit;
    Button addTimeButton;
    Button addTimeNoteButton, saveTimeNotesButton;
    EditText edit_submission, timeNotesBox;
    TextView result;
    
    Handler handler = new Handler();
    
    boolean timer_started = false;
    boolean timer_zero_reset = true;
    
    long start_time;
    long pause_time;
    
    final int SECONDS_TO_MILLISECONDS = 1000;
    final int MINUTES_TO_MILLISECONDS = 60 * 1000;
    public static final String PERFS = "save";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // This applies the layout specified in 
        //   res->layout->activity_request_clue.xml
        // to our Activity. We can now find views within that layout and
        // manipulate them. Don't try to call findViewById() before this!
        setContentView(R.layout.activity_timer);
		
        // setup activity widgets
        timer = (TextView) findViewById(R.id.TextTimer);
        //timer.setText("00:00:000");
        pause_time = 0;
        edit_submission = (EditText) findViewById(R.id.EditSubmission);
        //edit_submission.setHint("Type submission here");

        // Assign functions to buttons
        button_reset = (Button) findViewById(R.id.ButtonReset);
        button_reset.setOnClickListener(reset);
        
        button_start_stop = (Button) findViewById(R.id.ButtonStartStop);
        button_start_stop.setOnClickListener(startStopPressed);
        button_start_stop.setText("Start");
        
        button_submit = (Button) findViewById(R.id.buttonSubmit);
        //button_submit.setOnClickListener(submit);

        addTimeButton = (Button) findViewById(R.id.addTimeButton);
        saveTimeNotesButton = (Button) findViewById(R.id.saveTimeNotesButton);
        addTimeNoteButton = (Button) findViewById(R.id.addTimeNoteButton);

        timeNotesBox = (EditText) findViewById(R.id.timeNotesBox);
        SharedPreferences load = getSharedPreferences(PERFS, 0);
        timeNotesBox.setText(load.getString("timeNotes", "nope"));
    }
    
    @Override
	protected void onDestroy() {
		timer_started = false;
		super.onDestroy();
	}
    
    /**
     * React to pressing the reset button
     */ 
    View.OnClickListener reset = new OnClickListener() {
        public void onClick(View v) {
        	// On reset simply set the timer text to zeroes 
        	// and reset the starting point in time to now
        	start_time = (new Date()).getTime();
        	timer_zero_reset = true;
            button_reset.setEnabled(false);
            addTimeButton.setEnabled(false);
            addTimeNoteButton.setEnabled(false);
        	timer.setText("");
        }
    };
    
    /**
     * React to pressing the start/stop button
     */    
	View.OnClickListener startStopPressed = new OnClickListener() {
		public void onClick(View v) {
			// If the timer is running then stop the timer
			// and recorded the paused point in time
			if(timer_started)
			{
				pause_time = (new Date()).getTime();
				timer_started = false;
				button_start_stop.setText("Start");
                button_reset.setEnabled(true);
                addTimeButton.setEnabled(true);
                addTimeNoteButton.setEnabled(true);
			}
			else
			{// If the timer is stopped then start the timer
				
				if(timer_zero_reset)
				{// set the starting point in time to now if the timer was previously reset
					start_time = (new Date()).getTime();
				}
				else
				{	// since the timer was pause the start reference point in time 
					// needs to be adjusted for the time spent paused
					start_time += (new Date()).getTime() - pause_time;
				}
				
				timer_started = true;
				timer_zero_reset = false;
				button_start_stop.setText("Stop");
                button_reset.setEnabled(false);
                addTimeButton.setEnabled(false);
                addTimeNoteButton.setEnabled(false);
				// post event to thread to execute an update to the UI with 0 wait time
				handler.postDelayed(update, 0);
			}
		}
	};
	
    /**
     * This function is used to periodically update the UI
     */    
	private final Runnable update = new Runnable() {
    	public void run() {
    		// if timer is running then update the text for the timer
    		if(timer_started)
    		{
    			long timer_diff = (new Date()).getTime() - start_time;
    			int minutes = (int)(timer_diff/MINUTES_TO_MILLISECONDS);
    			int seconds = (int)((timer_diff%MINUTES_TO_MILLISECONDS) / SECONDS_TO_MILLISECONDS);
    			int milliseconds = (int) timer_diff%SECONDS_TO_MILLISECONDS;
    			// post this function again to the thread to run 80 milliseconds in the future
    			handler.postDelayed(update, 80);
    			setTimerText(minutes, seconds, milliseconds);
    		}
        }
	};
	
	/**
	 * Set the timer timer in the format of 00:00:000
	 * @param minutes
	 * @param seconds
	 * @param milliseconds
	 */
	private void setTimerText(int minutes, int seconds, int milliseconds)
	{
		timer.setText(String.format("%02d", minutes)+":"
				+ String.format("%02d", seconds)+":"
				+ String.format("%03d", milliseconds));
	}
	
	
    View.OnClickListener submit = new OnClickListener() {
        public void onClick(View v) {
        	String textToSubmit = getTextToSubmit();
    		
    		// Actually do the submitting
    		submitText(textToSubmit);
    		
    		// The following two lines are just some code to make the soft
    		// keyboard hide when you press the submit button. Feel free to
    		// look up how SystemServices work, but for now you can just 
    		// copy and paste these lines anywhere you just want to make the
    		// keyboard go away.
    		InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE); 
    		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                       InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
    
    /**
     * Generates the text to submit
     * 
     * @return the text to be submitted
     */
    private String getTextToSubmit() {
    	// This is just an example of how you may want to split out some of
    	// your larger tasks into smaller chunks to logically break down the
    	// problem into simpler and easier-to-read pieces.
    	
        return edit_submission.getText().toString();
    }

    public void addTimeClicked (View view)
    {
        edit_submission.setText("" + edit_submission.getText() + timer.getText());
    }

    public void submitClick (View view)
    {
        String contactName = "BRATA";
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", getPhoneNumber(contactName));
        smsIntent.putExtra("sms_body", "" + edit_submission.getText() );
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


    /**
     * Performs the actual submission of text
     * 
     * @param text
     * 		the text to be submitted
     */
    private void submitText(String text) {
        // should confirm submission then submit
    }

    public void timeNotesSaveClick (View view)
    {
        String message = timeNotesBox.getText().toString();
        SharedPreferences saveTimeNotes = getSharedPreferences(PERFS, 0);
        SharedPreferences.Editor editor = saveTimeNotes.edit();
        editor.putString("timeNotes", message);
        editor.commit();
    }

    public void addTimeNotesClick (View view)
    {
        timeNotesBox.setText("" + timeNotesBox.getText() + "\n" +  timer.getText());
    }
}
