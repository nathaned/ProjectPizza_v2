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

import java.util.ArrayList;
import java.util.Arrays;

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

    public void newDecrypt(View view)
    {
        String given = originalMessage.getText().toString();
        int [] size = findPrimes(given.length());
        //size[0] is number of rows; size[1] is number of columns
        if (size[0]==-1)
        {
            switchedMessage.setText("-1, length: " + given.length());
            return;
        }
        String out = "";
        for (int col=0; col<size[0]; col++)
        {
            for (int row=0; row<size[1]; row++)
            {
                out+=given.charAt( (row*size[0]) + col );
            }
        }
        switchedMessage.setText(out);
        switchedMessage.setText(switchedMessage.getText().toString()+"\n"+size[0] + "x" + size[1] + " (" + given.length() + ")");

    }

    public static int[] findPrimes(int length)
    {
        int [] found = {-1, -1};
        final ArrayList<Integer> PRIMES = new ArrayList<Integer>(Arrays.asList(
                2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53,
                59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113,
                127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179));
        for (int i=0; i<PRIMES.size(); i++)
        {
            if (length % PRIMES.get(i)==0 && PRIMES.contains(length/PRIMES.get(i)))
            {
                found[0] = PRIMES.get(i);
                found[1] = length/PRIMES.get(i);
                break;
            }
        }
        return found;
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
