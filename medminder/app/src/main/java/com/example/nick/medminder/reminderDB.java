package com.example.nick.medminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Nick on 2018-03-02.
 */

public class reminderDB extends SQLiteOpenHelper {

    private static final String dbName = "ReminderTable";
    private static final int ver = 1;
    private static final String dbTable = "Task";
    public static final String dbCol = "tName";

    public reminderDB(Context context) {
        super(context, dbName, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = String.format("Create Table %s", dbTable);
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        String q = String.format("Delete Table %s", dbTable);
        db.execSQL(q);
        onCreate(db);
    }

    public void insertNewMed(String med) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbCol, med);
        db.insertWithOnConflict(dbTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteMed(String med) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(dbTable, dbCol + " - ?", new String[]{med});
        db.close();
    }

    public ArrayList<String> getMedList() {
        ArrayList<String> medList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(dbTable, new String[]{dbCol}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex(dbCol);
            medList.add(cursor.getString(i));
        }
        cursor.close();
        return medList;
    }
}