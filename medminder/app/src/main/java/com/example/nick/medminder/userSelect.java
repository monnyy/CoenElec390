package com.example.nick.medminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class userSelect extends AppCompatActivity {

    Button btn_user_temp, btn_user_temp2, btn_user_temp3, btn_user_temp4;
    Button btn_user_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        btn_user_temp = (Button) findViewById(R.id.login_button2);
        btn_user_temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userSelect.this, splash.class);
                startActivity(intent);
            }
        });
    }
}
