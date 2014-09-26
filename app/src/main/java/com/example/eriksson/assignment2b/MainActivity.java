package com.example.eriksson.assignment2b;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    ContactAdapter contactAdapter;
    ContactDbHelper contactDbHelper;
    Cursor contactCursor;

    static int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactDbHelper = new ContactDbHelper(this);
        // Hämtar all data från databasen och kopplar till listan
        contactCursor = contactDbHelper.get();
        // Koppla listan till kontaktAdaptern
        contactAdapter = new ContactAdapter(this, contactCursor, false);

        // Kopplar adaptern till listan
        setListAdapter(contactAdapter);
        getListView().setOnItemLongClickListener(this);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Hämtar ut data från listan
        Cursor cursor = (Cursor) contactAdapter.getItem(position);
        // Hämtar ut data med kolumindex och skickar in det i våran intent
        Intent editContact = new Intent(this,ContactDetails.class);
        editContact.putExtra("name",cursor.getString((cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_NAME))));
        editContact.putExtra("age",cursor.getString((cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_AGE))));
        editContact.putExtra("description",cursor.getString((cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_DESCRIPTION))));
        editContact.putExtra("url",cursor.getString((cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_URL))));
        // Hämtar ut databasid och skickar med det i intent
        editContact.putExtra("id", id);
        // starta aktivitet och väntar på svar
        startActivityForResult(editContact, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Uppdaterar databasen när användaren om användaren har uppdaterat
        contactCursor = contactDbHelper.get();
        contactAdapter.changeCursor(contactCursor);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public int getSelectedItemPosition() {
        return super.getSelectedItemPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_addContact) {
            Intent addContact = new Intent(this, AddContact.class);
            startActivityForResult(addContact, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        currentId = (int)id;
        // Skapar en dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("").setTitle(R.string.delete_contact);

        // Skapar en knappen och en lyssnare för att radera o databasen.
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                contactDbHelper.delete(currentId);
                contactCursor = contactDbHelper.get();
                contactAdapter.changeCursor(contactCursor);
                contactAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.cancel,null);

        // Skapar dialogen och visar
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}
