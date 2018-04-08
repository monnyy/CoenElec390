//package com.example.nick.medminder;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import java.util.ArrayList;
//
//public class viewReminder extends AppCompatActivity {
//
//    reminderDB db;
//    ArrayAdapter<String> adapter;
//    ListView lstTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_reminder);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        db = new reminderDB(this);
//        lstTask = (ListView)findViewById(R.id.lv_view_reminder);
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