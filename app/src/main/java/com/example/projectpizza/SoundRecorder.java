package com.example.projectpizza;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class SoundRecorder extends Activity {
    /**
     * Tag to track errors in LogCat
     */
    private static final String LOG_TAG = "SoundRecordingActivity";

    /**
     * Directory where recordings are kept
     */
    private static String mFolder = null;

    /**
     * Recording state (true means ready to record, false means recording)
     */
    private boolean mStartRecording = true;

    /**
     * Playback state (true means ready to play, false means playing)
     */
    private boolean mStartPlaying = true;

    /**
     * Handle to the record button
     */
    private Button mRecordButton = null;

    /**
     * Handle to the MediaRecorder
     */
    private MediaRecorder mRecorder = null;

    /**
     * Handle to the play button
     */
    private Button mPlayButton = null;

    /**
     * Handle to the MediaPlayer
     */
    private MediaPlayer mPlayer = null;

    /**
     * Handle to the radio group for file selection
     */
    private RadioGroup filesGroup;

    /**
     * List of possible files to save from
     */
    private String[] filesList;

    /**
     * Constructor. Sets the directory for recordings storage
     */
    public SoundRecorder() {
        mFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFolder += "/brata/";

        File brataDir = new File(mFolder);
        brataDir.mkdirs();

        filesList = new String[]{
                "Recording1",
                "Recording2",
                "Recording3",
                "Recording4",
                "Recording5",
        };
    }

    /**
     * Used to create handles to sound files
     */
    private void setUpSoundFiles()
    {
        for (int i = 0; i<filesList.length; i++) {

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(filesList[i]);
            filesGroup.addView(radioButton);

            if(i == 0)
            {
                filesGroup.check(radioButton.getId());
            }
        }

    }

    /**
     * Returns the path for the currently selected file
     * @return
     */
    private String getCurrentFile()
    {
        return mFolder +
                ((RadioButton) findViewById(filesGroup.getCheckedRadioButtonId())).getText().toString();
    }

    /**
     * Handle the record start/stop
     * @param start True to start recording, false to stop.
     */
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    /**
     * Handle the playback start/stop
     * @param start True to start playback, false to stop.
     */
    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    /**
     * Play the audio
     */
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(playCompleted);
        Toast.makeText(this, getCurrentFile(), Toast.LENGTH_LONG).show();
        try {
            mPlayer.setDataSource(getCurrentFile());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    /**
     * Stop playing the audio
     */
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }


    /**
     * Start recording
     */
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(getCurrentFile());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    /**
     * Stop recording
     */
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the content view (can call findViewById after this)
        setContentView(R.layout.activity_sound_recorder);

        // Need filename textview to get the filename text
        //mFileName = (TextView)findViewById(R.id.TextFileName);
        // Need the record/play buttons to add the listeners and set the text
        mRecordButton = (Button)findViewById(R.id.RecordButton);
        mPlayButton = (Button)findViewById(R.id.PlayButton);

        // Set the listeners for clicks
        mRecordButton.setOnClickListener(record);
        mPlayButton.setOnClickListener(play);

        // Set up files selection group
        filesGroup = (RadioGroup) findViewById(R.id.FilesRadioGroup);
        setUpSoundFiles();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Free up the resources
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * Listener for when the user clicks the record button
     */
    View.OnClickListener record = new View.OnClickListener() {
        public void onClick(View v) {
            onRecord(mStartRecording);
            if (mStartRecording) {
                mRecordButton.setText("Stop recording");
            } else {
                mRecordButton.setText("Start recording");
            }
            mStartRecording = !mStartRecording;
        }
    };

    /**
     * Listener for when the user clicks the play button
     */
    View.OnClickListener play = new View.OnClickListener() {
        public void onClick(View v) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                mPlayButton.setText("Stop playing");
            } else {
                mPlayButton.setText("Start playing");
            }
            mStartPlaying = !mStartPlaying;
        }
    };

    /**
     * Listener for when playback completed to set button in correct state
     */
    MediaPlayer.OnCompletionListener playCompleted = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer arg0) {
            mStartPlaying = true;
            mPlayButton.setText("Start playing");
        }

    };
}
