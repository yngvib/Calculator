package com.example.calculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplay;

    private SharedPreferences mSP;
    private boolean mDoVibrate = false;
    private Vibrator mV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSP = PreferenceManager.getDefaultSharedPreferences( getBaseContext() );
        mV = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        mDisplay = findViewById( R.id.display );

        // Log.i("CALCULATOR", "Called onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDoVibrate = mSP.getBoolean("vibrate", false);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if ( id == R.id.action_settings ) {
           Intent intent = new Intent(this, CalcPreferenceActivity.class);
           startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void buttonPressed( View view ) {

        Button buttonView = (Button) view;
        String text, result;

        switch ( view.getId() ) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
                mDisplay.append( buttonView.getText() );
                break;
            case R.id.buttonP:
            case R.id.buttonM:
                text = mDisplay.getText().toString();
                if ( !text.endsWith("+") && !text.endsWith("-") ) {
                    mDisplay.append( buttonView.getText() );
                }
                break;
            case R.id.buttonD:
                text = mDisplay.getText().toString();
                if ( !text.isEmpty() ) {
                    mDisplay.setText( text.substring(0, text.length()-1) );
                }
                break;
            case R.id.buttonC:
                mDisplay.setText( "" );
                break;
            case R.id.buttonE:
                result = evalExpression( mDisplay.getText().toString() );
                mDisplay.setText( result );
                break;
        }

        if ( mDoVibrate ) {
            mV.vibrate( 500 );
            Toast.makeText(getApplicationContext(), "Vibrate ...", Toast.LENGTH_SHORT).show();
        }

    }

    private String evalExpression( String expr ) {
        boolean doAdd = true;
        BigInteger result;

        result = BigInteger.ZERO;
        StringTokenizer st = new StringTokenizer( expr, "+-", true );
        while ( st.hasMoreElements() ) {
            String token = st.nextToken();
            Log.i("CALCULATOR", token );
            if ( token.equals("+") ) {
                doAdd = true;
            }
            else if ( token.equals("-") ) {
                doAdd = false;
            }
            else {
                BigInteger value = new BigInteger( token );
                if ( doAdd ) {
                    result = result.add( value );
                }
                else {
                    result = result.subtract( value );
                }
            }
        }
        return result.toString();
    }
}
