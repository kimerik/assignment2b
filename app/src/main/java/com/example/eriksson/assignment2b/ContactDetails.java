package com.example.eriksson.assignment2b;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class ContactDetails extends Activity implements View.OnClickListener {
    // Skapa alla vyer
    EditText editName;
    EditText editUrl;
    EditText editAge;
    EditText editDescription;
    ImageView img;
    Button updateBtn;
    ContactDbHelper contactDbHelper;
    // Id som kommer hålla databasid för en kontakt
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        // Skapar en instans av databasen
        contactDbHelper = new ContactDbHelper(this);

        // Hämtar ut data om kontakten
        String name = getIntent().getStringExtra("name");
        String description = getIntent().getStringExtra("description");
        String age = getIntent().getStringExtra("age");
        String imgUrl = getIntent().getStringExtra("url");
        id = getIntent().getLongExtra("id",0);

        // Sätter texten i alla fällt
        editName = (EditText)findViewById(R.id.editName);
        editName.setText(name);

        editDescription = (EditText)findViewById(R.id.editDescription);
        editDescription.setText(description);

        editAge = (EditText)findViewById(R.id.editAge);
        editAge.setText(age);

        editUrl = (EditText)findViewById(R.id.editUrl);
        editUrl.setText(imgUrl);

        //  Skapar knapp som används för uppdatering av databasen
        updateBtn = (Button)findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);

        // håller bilden
        img = (ImageView)findViewById(R.id.editImg);
        Picasso.with(this).load(editUrl.getText().toString()).placeholder(R.drawable.ic_launcher).resize(460, 325).into(img);
    }


    @Override
    public void onClick(View v) {
        // Skapar en model och hämtar all fomulärdata
        ContactsModel model = new ContactsModel(id ,editName.getText().toString(),editUrl.getText().toString(),
        Integer.parseInt(editAge.getText().toString()),editDescription.getText().toString());

        // Anropar metoden och sedan skickar in modelen till databasen för uppdatering
        contactDbHelper.update(model);
        // sätter relultat id och till vilken intent
        setResult(0,getIntent());
        // Avslutar denna intenten
        finish();
    }
}
