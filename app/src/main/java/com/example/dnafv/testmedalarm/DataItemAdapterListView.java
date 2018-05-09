package com.example.dnafv.testmedalarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnafv.testmedalarm.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//This ArrayAdapter is going to manage <DataItems> rather than simple strings
//We then create a Constructor that will pass in a Java List b/c that's how the data is
// stored at present - 3 args, a context, a resource & a List of data objects

public class DataItemAdapterListView extends ArrayAdapter<DataItem> {

    //We only need to pass in the context and the data in our constructor so we remove the int and
    // pass in the list_item.xml so the Adapter knows which layout it's using, the data is being
    // passed in as an arg objects - we want to save it persistently so we have access to it
    // anytime we need it so we create a var called mDataItems
    List<DataItem>mDataItems;
    //Also need a ref to the Layout Inflater Object to open and read into memory the xml layout file
    LayoutInflater mInflater;

    public DataItemAdapterListView(Context context, List<DataItem> objects) {
        super(context, R.layout.list_item, objects);

        //Assigning our dataItems to the mDataItems var
        mDataItems = objects;
        //Assign the LayoutInflater to mInflater passing in the context where the CONTEXT is the
        // background information the app has access to like layouts, resources, images, strings, etc.
        mInflater = LayoutInflater.from(context);
    }


    //Each time the ArrayAdapter encounters a new dataItem to display it needs to use the method
    // getView. We will be Overriding the method getView - where:
    // position = position of current dataItem in the data set - named mDataItems in this case
    // convertView = ref tool layout
    //
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //The 1st time the layout is referenced it will be null so we assign the list_item.xml
        // file view.
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, parent, false);
        }

        //We then link the DataItems to the TextViews and ImageViews within the list_view.xml
        // activity this will then place the dataItems in the right places
        TextView tvName = (TextView)convertView.findViewById(R.id.itemNameText);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

        //To display data - we know the position of the dataItem in the list of data from the
        // Position arg so we create a ref to DataItem named item, 
        DataItem item = mDataItems.get(position);
        //We then display the items we have retrieved: Item name & Item Image
        tvName.setText(item.getName());

        //Setting the image resources:
        //imageView.setImageResource(R.drawable.apple_pie);
        //To load images dynamically, get the name of the image (string), use the method getImage()
        // , create an input stream and get its ref by calling getContext.getAssets which returns a
        // ref to the assets dir...
        //The below code retrieves the images from the assets folder and displays them in the
        // list view based on the item name and its corresponding string that descries the image
        // name
        InputStream inputStream = null;

        try {
            //Getting the file name from the appropriate data object
            String imageFile = item.getImage();
            //Creating a stream
            inputStream = getContext().getAssets().open(imageFile);
            //Creating a drawable object
            Drawable d = Drawable.createFromStream(inputStream, null);
            //Setting the drawable object as the source of the image view
            imageView.setImageDrawable(d);
            //If we get any exceptions they will be dumped into the console
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }
}
