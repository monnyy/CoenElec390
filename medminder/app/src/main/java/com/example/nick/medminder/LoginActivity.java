package com.example.nick.medminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected static final String TAG = "Login Activity"; //to log the activity for debugging
    protected Button loginButton = null;
    protected EditText passWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //alarmSet();
        Log.d(TAG, "The onCreate() event");
        setupUI();
    }

    protected void setupUI(){

        loginButton = (Button) findViewById(R.id.login_button);

        int[] maxLengths = {5,4};
        InputFilter[] FilterArrayID = new InputFilter[1];

        passWord = (EditText) findViewById(R.id.user_pwd);
        FilterArrayID[0] = new InputFilter.LengthFilter(maxLengths[1]);
        passWord.setFilters(FilterArrayID);


        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View V)
            {
                //get the inputed values
                String passValue = passWord.getText().toString();

                Log.d(TAG, "The onClick() LOGGED IN event");

                Toast toast = null;

                if (passValue.isEmpty() || !passValue.equals("1234"))
                {
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Password ", Toast.LENGTH_LONG);
                    toast.show();
                }

                if (passValue.equals("1234"))
                {
                    Intent intent = new Intent(LoginActivity.this, splash.class);
                    startActivity(intent);
                }
            }
        });
    }
}
