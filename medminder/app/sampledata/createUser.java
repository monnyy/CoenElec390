//package com.example.nick.medminder;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//public class createUser extends AppCompatActivity {
//
//    Button cuser_btn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_user);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        cuser_btn = (Button) findViewById(R.id.cuser_btn);
//        cuser_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(createUser.this, userSelect.class);
//                Toast toast = Toast.makeText(getApplicationContext(), "PLACEHOLDER! User creation not yet implemented!", Toast.LENGTH_LONG);
//                toast.show();
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
