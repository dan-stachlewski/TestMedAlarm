package com.example.dnafv.testmedalarm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    /*
     * I need 2 constants:
     * 1.The name of the DB File
     * 2.Current Version of the DB Structure
     */
    public static final String DB_FILE_NAME = "nadias.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Call the SQL_CREATE function to create the DB Tables - call the stmnt for each table if
        // there are >1
        db.execSQL(ItemsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //This is used to upgrade the database if you want to wipe the apps data & start again
        //therefore it will delete the current database & call the onCreate to generate a NEW
        // database instance with all it's tables.
        db.execSQL(ItemsTable.SQL_DELETE);
        onCreate(db);
    }
}
