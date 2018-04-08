package com.example.nick.medminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    Button btn_edit_profile, btn_view_reminder, btn_create_reminder;

    TextView mainName, mainDOB, mainAddress;

    String name, d_o_b, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        mainName = findViewById(R.id.seniorName);
        mainDOB = findViewById(R.id.seniorDOB);
        mainAddress = findViewById(R.id.seniorAddress);

        btn_edit_profile = (Button) findViewById(R.id.edit_profile_button);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toProfileActivity();
            }
        });


        btn_view_reminder = (Button) findViewById(R.id.view_reminders_button);
        btn_view_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash.this, viewReminder.class);
                startActivity(intent);
            }
        });


        btn_create_reminder = (Button) findViewById(R.id.new_reminder_button);
        btn_create_reminder.setVisibility(View.INVISIBLE);
        btn_create_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_guardian_switch,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.admin:
                Toast.makeText(this,"Now under user model",Toast.LENGTH_LONG).show();
                btn_create_reminder.setVisibility(View.INVISIBLE);
                return true;
            case R.id.guardian:
                Toast.makeText(this,"Now under guardian model",Toast.LENGTH_LONG).show();
                btn_create_reminder.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected  void onStart()
    {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("ProfilePreference", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("profileName",null);
        d_o_b = sharedPreferences.getString("profileDOB",null);
        address = sharedPreferences.getString("profileAddress",null);

        if(name == null || d_o_b == null || address == null)
            toProfileActivity();

        mainName.setText(name);
        mainDOB.setText(d_o_b);
        mainAddress.setText(address);
    }

    void toProfileActivity()
    {
        startActivity(new Intent(this,CreateUserProfileActivity.class));
    }
}