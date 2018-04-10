package com.example.nick.medminder;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nick.medminder.data.AlarmReminderContract;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

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
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Name ", Toast.LENGTH_LONG);

                else if (editDOBValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Date of Birth ", Toast.LENGTH_LONG);

                else if (editAddressValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Home Address ", Toast.LENGTH_LONG);

                else if (editEnameValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Emergency Name ", Toast.LENGTH_LONG);

                else if (editEphoneValue.isEmpty())
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Emergency Phone ", Toast.LENGTH_LONG);

                else
                {
                    //save the values
                    saveTheValues(editNameValue, editDOBValue, editAddressValue, editEnameValue, editEphoneValue);

                    focusDisable();
                    toast = Toast.makeText(getApplicationContext(), "Profile Updated! :)" , Toast.LENGTH_LONG);
                }

                toast.show();

                startActivity(new Intent(CreateUserProfileActivity.this, splash.class));
            }

        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_pass,menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.editPass:
//                editPassword();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

//    public void editPassword(){
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Change Password");
//
//        final String o;
//        final EditText oldPass = new EditText(this);
//        final EditText newPass = new EditText(this);
//        final EditText confirmPass = new EditText(this);
//        oldPass.setInputType(InputType.TYPE_CLASS_NUMBER);
//        newPass.setInputType(InputType.TYPE_CLASS_NUMBER);
//        confirmPass.setInputType(InputType.TYPE_CLASS_NUMBER);
//
//        oldPass.setHint("Old Password");
//        newPass.setHint("New Password");
//        confirmPass.setHint("Confirm New Password");
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//
//        ll.addView(oldPass);
//        ll.addView(newPass);
//        ll.addView(confirmPass);
//
//        SharedPreferences Prefs = getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = Prefs.edit();
//        o = Prefs.getString("loginpassword", "1234");
//
//        builder.setView(ll);
//
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (oldPass.getText().toString().isEmpty() || !oldPass.getText().toString().equals(o)){
//                    Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
//                    builder = (AlertDialog.Builder) dialog;
//                }
//                if (newPass.getText().toString().isEmpty() || confirmPass.getText().toString().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (!newPass.getText().toString().equals(confirmPass.getText().toString())) {
//                    Toast.makeText(getApplicationContext(), "Invalid Entry", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else {
//                    ContentValues values = new ContentValues();
//                    editor.putString("loginpassword", newPass.getText().toString());
//                    editor.apply();
//                    editor.commit();
//                }
//
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
//    }

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