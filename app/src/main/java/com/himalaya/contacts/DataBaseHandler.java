package com.himalaya.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper {
    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "contact";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create the table
        // this sytax will create the table contact
        // table has the fields id, name, email
        // id is autoincrement that means you dont need to worry to put the id
        // it will increase the id value by one itself
        // primary means duplicate id is ignored
        String SQL = "CREATE TABLE contact (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, phonenumber TEXT)";
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDatabaseVersion, int dataBaseVersion) {
        // delete the old table
        // create the new table
        sqLiteDatabase.delete("contact", null, null);
        onCreate(sqLiteDatabase);
    }
    // CRUD operation
    // create read update delete

    public Contact getContact(String id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("contact", null, "id = ?", new String[]{id}, null, null, null);
        String ids = cursor.getString(0);
        String name = cursor.getString(1);
        String phonenumber = cursor.getString(2);
        String email = cursor.getString(3);
        Contact contacts = new Contact(ids, name, phonenumber,email);
        db.close();
        return contacts;

    }

    public List<Contact> getAllContact() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Contact> contactList = new ArrayList<>();
        Cursor cursor = db.query("contact", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String ids = cursor.getString(0);
            String name = cursor.getString(1);
            String phonenumber = cursor.getString(2);
            String email = cursor.getString(3);
            Contact contacts = new Contact(ids, name, phonenumber, email);
            contactList.add(contacts);
        }
        db.close();
        return contactList;

    }

    public void saveContact(Contact contacts) {
        // we need to save the data
        // hence initialize the writable type of the database
        SQLiteDatabase db = this.getWritableDatabase();
        // content values is needed to save the data
        // all the data we are going to put in the database is first put in the contentvalues
        // then we put the content values in the table
        ContentValues cv = new ContentValues();
        cv.put("name", contacts.name);
        cv.put("phonenumber", contacts.phonenumber);
        cv.put("email", contacts.email);
        // finally insert the content values in the table
        long result = db.insert("contact", null, cv);
        Log.d("DataBaseHandler", " result = " + result);
        db.close();
    }

    public void deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("contact", "id = ?", new String[]{id});
        db.close();
    }
}

