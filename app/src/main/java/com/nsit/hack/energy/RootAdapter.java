package com.nsit.hack.energy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {

    private List<RootItem> itemsData;

    public RootAdapter(List<RootItem> itemsData) {
        this.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_home_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        String title_string;
        title_string = itemsData.get(position).getTitle();
        viewHolder.title.setText(title_string);
        viewHolder.imageView.setImageResource(itemsData.get(position).getImage());
        viewHolder.reading.setText(itemsData.get(position).getReading());
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView title;
        public ImageView imageView;
        public TextView reading;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.title);
            imageView = (ImageView) itemLayoutView.findViewById(R.id.image);
            reading = (TextView) itemLayoutView.findViewById(R.id.reading);
        }
    }
}