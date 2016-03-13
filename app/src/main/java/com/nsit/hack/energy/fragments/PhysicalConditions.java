package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.nsit.hack.energy.activities.PhysicalConditionsCharts;
import com.nsit.hack.energy.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by aman on 12/3/16.
 */
public class PhysicalConditions extends Fragment {

    private boolean mustStop;
    String url = "http://www.iot.net.in/homegrid/slice_iota.php";
    RequestQueue requestQueue;
    JSONArray js;
    Task mTask;
    CardView tempCard, humidCard, lightCard;
    TextView tempIN, humidIN, lightIN;

    public PhysicalConditions() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_physical_conditions, container, false);
        tempCard = (CardView) rootView.findViewById(R.id.temperature_card);
        humidCard = (CardView) rootView.findViewById(R.id.humidity_card);
        lightCard = (CardView) rootView.findViewById(R.id.ambient_light_card);

        tempIN = (TextView) rootView.findViewById(R.id.temperature_inside);
        humidIN = (TextView) rootView.findViewById(R.id.humidity_inside);
        lightIN = (TextView) rootView.findViewById(R.id.ambient_light_inside);

        tempCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "Temperature");
                startActivity(intent);
            }
        });

        humidCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "Humidity");
                startActivity(intent);
            }
        });

        lightCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "Ambient light");
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
                                    String temp = "Inside: " + js.getJSONObject(4).getInt("temp");
                                    String humid = "Inside: " + js.getJSONObject(4).getInt("humidity");
                                    String light = "Inside: " + js.getJSONObject(4).getInt("ldr");
                                    tempIN.setText(temp);
                                    humidIN.setText(humid);
                                    lightIN.setText(light);
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
