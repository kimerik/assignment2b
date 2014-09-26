package com.example.eriksson.assignment2b;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class AddContact extends Activity implements View.OnClickListener {

    // Skapar all variablar som vi behöver
    ContactAdapter ContactAdapter;
    ContactDbHelper contactDbHelper;
    Button addButton;
    EditText editName;
    EditText editUrl;
    EditText editAge;
    EditText editDescription;
    ImageView imgView;
    Button updateImageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        contactDbHelper = new ContactDbHelper(this);
        addButton = (Button)findViewById(R.id.addContactBtn);
        addButton.setOnClickListener(this);

        updateImageBtn = (Button)findViewById(R.id.updateImageBtn);
        updateImageBtn.setOnClickListener(this);

        imgView = (ImageView)findViewById(R.id.addImgView);

        // Hämtar alla vywer så att vi sedan kan hämta data
        editName = (EditText)findViewById(R.id.editName);
        editUrl = (EditText)findViewById(R.id.editImg);
        editAge = (EditText)findViewById(R.id.editAge);
        editDescription = (EditText)findViewById(R.id.editDescription);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.updateImageBtn){
            // håller bilden
            if(editUrl.getText().toString().length() != 0)
            Picasso.with(this).load(editUrl.getText().toString()).placeholder(R.drawable.ic_launcher).resize(460, 325).into(imgView);
        }
        else if(id == R.id.addContactBtn){

        //Skapar en Kontakt och fyller den med data
        ContactsModel model = new ContactsModel(editName.getText().toString(), editUrl.getText().toString(),
        Integer.parseInt(editAge.getText().toString()), editDescription.getText().toString());

        // Kör en insert och skickar in en model till databasenAdaptern
        contactDbHelper.insert(model);
        setResult(1, getIntent());
        finish();
        }

    }
}
