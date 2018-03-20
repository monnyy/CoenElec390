package com.example.nick.medminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    protected static final String TAG = "Login Activity"; //to log the activity for debugging
    protected Button loginButton = null;
    protected EditText userName, passWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Log.d(TAG, "The onCreate() event");
        setupUI();

    }

    protected void setupUI(){

        loginButton = (Button) findViewById(R.id.login_button);

        int[] maxLengths = {5,4};
        InputFilter[] FilterArrayID = new InputFilter[1];
        InputFilter[] FilterArrayAge = new InputFilter[1];


        userName = (EditText) findViewById(R.id.user_name);
        FilterArrayAge[0] = new InputFilter.LengthFilter(maxLengths[0]);
        userName.setFilters(FilterArrayAge);

        passWord = (EditText) findViewById(R.id.user_pwd);
        FilterArrayID[0] = new InputFilter.LengthFilter(maxLengths[1]);
        passWord.setFilters(FilterArrayID);





            loginButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View V){

                    //get the inputed values
                    String nameValue = userName.getText().toString();
                    String passValue = passWord.getText().toString();

                    Log.d(TAG, "The onClick() LOGGED IN event");
                    Toast toast = null;

                    if (nameValue.isEmpty()) {
                        toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Username ", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    else if (passValue.isEmpty()) {
                        toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Password ", Toast.LENGTH_LONG);
                        //show when the save button has been clicked
                        toast.show();
                    }

                    else if (nameValue.equals("admin") && passValue.equals("1234")) {


                        Intent intent = new Intent(V.getContext(), CreateUserProfileActivity.class);
                        startActivity(intent);
                    }
                }
            });
    }


}