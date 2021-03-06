package com.example.dnafv.testmedalarm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dnafv.testmedalarm.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//This code is very simailar to the DataItemAdapter for a ListView - its broken into smaller pieces
// for easier maintenance

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    public static final String ITEM_ID_KEY = "item_id_key";
    public static final String ITEM_KEY = "item_key";
    private List<DataItem> mItems;
    private Context mContext;

    //On change Listener to keep an eye out for app changes made by the user
    private SharedPreferences.OnSharedPreferenceChangeListener prefsListener;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    //This method is called auto by the adapter each time it needs a NEW visual rep of a data item
    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
        boolean grid = settings.getBoolean(mContext.getString(R.string.pref_display_grid), false);

        prefsListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                //The below code will log the message for us showing which prefs changed
                Log.i("preferences", "onSharedPreferenceChanged:" + key);
            }
        };
        //Register listener with appropriate listener set
        settings.registerOnSharedPreferenceChangeListener(prefsListener);

        //This code checks which layout is being asked for and provides it in the itemView - its
        // a ternary operator shorthand.
        int layoutId = grid ? R.layout.grid_item : R.layout.list_item;


        LayoutInflater inflater = LayoutInflater.from(mContext);
        //It inflates the list_item.xml layout file, it retrieves a view that is wrapped in an
        // instance of the ViewHolder class and returns the object
        View itemView = inflater.inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }


    //This method is called each time the adapter encounters a NEW dataItem that needs to
    // be displayed - it passes the reference to the ViewHolder and the position of the data item
    // in the collection. The Job of the OnBindViewHolder is to take that data object & display its
    // values - this is where we supply the data we want to display to the user
    // & set up event handlers
    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        final DataItem item = mItems.get(position);

        try {
            //We are getting tvName & imageView from the holder object
            holder.tvName.setText(item.getName());
            String imageFile = item.getImage();
            InputStream inputStream = mContext.getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Add onClickListener to the View that opens a Single DataItem when chosen from the list
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "You selected " + item.getName(), Toast.LENGTH_SHORT).show();

                //NOw have a UUID to pass b/w activities
                //String itemId = item.getItemId();
                //Now instead of passing the PK, I am passing the entire dataObject
                Intent intent = new Intent(mContext, DetailActivity.class);
                //Pass the UUID that Id's each item
                intent.putExtra(ITEM_KEY, item);
                //The below code launches the DetailsActivity displaying an Item's details
                mContext.startActivity(intent);
            }
        });
        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "You long clicked " + item.getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    //The below method returns the # of dataItems in the collection
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //The ViewHolder Class extends the RecyclerView.ViewHolder class & is responsible for setting
    // up the bindings to the views in the conten_main.xml layout files
    public static class ViewHolder extends RecyclerView.ViewHolder {


        //This retrieved data is being saved as public fields of the ViewHolder ClassW
        public TextView tvName;
        public ImageView imageView;
        //The view that is displayed when a list item is clicked - this is a view that represents a
        // single data item
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            //This view ref is available to the rest of the adapter
            mView = itemView;

        }
    }

}
