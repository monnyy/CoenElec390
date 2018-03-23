package com.example.nick.medminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class splash extends AppCompatActivity {

    Button btn_edit_profile, btn_view_reminder, btn_create_reminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        btn_edit_profile = (Button) findViewById(R.id.edit_profile_button);
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash.this, CreateUserProfileActivity.class);
                startActivity(intent);
            }
        });
        /*btn_view_reminder = (Button) findViewById(R.id.btn_view_reminder);
        btn_view_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash.this, viewReminder.class);
                startActivity(intent);
            }
        });*/
        btn_create_reminder = (Button) findViewById(R.id.new_reminder_button);
        btn_create_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(splash.this, createReminder.class);
                startActivity(intent);
            }
        });
    }
}