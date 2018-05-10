package com.example.dnafv.testmedalarm.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.dnafv.testmedalarm.R;
import com.example.dnafv.testmedalarm.model.DataItem;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class JSONHelper {

    private static final String FILE_NAME = "menuitems.json";
    public static final String TAG = "JSONHelper";

    public static boolean exportToJSON(Context context, List<DataItem> dataItemList) {

        DataItems menuData = new DataItems();
        menuData.setDataItems(dataItemList);

        Gson gson = new Gson();
        String jsonString = gson.toJson(menuData);
        Log.i(TAG, "exportToJSON: " + jsonString);

        FileOutputStream fileOutputStream = null;
        File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);

        //If successful it will return true otherwise false w an error msg - make sure
        // output stream closed

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Close the file output stream
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }


    public static List<DataItem> importFromJSON(Context context) {

        //Read the file to import
        FileReader reader = null;

        try {
            File file = new File(Environment.getExternalStorageDirectory(), FILE_NAME);
            reader = new FileReader(file);

            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(reader, DataItems.class);
            return dataItems.getDataItems();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static List<DataItem> importFromResource(Context context) {

        //Read the file to import
        InputStreamReader reader = null;
        InputStream inputStream = null;

        try {
            inputStream = context.getResources().openRawResource(R.raw.menuitems);
            reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(reader, DataItems.class);
            return dataItems.getDataItems();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class DataItems {
        List<DataItem> dataItems;

        public List<DataItem> getDataItems() {
            return dataItems;
        }

        public void setDataItems(List<DataItem> dataItems) {
            this.dataItems = dataItems;
        }
    }
}
