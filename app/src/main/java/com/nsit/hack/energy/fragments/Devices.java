package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.nsit.hack.energy.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aman on 13/3/16.
 */
public class Devices extends Fragment {

    TextView appPow, reactPow;
    RequestQueue requestQueue;

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
                startActivity(i);
            }
        });


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

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                150000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }
}
