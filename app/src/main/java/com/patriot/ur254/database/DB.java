package com.patriot.ur254.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dennis on 18/06/17.
 */

public class DB {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TeamUhuru";

    public static final String KEY_ROWID_C = "id";
    public static final String KEY_NAME_C = "name";
    public static final String KEY_NUMBER_C = "number";
    public static final String KEY_INVITED = "isInvited";
    public static final String KEY_IMAGE = "contactimage";
    public static final String KEY_USER = "TeamUhuru";
    public static final String KEY_BACKED_UP = "isBackedUp";

    private static final String DATABASE_C = "contacts";
    private static final String DATABASE_CREATE_C = "CREATE TABLE IF NOT EXISTS " + DATABASE_C
            + " ("
            + KEY_ROWID_C + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME_C + " TEXT NOT NULL, "
            + KEY_NUMBER_C + " TEXT, "
            + KEY_INVITED + " TEXT, "
            + KEY_IMAGE + " TEXT, "
            + KEY_USER + " TEXT NOT NULL, "
            + KEY_BACKED_UP + " TEXT NOT NULL);";

    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;

    public DB(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_C);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_C);
            onCreate(db);
        }
    }

    public void ReadContacts() {
        open();
        sqLiteDatabase.execSQL(DATABASE_CREATE_C);
    }

    public DB open() throws SQLiteException {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public boolean isCreated() {
        if (sqLiteDatabase != null) {
            return sqLiteDatabase.isOpen();
        }

        return false;
    }

    public void close() {
        databaseHelper.close();
        sqLiteDatabase.close();
    }

    public long NewName(String number, String name) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME_C, name);
        ////Log.d("updatedb", number+" : "+name);
        return sqLiteDatabase.update(DATABASE_C, values, KEY_NUMBER_C + " = ?", new String[]{number});

    }

    public long NewImage(String number, String image) {
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, image);
        return sqLiteDatabase.update(DATABASE_C, values, KEY_NUMBER_C + " = ?", new String[]{number});

    }

    public boolean checkforallContacts(String name, String number, String image, String status, String isuser, String backup) {
        String query = "SELECT * FROM " + DATABASE_C + " WHERE number=" + number + "";
        Cursor cs = sqLiteDatabase.rawQuery(query, null);

        if (cs.getCount() <= 0) {

            createforallContacts(name, number, image, status, isuser, backup);
            cs.close();
            return false;
        } else {
            cs.close();
            return true;
        }

    }

    public Cursor getAllContacts() {
        String getquery = "select * from " + DATABASE_C + " order by " + KEY_USER + " DESC, " + KEY_NAME_C + " ASC";
        return sqLiteDatabase.rawQuery(getquery, null);
    }

    private long createforallContacts(String name, String number, String invited, String notified, String isuser, String backup) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME_C, name);
        cv.put(KEY_NUMBER_C, number);
        cv.put(KEY_INVITED, invited);
        cv.put(KEY_IMAGE, notified);
        cv.put(KEY_USER, isuser);
        cv.put(KEY_BACKED_UP, backup);
        return sqLiteDatabase.insert(DATABASE_C, null, cv);
    }

}
