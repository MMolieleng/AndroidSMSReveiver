package com.database;

import com.messagereader.MessageObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vodacominnovationpark on 3/2/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final String TAG = "PostDatabase";

    // Database Specific Details

    // If you change the database schema, you must increment the database version.
    private static final int DB_VERSION = 1;
    // DB Name, same is used to name the sqlite DB file
    private static final String DB_NAME = "test_db";

    // `messages` table details

    public static final String TABLE_MESSAGES = "messages";
    public static final String ID = "id";
    public static final String ADDED_DATE = "added_date";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";


    private static final String CREATE_TABLE_POSTS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES
                    + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ADDED_DATE + " TEXT NOT NULL, "
                    + STATUS + " TEXT NOT NULL, "
                    + MESSAGE + " TEXT NOT NULL);";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Called when the database is created for the
        // first time. This is where the creation of
        // tables and the initial population of the tables should happen.

        db.execSQL(CREATE_TABLE_POSTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Called when the database needs to be upgraded.
        // The implementation should use this method to
        // drop tables, add tables, or do anything else
        // it needs to upgrade to the new schema version.

        Log.w(TAG, "Upgrading database. Existing contents will be lost. ["
                           + oldVersion + "] -> [" + newVersion + "]");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);

        // Create after dropping
        onCreate(db);
    }


    public boolean insertMessage (String message) {

        // set the format to sql date time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("message", message);
        contentValues.put("status", "Pending");
        contentValues.put("added_date", dateFormat.format(date));
        db.insert(TABLE_MESSAGES, null, contentValues);
        return true;
    }

    public ArrayList<MessageObj> getAllMessages() {
        ArrayList<MessageObj> array_list = new ArrayList<MessageObj>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM "+TABLE_MESSAGES, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            MessageObj msg = new MessageObj();

            msg.setAdded_date(res.getString(res.getColumnIndex("added_date")));
            msg.setMessage(res.getString(res.getColumnIndex("message")));
            msg.setStatus(res.getString(res.getColumnIndex("status")));
            array_list.add(msg);
            res.moveToNext();
        }
        return array_list;
    }
}
