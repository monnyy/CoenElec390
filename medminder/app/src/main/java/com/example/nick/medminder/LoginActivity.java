package com.example.nick.medminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected static final String TAG = "Login Activity"; //to log the activity for debugging
    protected Button loginButton = null;
    protected EditText passWord = null;
    String passwordSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //alarmSet();
        Log.d(TAG, "The onCreate() event");
        setupUI();
    }

    protected void onStart() {
        super.onStart();

        SharedPreferences Prefs = getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
        passwordSP = Prefs.getString("loginPassword", "1234");
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

                if (passValue.isEmpty() || !passValue.equals(passwordSP))
                {
                    toast = Toast.makeText(getApplicationContext(), "Incorrect PIN", Toast.LENGTH_LONG);
                    toast.show();
                }

                if (passValue.equals(passwordSP))
                {
                    Intent intent = new Intent(LoginActivity.this, splash.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void hidekeyboardlogin(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
