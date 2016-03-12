package com.nsit.hack.energy.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nsit.hack.energy.App;
import com.nsit.hack.energy.R;
import com.nsit.hack.energy.network.VolleySingleton;

/**
 * Created by aman on 13/3/16.
 */
public class MainAccess extends Fragment {

    Button triggerRelay;
    EditText ipAddr;
    RequestQueue requestQueue;

    public MainAccess() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragement_main_access, container, false);

        requestQueue = VolleySingleton.getmInstance().getRequestQueue();

        triggerRelay = (Button) rootView.findViewById(R.id.trigger_realy);
        ipAddr = (EditText) rootView.findViewById(R.id.ip_addr);

        triggerRelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ipAddr.getText()+"/set.php?name=relay&val=0", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("MAIN-ACCESS","Response"+response);
                        Toast.makeText(App.getAppContext(),"Response: "+response,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("MAIN-ACCESS","Response"+error);
                        Toast.makeText(App.getAppContext(),"Error: "+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(stringRequest);
            }
        });

        return rootView;
    }


}
