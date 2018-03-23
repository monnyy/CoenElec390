package com.example.nick.medminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CreateUserProfileActivity extends AppCompatActivity {

    Button create_user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user_profile);

        create_user_profile = (Button) findViewById(R.id.save_profile_button);
        create_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateUserProfileActivity.this, splash.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
            }
        });
    }
}