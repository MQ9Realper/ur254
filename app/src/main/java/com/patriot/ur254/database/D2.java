package com.patriot.ur254.database;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by dennis on 18/06/17.
 */

public class D2 {
    private DB dbAdapter;

    public D2(Context context){
        dbAdapter = new DB(context);
    }

    public Cursor getAllContacts() {
        dbAdapter.open();
        return dbAdapter.getAllContacts();
    }

    public long NewName(String number, String name) {
        long count = 0;
        dbAdapter.open();
        count = dbAdapter.NewName(number,name);
        dbAdapter.close();
        return count;

    }

    public long NewImage(String number, String name) {
        long count = 0;
        dbAdapter.open();
        count = dbAdapter.NewImage(number,name);
        dbAdapter.close();
        return count;

    }

    public boolean checkforallContacts(String name, String number, String image, String status, String isuser, String backup) {
        boolean count;
        dbAdapter.open();
        count = dbAdapter.checkforallContacts(name, number, image, status, isuser, backup);
        dbAdapter.close();
        return count;

    }

}
