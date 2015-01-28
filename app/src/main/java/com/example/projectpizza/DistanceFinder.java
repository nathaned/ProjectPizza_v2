package com.example.projectpizza;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class DistanceFinder extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;

    LinearLayout oView;
    LinearLayout oView2;
    boolean overlayEnabled=false;
    boolean overlay2Enabled=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance_finder);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);


    }


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.distance_finder, menu);
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

    public void overlayButtonClick (View view)
    {
        /*final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);

        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup mTopView = (ViewGroup) inflater.inflate(R.layout.activity_distance_finder, null);
        getWindow().setAttributes(params);
        wm.addView(mTopView, params);*/

        if(overlayEnabled)
        {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(oView);
            overlayEnabled = false;
        }
        else
         {
             oView = new LinearLayout(this);
             //oView.setBackground(getResources().getDrawable(R.drawable.distance_bars));
             oView.setBackgroundResource(R.drawable.distance_bars);
             //oView.setBackgroundColor(0x88ff0000); // The translucent red color
             //oView.setBackgroundResource(R.drawable.light_gauge_modified);
             final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                     WindowManager.LayoutParams.FLAG_FULLSCREEN,
                     WindowManager.LayoutParams.FLAG_FULLSCREEN,
                     WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                     WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                     PixelFormat.TRANSLUCENT);
             WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
             wm.addView(oView, params);
             overlayEnabled = true;
         }
    }

    public void overlay2ButtonClick (View view)
    {
        if(overlay2Enabled)
        {
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.removeView(oView2);
            overlay2Enabled = false;
        }
        else
        {
            oView2 = new LinearLayout(this);
            oView2.setBackgroundResource(R.drawable.distance_grid);
            final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.addView(oView2, params);
            overlay2Enabled = true;
        }
    }

    public void captureClick (View view)
    {
        /*Button captureButton = (Button) findViewById(R.id.captureButton);
        captureButton.setText("Captureaaaaaaa");*/
    }


    /*boolean optimized = false;
    public void optimizeButtonClick (View view)
    {
        if (!optimized)  {
            Button optimizeButton = (Button) findViewById(R.id.optimizeButton);
            optimizeButton.setText("Optimized!");
            optimized=true;
        }
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        //releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    /*private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }*/

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }


    }
}


