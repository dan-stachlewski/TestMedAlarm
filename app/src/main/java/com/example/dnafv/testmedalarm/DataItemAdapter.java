package com.example.dnafv.testmedalarm;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dnafv.testmedalarm.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//This code is very simailar to the DataItemAdapter for a ListView - its broken into smaller pieces
// for easier maintenance

public class DataItemAdapter extends RecyclerView.Adapter<DataItemAdapter.ViewHolder> {

    private List<DataItem> mItems;
    private Context mContext;

    public DataItemAdapter(Context context, List<DataItem> items) {
        this.mContext = context;
        this.mItems = items;
    }

    //This method is called auto by the adapter each time it needs a NEW visual rep of a data item
    @Override
    public DataItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //It inflates the list_item.xml layout file, it retrieves a view that is wrapped in an
        // instance of the ViewHolder class and returns the object
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    //This method is called each time the adapter encounters a NEW dataItem that needs to
    // be displayed - it passes the reference to the ViewHolder and the position of the data item
    // in the collection. The Job of the OnBindViewHolder is to take that data object & display its
    // values
    @Override
    public void onBindViewHolder(DataItemAdapter.ViewHolder holder, int position) {
        DataItem item = mItems.get(position);

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
        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.itemNameText);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

}
