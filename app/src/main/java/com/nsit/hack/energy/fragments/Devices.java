package com.nsit.hack.energy.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.nsit.hack.energy.App;
import com.nsit.hack.energy.R;
import com.nsit.hack.energy.activities.AddNewDevice;
import com.nsit.hack.energy.adapters.RunningDevicesAdapter;
import com.nsit.hack.energy.network.VolleySingleton;
import com.nsit.hack.energy.utils.SharedPrefs;
import com.nsit.hack.energy.utils.ShowMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aman on 13/3/16.
 */
public class Devices extends Fragment {

    TextView appPow, reactPow;
    RequestQueue requestQueue;
    RunningDevicesAdapter adapter;

    RecyclerView recyclerView;

    String json;

    RecyclerView.LayoutManager layoutManager;

    public Devices() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_devices, container, false);

        Button btnAddDevices = (Button) rootView.findViewById(R.id.add_new_device);

        requestQueue = VolleySingleton.getmInstance().getRequestQueue();

        appPow = (TextView) rootView.findViewById(R.id.tv_app_pow);
        reactPow = (TextView) rootView.findViewById(R.id.tv_react_pow);

        btnAddDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(App.getAppContext(), AddNewDevice.class);
                startActivityForResult(i, 1);
            }
        });



        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_running_devices_list);

        populateRecycler();
        updateDeviceList();
        return rootView;

    }

    private void updateDeviceList() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://www.iot.net.in/homegrid/slice_root_new.php", (String) null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(4);
                    String voltage = jsonObject.getString("voltage_v");
                    double vol = Double.parseDouble(voltage);
                    double curr = Double.parseDouble(jsonObject.getString("current_i"));

                    appPow.setText("Apparent Power\n"+(vol*curr)+" VA");
                    reactPow.setText("Reative Power\n0 VAR");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ShowMessage.toast("Oops something went wrong...");
            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                150000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    public void populateRecycler() {
        json = SharedPrefs.getPrefs("jsonDevices","[]");

        if(json.equals("[]")) {
            ShowMessage.toast("No devices configured");
        } else {
            try {
                JSONArray jsonArray = new JSONArray(json);

                adapter = new RunningDevicesAdapter(jsonArray);
                layoutManager = new LinearLayoutManager(App.getAppContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                ShowMessage.toast("No combination found for configured devices");

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                populateRecycler();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
