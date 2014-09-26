package com.example.eriksson.assignment2b;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Eriksson on 2014-09-24.
 */

    public class ContactDbHelper extends SQLiteOpenHelper {

    // Konstanter
    private final static String DATABASE_NAME = "ContactTable.db";
    private final static int DATABASE_VERSION = 4;
    private final static String COMMA_SEP = ",";
    private final static String TEXT_TYPE = " TEXT";

    // Projection av databasen
    String[] projection = {
            ContractContact.ContactGallery._ID,
            ContractContact.ContactGallery.COLUMN_NAME_AGE,
            ContractContact.ContactGallery.COLUMN_NAME_URL,
            ContractContact.ContactGallery.COLUMN_NAME_DESCRIPTION,
            ContractContact.ContactGallery.COLUMN_NAME_NAME
    };

    // Databasen
    SQLiteDatabase db;


    // Strängen för att skapa upp databasen.
    private static final String SQL_CREATE_GALLERY =
            "CREATE TABLE " + ContractContact.ContactGallery.TABLE_NAME + " (" +
                    ContractContact.ContactGallery._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    ContractContact.ContactGallery.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ContractContact.ContactGallery.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    ContractContact.ContactGallery.COLUMN_NAME_AGE + TEXT_TYPE + COMMA_SEP +
                    ContractContact.ContactGallery.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + " )";

    // Strängen för att radera databasen
    private static final String SQL_DELETE_GALLERY =
            "DROP TABLE IF EXISTS " + ContractContact.ContactGallery.TABLE_NAME;

    // Konstrukton
    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Metoden för att skapa databasen
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_GALLERY);
            Log.d("GalleryDbHelper", "Table created version" + DATABASE_VERSION);
        } catch (Exception ex) {
            Log.d("GalleryDbHelper", ex.getMessage());
        }
    }

    // Metoden för att uppdatera databasen
    // Jämnför gamla och nya versionen, ny version så raderar den hela och lägger in en ny
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            try {
                db.execSQL(SQL_DELETE_GALLERY);
                onCreate(db);
            } catch (Exception ex) {
                Log.d("GalleryDbHelper", ex.getMessage());
            }

        }
    }

    // Hämtar datat i databaen med projection
    public Cursor get() {

        db = getReadableDatabase();

        Cursor cursor = db.query(
                ContractContact.ContactGallery.TABLE_NAME,  // The table to query
                projection,              // The columns to return
                null,                    // The columns for the WHERE clause
                null,                    // The values for the WHERE clause
                null,                    // don't group the rows
                null,                    // don't filter by row groups
                null                     // The sort order
        );

        return cursor;
    }

    // Metoden för att sätta in i databasen
    public long insert(ContactsModel contactsModel) {

        // Hämta databas att skriva till
        db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_NAME, contactsModel.getName());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_URL, contactsModel.getImgUrl());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_AGE, contactsModel.getAge());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_DESCRIPTION, contactsModel.getDescription());

        // Få ut ett ID, blir det noll så har något gått fel.
        long id = db.insert(ContractContact.ContactGallery.TABLE_NAME, null, contentValues);

        return id;
    }

    // Radera rad med ett ID
    public void delete(int currentId) {
        db = getWritableDatabase();
        db.delete(ContractContact.ContactGallery.TABLE_NAME, ContractContact.ContactGallery._ID + "=" + currentId, null);
    }

    // Uppdaterar rad i databasen
    public void update(ContactsModel contactsModel) {
       db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractContact.ContactGallery._ID, contactsModel.getId());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_NAME, contactsModel.getName());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_URL, contactsModel.getImgUrl());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_AGE, contactsModel.getAge());
        contentValues.put(ContractContact.ContactGallery.COLUMN_NAME_DESCRIPTION, contactsModel.getDescription());

        db.update(ContractContact.ContactGallery.TABLE_NAME, contentValues , ContractContact.ContactGallery._ID + "=" + contactsModel.getId(), null);

    }
}