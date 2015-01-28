package com.example.projectpizza;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

public class Encryption extends Activity {

    public static TextView switchedMessage;
    public static EditText originalMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        switchedMessage = (TextView) findViewById(R.id.switchedMessage);
        originalMessage = (EditText) findViewById(R.id.originalMessage);
    }


    public static void encryptOrDecrypt (View view)
    {
        String original = originalMessage.getText().toString();
        String switched = "";
        for (int i=0; i<original.length()-1; i+=2)
        {
            switched = switched + original.charAt(i+1) + original.charAt(i);
        }
        if (original.length()%2==1) switched = switched + original.charAt(original.length()-1);
        switchedMessage.setText(switched);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.encryption, menu);
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


}
