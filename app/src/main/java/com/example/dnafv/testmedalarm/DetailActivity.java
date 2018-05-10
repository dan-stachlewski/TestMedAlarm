package com.example.dnafv.testmedalarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dnafv.testmedalarm.model.DataItem;
import com.example.dnafv.testmedalarm.sampleData.SampleDataProvider;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //To get to the Intent that launched the activity - call the getIntent Method & from
        // that I can get access to getExtras which gives me access to any of the extra values
        // passed in
        //String itemId = getIntent().getExtras().getString(DataItemAdapter.ITEM_ID_KEY);
        //The ItemId gives me access to the the selected dataItems attributes from the DataStore
        // - which is the Sample Data Provider - the staticMap named DataItemMap
        //DataItem item = SampleDataProvider.dataItemMap.get(itemId);

        //Now when we receive the intent from the DataItemAdapter we call the getParcelable Method
        // instead of using StringId
        DataItem item = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KEY);
        if (item != null) {
            Toast.makeText(this, "Received item " + item.getName(), Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "We didn't receive any data for " + item.getName(), Toast.LENGTH_SHORT).show();

        }

    }
}
