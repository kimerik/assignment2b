package com.example.eriksson.assignment2b;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Eriksson on 2014-09-24.
 */
public class ContactAdapter extends CursorAdapter {

    LayoutInflater inflater;
    ViewHolder viewHolder;
    int count;

    public ContactAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Den här körs för varje rad och koppla ihop alla item
        count++;
        viewHolder = new ViewHolder();

        // Flätar in xml till java så att vi kan använda den.
        View view = inflater.inflate(R.layout.row_contact_layout, parent, false);
        // Sätt våran vy till viewHolder så att vi kan återanvända den
        viewHolder.imgUrl = (ImageView) view.findViewById(R.id.rowImgUrl);
        viewHolder.name = (TextView) view.findViewById(R.id.rowName);
        // Sätter en tag så att den håller reda på alla vyer,
        view.setTag(viewHolder);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        viewHolder = (ViewHolder) view.getTag();
        // Hämtar ut vy med taggen och sedan sätter vi data till den.
        viewHolder.name.setText(cursor.getString((cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_NAME))));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(ContractContact.ContactGallery.COLUMN_NAME_URL))).placeholder(R.drawable.ic_launcher).resize(460, 325).into(viewHolder.imgUrl);
    }
     // Våran ViewHolder som håller alla vyer.
    static class ViewHolder {
        public TextView name;
        public ImageView imgUrl;
    }
}
