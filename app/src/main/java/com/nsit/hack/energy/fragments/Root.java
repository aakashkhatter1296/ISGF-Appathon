package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nsit.hack.energy.R;
import com.nsit.hack.energy.activities.RootDetailActivity;
import com.nsit.hack.energy.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by joydeep on 12/3/16.
 */
public class Root extends Fragment {

    public Root() {
        // Required empty public constructor
    }
    String url = "http://iot.net.in/sparcs/slice_edison_vi.php";
    RequestQueue requestQueue;
    JSONArray js;
    Task mTask;
    TextView currVolt, currPow;

    private boolean mustStop;
    CardView voltageCard, meterReadingCard, powerCard;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_root_static, container, false);
        voltageCard = (CardView) rootView.findViewById(R.id.voltage_card);
        meterReadingCard = (CardView) rootView.findViewById(R.id.meter_reading_card);
        powerCard = (CardView) rootView.findViewById(R.id.power_card);

        currPow = (TextView) rootView.findViewById(R.id.curr_power);
        currVolt = (TextView) rootView.findViewById(R.id.curr_voltage);

        voltageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RootDetailActivity.class);
                intent.putExtra("rootProperty", "voltage");
                startActivity(intent);
            }
        });

        meterReadingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RootDetailActivity.class);
                intent.putExtra("rootProperty", "meterReading");
                startActivity(intent);
            }
        });

        powerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RootDetailActivity.class);
                intent.putExtra("rootProperty", "power");
                startActivity(intent);
            }
        });

        requestQueue = VolleySingleton.getmInstance().getRequestQueue();
        return rootView;
    }

    private class Task extends AsyncTask<Void, Void, Void> {
        StringRequest jsonArrRequest;

        @Override
        protected Void doInBackground(Void... params) {
            while (!mustStop) {
                jsonArrRequest = new StringRequest(Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    js = new JSONArray(response);
                                    double volt = js.getJSONObject(4).getDouble("voltage_s");
                                    double pow =  js.getJSONObject(4).getDouble("current_s") * volt;
//                                    String light = "Inside: " + js.getJSONObject(4).getInt("light_s");
//                                    tempIN.setText(temp);
//                                    humidIN.setText(humid);
//                                    lightIN.setText(light);
                                    currVolt.setText("Current Voltage: "+volt+" V");
                                    currPow.setText("Current Power: "+pow+" VA");
                                    Log.d("aakash", "" + js);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("aakash", "Error: " + error.getMessage());
                        error.printStackTrace();
                    }
                });
                jsonArrRequest.setRetryPolicy(new DefaultRetryPolicy(
                        150000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(jsonArrRequest);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mustStop = false;
        mTask = new Task();
        mTask.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
        mustStop = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        mustStop = true;
    }

}
