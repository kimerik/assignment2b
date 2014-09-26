package com.example.eriksson.assignment2b;

import android.provider.BaseColumns;

/**
 * Created by Eriksson on 2014-09-24.
 */
public class ContractContact implements BaseColumns {

    // Kontraktet över hur databastabellerna ska se ut

    public ContractContact(){
    }

    public static abstract class ContactGallery implements BaseColumns{
        public final static String TABLE_NAME = "ContactTable";
        public final static String COLUMN_NAME_URL = "url";
        public final static String COLUMN_NAME_NAME = "name";
        public final static String COLUMN_NAME_AGE = "age";
        public final static String COLUMN_NAME_DESCRIPTION = "description";
    }
}
