package com.nsit.hack.energy.activities;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nsit.hack.energy.R;
import com.nsit.hack.energy.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PhysicalConditionsCharts extends AppCompatActivity {
    String url = "http://www.iot.net.in/homegrid/slice_iota.php";
    JSONArray js;
    LineChart chart;
    ArrayList<String> xAxis;
    Task mTask;
    ProgressBar loadingSpinner;
    private boolean mustStop;
    RequestQueue requestQueue;
    String physicalCondition;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_conditions_charts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        physicalCondition = extras.getString("condition");
        setTitle(physicalCondition);

        requestQueue = VolleySingleton.getmInstance().getRequestQueue();

        xAxis = new ArrayList<>(); //x
        for (int i = 0; i < 5; i++) {
            xAxis.add("j" + i);
        }
        chart = (LineChart) findViewById(R.id.physical_conditions_chart);
        loadingSpinner = (ProgressBar) findViewById(R.id.loadingSpinner2);
        chart.setData(new LineData());

        if (physicalCondition.equals("Temperature")) {
            chart.setDescription("Temperature v/s Time");
        } else if (physicalCondition.equals("Humidity")) {
            chart.setDescription("Humidity v/s Time");
        } else {
            chart.setDescription("Ambient Light v/s Time");
        }
    }

    private void updateData(JSONArray js) {
        //Log.d("joydeep", String.valueOf(js));

        LineData data = chart.getData();

        if (data != null) {
            loadingSpinner.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
        }
        ILineDataSet set = data.getDataSetByIndex(0);

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }
        if (physicalCondition.equals("Temperature")) {
            try {
                for (int j = 0; j < js.length(); j++) {
                    data.addXValue(String.valueOf(j));
                    data.addEntry(new Entry((float) js.getJSONObject(j).getInt("temp"), time++), 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (physicalCondition.equals("Humidity")) {
            try {
                for (int j = 0; j < js.length(); j++) {
                    data.addXValue(String.valueOf(j));
                    data.addEntry(new Entry((float) js.getJSONObject(j).getInt("humidity"), time++), 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (physicalCondition.equals("Ambient light")) {
            try {
                for (int j = 0; j < js.length(); j++) {
                    data.addXValue(String.valueOf(j));
                    data.addEntry(new Entry((float) js.getJSONObject(j).getInt("ldr"), time++), 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        chart.notifyDataSetChanged();
        chart.setVisibleXRangeMaximum(js.length());
        chart.moveViewToX(data.getXValCount() - js.length() + 1);
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
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
                                    updateData(js);
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
        time = 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mustStop = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        //check the state of the task
        mustStop = true;
    }
}
