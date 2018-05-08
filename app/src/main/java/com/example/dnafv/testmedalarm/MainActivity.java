package com.example.dnafv.testmedalarm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dnafv.testmedalarm.model.DataItem;
import com.example.dnafv.testmedalarm.sampleData.SampleDataProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //Initialise the variable used to change the Text within the TextView in the MainActivity
    // Activity activity_main.xml > content_main.xml
    //TextView tvOut;
    //Create a reference to the list version of the data as a persistent field
    //Get a refrence to that list by calling the static field of the sampleDataProvider

    //We are working with a single Object made
    //Now we have all that data avaliable to the Activity Class
    // as we are working with simple string sets made up of item data
    List<DataItem>dataItemList = SampleDataProvider.dataItemList;


    //New List made up of simple string values & use the foreach loop to cycle through each datum
    //in the array
    List<String>itemNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //The tells the framework to open the activity_main.xml file file associated with the
        // MainActivity.java file
        setContentView(R.layout.activity_main);
        //This tells the framework to load the toolbar named toolbar in the activity_main.xml file
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //This tells the framework to load the floating action button named fab in the
        // activity_main.xml file
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //We can now Test the dataItem Class:
        /**
         * Declare a new single instance of the dataItem class
         * the Object will be named item
         * use a version of the Constructor method DataItem() that receives all of the values
         * DataItems have been successfully saved and displayed in the app via the DataItem Class
         * */
        //DataItem item = new DataItem(null, "My menu item", "a category", "a description", 1, 9.95, "apple_pie.jpg");


        //This links the variable tvOut to the TextView within the MainActivity
        // Activity activity_main.xml > content_main.xml
        //tvOut = (TextView)findViewById(R.id.tvOut);
        //This changes the text being displayed from Hello World! to Ready to code in the TextView
        // within the MainActivity Activity activity_main.xml > content_main.xml
        //tvOut.setText("Ready to code!");
        //tvOut.setText("");

        //To sort the data alphabetically
        //Sorting Complext Objects - need an insance of Java's comparator interface

        /*Collections.sort(dataItemList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        */

        //Loop thru the data with a foreach loop
        //Using an instance of the DataItem class called item that I will get from the dataItemList
        for (DataItem item: dataItemList) {
            //tvOut.append(item.getName() + "\n");
            itemNames.add(item.getName());
        }
        //B/c we are using simple Strings I can sort thru them using Collections
        // whih are now ready for display
        Collections.sort(itemNames);

        //In order to display data in a list view we need to use a class called an
        // Adapter = ArrayAdapter whose job is to cycle thru the adapter and then as needed
        // create View Objects to represent List Items - rows in the list & then pass the data
        // to those List Items so they are displayed in the Activity
        //At the mo the ArrayAdapter is managing String values, named adapter & will instantiate
        // it with a call to the ArrayAdapters Constructor Method:
        // Context = this (for current activity), layout resorce id (layout included by android), pass in dataObjects
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemNames);
        //Now we have to bind the adapter to the list view with a ref to the listView Object
        ListView listView = (ListView) findViewById(android.R.id.list);
        //Bind the 2 objects together
        listView.setAdapter(adapter);
    }

    //This method loads the menu that sits at the top of the device
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
