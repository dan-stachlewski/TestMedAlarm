package com.example.dnafv.testmedalarm.database;

/***
 * This class includes a number of constants:
 * Setting the Names of the Tables, names & types of the columns, & other critical information
 */


public class ItemsTable {

    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME = "itemName";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_POSITION = "sortPosition";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE = "image";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_POSITION + " INTEGER," +
                    COLUMN_PRICE + " REAL," +
                    COLUMN_IMAGE + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
    /* Execution of the statements goes into the openHelper Class - this class just sets everything up */
}
