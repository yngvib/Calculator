package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDisplay = findViewById( R.id.display );

        // Log.i("CALCULATOR", "Called onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu );
        return true;
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
