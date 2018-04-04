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
//public class editUser extends AppCompatActivity {
//
//    Button euser_btn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_user);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        euser_btn = (Button) findViewById(R.id.euser_btn);
//        euser_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(editUser.this, splash.class);
//                Toast toast = Toast.makeText(getApplicationContext(), "PLACEHOLDER! User editing not yet implemented!", Toast.LENGTH_LONG);
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
