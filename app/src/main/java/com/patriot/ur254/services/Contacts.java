package com.patriot.ur254.services;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.patriot.ur254.database.D2;
import com.patriot.ur254.utils.G4;
import com.patriot.ur254.utils.SharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by dennis on 18/06/17.
 */

public class Contacts extends IntentService {
    public SharedPreferences loggedinuser;
    public static String filename = "UserName";
    private static final String DATABASE_TABLE_OkoaTime = "contacts";
    public int dbtotal = 0, phonetotal = 0, a, counter, phonecount = 0;

    Boolean internetavailable = false;
    String url, encodedurl, mynumber = "";
    String userphonenumber, key, IMEInumber, ccode;
    String phoneNumber = null;
    String negatephoneNumber = null;
    String serverphoneNumber = null;
    String slashedphoneNumber = null;
    String countrycode = "254";
    SQLiteDatabase DB = null;
    public static String contactname, image_uri;
    String[] names;
    String line;
    String returnedcode;
    Boolean update = true;
    Boolean display = false;

    public Contacts() {
        super("SCnts");
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onHandleIntent(Intent intent) {
        loggedinuser = getSharedPreferences(filename, 0);
        SharedPreference preferences = new SharedPreference(getApplicationContext());
        preferences.putcontactSaved(true);

        DB = this.openOrCreateDatabase(com.patriot.ur254.database.DB.DATABASE_NAME, MODE_PRIVATE, null);
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                //	final String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(
                            PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?",
                            new String[]{contact_id}, null);
                    //int phonecount2=phoneCursor.getCount();
                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        ////////////////////////////////////////////
                        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
                        if (phoneNumber.length() < 8) {
                            phoneNumber = "usiweke kwa db";
                        } else if (phoneNumber.length() > 14) {
                            phoneNumber = "usiweke kwa db";
                        } else {

                            if (phoneNumber.length() > 7) {
                                contactname = phoneCursor.getString(phoneCursor.getColumnIndex(DISPLAY_NAME));
                                image_uri = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                                String firstLetter = String.valueOf(phoneNumber
                                        .charAt(0));
                                // 1. if +254 -> remove +
                                if (firstLetter.equals("+")) {
                                    negatephoneNumber = phoneNumber.substring(1);
                                    slashedphoneNumber = negatephoneNumber;
                                }
                                // 2. if 0722 -> remove 0 add 254
                                else if (firstLetter.equals("0")) {
                                    negatephoneNumber = phoneNumber.substring(1);
                                    if (countrycode.equals(null) || countrycode.length() < 1) {
                                        countrycode = "254";
                                    }

                                    slashedphoneNumber = countrycode + negatephoneNumber;
                                }
                                // 3. If is starts with countrycode do nothing
                                else if (phoneNumber.startsWith(countrycode)) {
                                    slashedphoneNumber = phoneNumber;
                                } else {//Ab phone starts with 861 dont add country code of mycountry
                                    slashedphoneNumber = phoneNumber;
                                }
                                serverphoneNumber = slashedphoneNumber;
                                if (serverphoneNumber.equals(mynumber)) {
                                    //dont add user number
                                } else {
                                    ++phonetotal;
                                    Cursor autocs = null;
                                    Cursor autocstotal = null;
                                    autocs = DB.rawQuery("SELECT * FROM " + DATABASE_TABLE_OkoaTime + " WHERE number=" + serverphoneNumber + "", null);
                                    autocstotal = DB.rawQuery("SELECT * FROM " + DATABASE_TABLE_OkoaTime + "", null);
                                    autocs.moveToFirst();
                                    dbtotal = autocstotal.getCount();

                                    if (autocs.getCount() > 0) {
                                        Bitmap userphoto = null;
                                        if (image_uri != null) {
                                            try {
                                                userphoto = MediaStore.Images.Media.getBitmap(Contacts.this.getContentResolver(), Uri.parse(image_uri));
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                userphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                                byte[] b = baos.toByteArray();
                                                image_uri = G4.encodeToString(b, G4.DEFAULT);
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            isNameNew(contactname, serverphoneNumber, image_uri);
                                            D2 updatename = new D2(this);
                                            updatename.NewImage(serverphoneNumber, image_uri);
                                        }

                                    } else {
                                        D2 contactinfo = new D2(this);
                                        contactinfo.checkforallContacts(contactname, serverphoneNumber, "0", "", "0", "0");
                                        if (image_uri != null) {
                                            try {
                                                Bitmap userphoto = null;
                                                userphoto = MediaStore.Images.Media.getBitmap(Contacts.this.getContentResolver(), Uri.parse(image_uri));
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                userphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                                byte[] b = baos.toByteArray();
                                                image_uri = G4.encodeToString(b, G4.DEFAULT);
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            D2 updatename = new D2(this);
                                            updatename.NewImage(serverphoneNumber, image_uri);
                                        }

                                    }
                                    autocs.close();
                                    autocstotal.close();
                                }
                            }//end if phonenumber > 5

                        }//end if has nonnumeric
                    }
                    phoneCursor.close();

                }

            }
        }

    }

    public void isNameNew(String name, String number, String image) {
        Cursor autocs5 = DB.rawQuery("SELECT * FROM " + DATABASE_TABLE_OkoaTime + " WHERE number=" + number + "", null);
        autocs5.moveToFirst();
        if (autocs5.getCount() > 0) {
            String dbname = autocs5.getString(1);
            String dbnumber = autocs5.getString(2);

            if (name.equals(dbname)) {
            } else {
                String updatedname = dbname + ", " + name;
                a = 0;

                String dbname2 = dbname;
                while (dbname2.contains(",")) {
                    a = dbname2.split(",").length - 1;
                    dbname2 = dbname2.replace(",", "");
                    //dbname = dbname.replace(" ","");
                }

                if (a > 0) {
                    names = dbname.split(",");

                    for (int i = 0; i <= a; i++) {
                        String splitname1 = names[i];
                        String frstlettter = String.valueOf(splitname1.charAt(0));
                        // 1. if space -> remove
                        if (frstlettter.equals(" ")) {
                            splitname1 = splitname1.substring(1);

                        }
                        if (name.equalsIgnoreCase(splitname1) || name.equals("found1stname=dbname")) {
                            name = "found1stname=dbname";
                        } else {
                            //if checking last name and not similar add..
                            if (i == a) {
                                String newdbname = dbname + ", " + name;
                                //DB already contained more than one name
                                //so needed to update after checkin all names separated by comma
                                D2 updatename = new D2(this);
                                updatename.NewName(dbnumber, newdbname);
                            }
                        }
                    }

                } else {
                    //DB doesnt contain more than one name update normally
                    D2 updatename = new D2(this);
                    updatename.NewName(dbnumber, updatedname);
                }


            }
        }
        autocs5.close();
    }

}
