package com.nsit.hack.energy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nsit.hack.energy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aman on 13/3/16.
 */
public class RunningDevicesAdapter extends RecyclerView.Adapter<RunningDevicesAdapter.ViewHolder> {
    private JSONArray dataSet;


    public RunningDevicesAdapter(JSONArray dataSet) {
        this.dataSet = dataSet;
    }

    public void setData(JSONArray dataSet) {
        this.dataSet = dataSet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView deviceTitle, appPow, reactPow;
        public ViewHolder(View itemView) {
            super(itemView);
            deviceTitle = (TextView)itemView.findViewById(R.id.device_title);
            appPow = (TextView) itemView.findViewById(R.id.device_app_power);
            reactPow = (TextView) itemView.findViewById(R.id.device_react_power);
        }
    }

    @Override
    public RunningDevicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_device_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        JSONObject data = null;
        try {
            data = dataSet.getJSONObject(position);
            holder.deviceTitle.setText("Device "+position+": "+((data.getString("name").trim().equals("")) ? "<No Title>" : data.getString("name").trim()));
            holder.appPow.setText("Apparent Power: "+data.getString("apparent_powl").trim());
            holder.reactPow.setText("Reactive Power: "+getInitial(data.getString("reactive_pow")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return dataSet.length();
    }

    public String getInitial(String host) {

        String initial = "X";
        //Log.d("adapter","Hostname: "+host);
        if (host.startsWith("www.")) {
            initial = host.substring(4,5).toUpperCase();
        } else {
            initial = host.substring(0,1).toUpperCase();
        }
        //Log.d("adapter","initial: "+initial);
        return initial;
    }
}

