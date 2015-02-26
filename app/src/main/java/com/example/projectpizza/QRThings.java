package com.example.projectpizza;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class QRThings extends Activity {

    EditText scanMessageText;
    TextView returnedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrthings);

        scanMessageText = (EditText)findViewById(R.id.scanMessageText);
        returnedText = (TextView)findViewById(R.id.returnedText);
    }


    public void scanNoMessage(View view)
    {
        MasterServerCommunicator.getInstructionUsingQR(QRThings.this);
    }

    public void scanWithMessage(View view)
    {
        String message = scanMessageText.getText().toString();
        MasterServerCommunicator.sendMessageUsingQR(QRThings.this, message);
    }
    @Override
    protected void onResume() {
        // Auto-generated method stub
        super.onResume();

        returnedText.setText(MessageDecoder.encodedMessage + "\n\n" + MessageDecoder.decodedMessage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_qrthings, menu);
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
}
