//package com.example.nick.medminder;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class userSelect extends AppCompatActivity {
//
//    Button btn_user_temp, btn_user_temp2, btn_user_temp3, btn_user_temp4;
//    Button btn_user_create;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_select);
//
//        btn_user_temp = (Button) findViewById(R.id.btn_user_temp);
//        btn_user_temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(userSelect.this, splash.class);
//                Toast toast = Toast.makeText(getApplicationContext(), "Welcome User 1*!", Toast.LENGTH_LONG);
//                ViewGroup group = (ViewGroup) toast.getView();
//                TextView msg = (TextView) group.getChildAt(0);
//                msg.setTextSize(32);
//                toast.show();
//                startActivity(intent);
//            }
//        });
//        btn_user_create = (Button) findViewById(R.id.btn_user_create);
//        btn_user_create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(userSelect.this, createUser.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_user_temp2 = (Button) findViewById(R.id.btn_user_temp2);
//        btn_user_temp2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Please select User 1*", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
//        btn_user_temp3 = (Button) findViewById(R.id.btn_user_temp3);
//        btn_user_temp3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Please select User 1*", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
//        btn_user_temp4 = (Button) findViewById(R.id.btn_user_temp4);
//        btn_user_temp4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Please select User 1*", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//    }
//}
