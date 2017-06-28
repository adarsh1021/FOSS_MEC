package com.infinitystudios.foss_mec;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Adarsh on 28-06-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "foss.db";
    private static final String TABLE_EVENTS = "events";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_IMAGEURL = "imageurl";
    private static final String COLUMN_TYPE = "type";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_EVENTS + "(" +
                COLUMN_ID + " INTEGER, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMAGEURL + " TEXT, " +
                COLUMN_TYPE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EVENTS+";");
        onCreate(db);
    }

    // Add new row to database
    public void addEvent(Event event) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, event.get_id());
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_IMAGEURL, event.getImageURL());
        values.put(COLUMN_TYPE, event.getType());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.close();
    }

    // Update task details`
    public void updateTask(Event event) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, event.get_id());
        values.put(COLUMN_TITLE, event.getTitle());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_IMAGEURL, event.getImageURL());
        values.put(COLUMN_TYPE, event.getType());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_EVENTS, values, COLUMN_ID+"="+event.get_id(), null);
        db.close();
    }

    // Delete task from DB
    public void deleteTask(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_EVENTS+" WHERE "+COLUMN_ID+"="+id);
        Log.v("Deleted task with id", String.valueOf(id));
    }

    // Print DB as string
    public String databaseToString(String type){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS + " WHERE "+ COLUMN_TYPE +"=\""+type+"\";";

        // Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        // move to first row
        c.moveToFirst();
        while (!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("title"))!=null) {
                dbString+=c.getString(c.getColumnIndex(COLUMN_ID))+",";
                dbString+=c.getString(c.getColumnIndex(COLUMN_TITLE))+",";
                dbString+=c.getString(c.getColumnIndex(COLUMN_DESCRIPTION))+",";
                dbString+=c.getString(c.getColumnIndex(COLUMN_IMAGEURL))+",";
                dbString+=c.getString(c.getColumnIndex(COLUMN_TYPE))+",";
                dbString+=";";
                c.moveToNext();
            }

        }
        db.close();
        return dbString;
    }

    String getMaxId() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT MAX("+COLUMN_ID+") FROM " + TABLE_EVENTS +";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        return  c.getString(0);
    }
}
