package com.example.nick.medminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class CreateUserProfileActivity extends AppCompatActivity {

    protected EditText editName, editDOB, editAddress, editEname, editEphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_profile);

        editName = (EditText) findViewById(R.id.userName);
        editDOB = (EditText) findViewById(R.id.userDOB);
        editAddress = (EditText) findViewById(R.id.userAddress);
        editEname = (EditText) findViewById(R.id.guardName);
        editEphone = (EditText) findViewById(R.id.guardPhone);


        final Button saveButton = (Button) findViewById(R.id.save_profile_button);
        saveButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                //get the inputed values
                String editNameValue = editName.getText().toString();
                String editDOBValue = editDOB.getText().toString();
                String editAddressValue = editAddress.getText().toString();
                String editEnameValue = editEname.getText().toString();
                String editEphoneValue = editEphone.getText().toString();

                Toast toast;

                if (editNameValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Name ", LENGTH_LONG);

                else if (editDOBValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Date of Birth ", LENGTH_LONG);

                else if (editAddressValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Home Address ", LENGTH_LONG);

                else if (editEnameValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Emergency Name ", LENGTH_LONG);

                else if (editEphoneValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Emergency Phone ", LENGTH_LONG);

                else
                {
                    //save the values
                    saveTheValues(editNameValue, editDOBValue, editAddressValue, editEnameValue, editEphoneValue);

                    focusDisable();
                    toast = Toast.makeText(getApplicationContext(), "Profile Updated! :)" , LENGTH_LONG);
                }

                toast.show();

                startActivity(new Intent(CreateUserProfileActivity.this, splash.class));
            }

        });
    }

    protected  void onStart()
    {
        super.onStart();

        //get the inputed values
        String editNameValue = editName.getText().toString();
        String editDOBValue = editDOB.getText().toString();
        String editAddressValue = editAddress.getText().toString();
        String editEnameValue = editEname.getText().toString();
        String editEphoneValue = editEphone.getText().toString();

        ExistingValues(editNameValue, editDOBValue, editAddressValue, editEnameValue, editEphoneValue);

    }

    private void ExistingValues(String eNV, String eDV, String eAV, String eEnV, String eEpV)
    {
        SharedPreferences Prefs = getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);

        if(eNV != null || eDV != null || eAV != null || eEnV != null || eEpV != null)
        {
            editName.setText(Prefs.getString("profileName", eNV));
            editDOB.setText(Prefs.getString("profileDOB", eDV));
            editAddress.setText(Prefs.getString("profileAddress", eAV));
            editEname.setText(Prefs.getString("profileEname", eEnV));
            editEphone.setText(Prefs.getString("profileEphone", eEpV));
        }
    }


    private void saveTheValues(String eNV, String eDV, String eAV, String eEnV, String eEpV)
    {
        SharedPreferences Prefs = getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = Prefs.edit();

        editor.putString("profileName", eNV);
        editor.putString("profileDOB", eDV);
        editor.putString("profileAddress", eAV);
        editor.putString("profileEname", eEnV);
        editor.putString("profileEphone", eEpV);
        editor.apply();
        editor.commit();
    }

    protected void focusDisable()
    {
        editName.setFocusable(false);
        editDOB.setFocusable(false);
        editAddress.setFocusable(false);
        editEname.setFocusable(false);
        editEphone.setFocusable(false);
    }




    public void hidekeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}