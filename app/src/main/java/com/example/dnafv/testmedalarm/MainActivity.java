package com.example.dnafv.testmedalarm;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnafv.testmedalarm.model.DataItem;
import com.example.dnafv.testmedalarm.sampleData.SampleDataProvider;
import com.example.dnafv.testmedalarm.utils.JSONHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_REQUEST = 1001;
    public static final String MY_GLOBAL_PREFS = "my_global_prefs";

    private static final int REQUEST_PERMISSION_WRITE = 1001;
    public static final String FILE_NAME = "lorem_ipsum.text";
    private boolean permissionGranted;
    private static final String TAG = "MainActivity";

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

        checkPermissions();

        Collections.sort(dataItemList, new Comparator<DataItem>() {
            @Override
            public int compare(DataItem o1, DataItem o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

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

        //Loop thru the data with a foreach loop
        //Using an instance of the DataItem class called item that I will get from the dataItemList
        //for (DataItem item: dataItemList) {
            //tvOut.append(item.getName() + "\n");
            //itemNames.add(item.getName());
        //}
        //B/c we are using simple Strings I can sort thru them using Collections
        // whih are now ready for display
        //Collections.sort(itemNames);

        //In order to display data in a list view we need to use a class called an
        // Adapter = ArrayAdapter whose job is to cycle thru the adapter and then as needed
        // create View Objects to represent List Items - rows in the list & then pass the data
        // to those List Items so they are displayed in the Activity
        //At the mo the ArrayAdapter is managing String values, named adapter & will instantiate
        // it with a call to the ArrayAdapters Constructor Method:
        // Context = this (for current activity), layout resorce id (layout included by android), pass in dataObjects
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemNames);
        //Now we have to bind the adapter to the list view with a ref to the listView Object

        //We need to create our Custom DataAdapter
        //DataItemAdapterListView adapter = new DataItemAdapterListView(this, dataItemList);
        DataItemAdapter adapter = new DataItemAdapter(this, dataItemList);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean grid = settings.getBoolean(getString(R.string.pref_display_grid), false);


        //ListView listView = (ListView) findViewById(android.R.id.list);
        //The recyclerView is connected to the vrItems RecyclerView in content_main.xml
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvItems);
        if(grid){
            //The 3 will create a display with 3 cols (within the Grid)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }

        //Bind the 2 objects together
        //listView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
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
        switch(item.getItemId()){
            case R.id.action_signin:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, PrefsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_export:
                boolean result = JSONHelper.exportToJSON(this, dataItemList);
                if(result){
                    Toast.makeText(this, "Data Exported", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Export Failed", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_import:
                List<DataItem>dataItems = JSONHelper.importFromResource(this);
                if(dataItems != null){
                    for (DataItem dataItem :
                            dataItems) {
                        Log.i(TAG, "onOptionsItemSelected: " + dataItem.getName());
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SIGNIN_REQUEST) {
            String email = data.getStringExtra(SigninActivity.EMAIL_KEY);
            Toast.makeText(this, "You signed in as " + email, Toast.LENGTH_SHORT).show();

            //We want to save the entered email address in the email field so the user doesn't
            // need to keep adding it again & again
            SharedPreferences.Editor editor = getSharedPreferences(MY_GLOBAL_PREFS,MODE_PRIVATE).edit();
            editor.putString(SigninActivity.EMAIL_KEY, email);
            editor.apply();

        }

    }

    private File getFile(){
        return new File(
                Environment.getExternalStorageDirectory(), FILE_NAME);
    }

    public void onCreateButtonClick(View view){
        if(!permissionGranted){
            checkPermissions();
            return;
        }


        String string = getString(R.string.lorem_ipsum);

        FileOutputStream fileOutputStream = null;
        File file = getFile();

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(string.getBytes());
            Toast.makeText(this, "File written: " + FILE_NAME, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally{
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onReadButtonClick(View view){
        if(!permissionGranted){
            checkPermissions();
            return;
        }
    }

    public void onDeleteButtonClick(View view){
        if(!permissionGranted){
            checkPermissions();
            return;
        }

        File file = getFile();
        if(file.exists()){
            file.delete();
            Toast.makeText(this, "File deleted!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "File doesn't exist!", Toast.LENGTH_SHORT).show();
        }
    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
    }

    // Initiate request for permissions.
    private boolean checkPermissions() {
        //Checks to see if you have the folloiwng permissions or not
        //if either returns false you cant access readable or writable storage
        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            Toast.makeText(this, "This app only works on devices with usable external storage",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_WRITE);
            return false;
        } else {
            return true;
        }
    }

    // Handle permissions result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_WRITE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                    Toast.makeText(this, "External storage permission granted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
