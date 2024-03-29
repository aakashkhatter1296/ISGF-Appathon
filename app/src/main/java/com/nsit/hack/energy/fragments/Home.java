//
//package com.nsit.hack.energy.fragments;
//
//import android.graphics.Color;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ProgressBar;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.nsit.hack.energy.R;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//
//import java.util.ArrayList;
//
//
///**
// * Created by joydeep on 7/3/16.
// */
//
//public class Home extends Fragment {
//    String url = "http://iot.net.in/smartmeter/support/slice_iota.php";
//    JSONArray js;
//    LineChart chart, humidityChart;
//    ArrayList<String> xAxis;
//    Task mTask;
//    ProgressBar loadingSpinner;
//
//    public Home() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//
///*ArrayList<Entry> entries = new ArrayList<>(); //y
//        entries.add(new Entry(4f, 0));
//        entries.add(new Entry(8f, 1));
//        entries.add(new Entry(6f, 2));
//        entries.add(new Entry(12f, 3));
//        entries.add(new Entry(18f, 4));
//        entries.add(new Entry(9f, 5));
//        dataset = new LineDataSet(entries, "# of Calls");
//        dataset.setColor(Color.RED);
//        dataset.setCircleColor(Color.RED);*//*
//
//        */
///*ArrayList<String> xAxis = new ArrayList<String>(); //x
//        xAxis.add("January");
//        xAxis.add("February");
//        xAxis.add("March");
//        xAxis.add("April");
//        xAxis.add("May");
//        xAxis.add("June");*/
//
//        xAxis = new ArrayList<>(); //x
//        for (int i = 0; i < 10; i++) {
//            xAxis.add("j" + i);
//        }
//        chart = (LineChart) rootView.findViewById(R.id.chart1);
//        loadingSpinner = (ProgressBar) rootView.findViewById(R.id.loadingSpinner);
//
//        //data = new LineData(xAxis, dataset);
//        //chart.setData(new LineData());
//        chart.setDescription("Variation of temperature with time");
//        humidityChart.setData(new LineData());
//        humidityChart.setDescription("Variation of humidity with time");
//
//        /*LineData data = new LineData(xAxis, dataset);
//        chart.setData(data);
//        chart.setDescription("# of times Alice called Bob");*/
//
//
//        return rootView;
//    }
//
//    private void updateData(JSONArray js) {
//        Log.d("joydeep", String.valueOf(js));
//
//        LineData data = chart.getData();
//        LineData humidityData = humidityChart.getData();
//
//        if (data != null) {
//            loadingSpinner.setVisibility(View.GONE);
//            chart.setVisibility(View.VISIBLE);
//        }
//        if (humidityData != null) {
//            humidityChart.setVisibility(View.VISIBLE);
//        }
//
//        ILineDataSet set = data.getDataSetByIndex(0);
//        ILineDataSet humiditySet = humidityData.getDataSetByIndex(0);
//        // set.addEntry(...); // can be called as well
//
//        if (set == null) {
//            set = createSet();
//            data.addDataSet(set);
//        }
//        if (humiditySet == null) {
//            humiditySet = createSet();
//            humidityData.addDataSet(humiditySet);
//        }
//        try {
//            for (int j = 0; j < js.length(); j++) {
//
//                data.addXValue(String.valueOf(j));
//                //data.addEntry(new Entry((float) (Math.random() * 40) + 30f, set.getEntryCount()), 0);
//                data.addEntry(new Entry((float) js.getJSONObject(j).getInt("temp"), set.getEntryCount()), 0);
//                humidityData.addXValue(String.valueOf(j));
//                //data.addEntry(new Entry((float) (Math.random() * 40) + 30f, set.getEntryCount()), 0);
//                humidityData.addEntry(new Entry((float) js.getJSONObject(j).getInt("humidity"), set.getEntryCount()), 0);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        chart.notifyDataSetChanged();
//
//        //dataset = new LineDataSet(js, "# of Calls");
//        //data = new LineData(xAxis, dataset);
//        //chart.setData(data);
//        chart.setVisibleXRangeMaximum(10);
//        chart.moveViewToX(data.getXValCount() - 11);
//
//        humidityChart.notifyDataSetChanged();
//        humidityChart.setVisibleXRangeMaximum(10);
//        humidityChart.moveViewToX(data.getXValCount() - 11);
//
//    }
//
//    private LineDataSet createSet() {
//
//        LineDataSet set = new LineDataSet(null, "Dynamic Data");
//        set.setAxisDependency(YAxis.AxisDependency.LEFT);
//        set.setColor(ColorTemplate.getHoloBlue());
//        set.setCircleColor(Color.WHITE);
//        set.setLineWidth(2f);
//        set.setCircleRadius(4f);
//        set.setFillAlpha(65);
//        set.setFillColor(ColorTemplate.getHoloBlue());
//        set.setHighLightColor(Color.rgb(244, 117, 117));
//        set.setValueTextColor(Color.WHITE);
//        set.setValueTextSize(9f);
//        set.setDrawValues(false);
//        return set;
//    }
//
//
//    private class Task extends AsyncTask<Void, Void, Void> {
//        RequestQueue queue;
//        StringRequest jsonArrRequest;
//
//        @Override
//        protected Void doInBackground(Void... params) {
//
///*Log.v("Async====", "DoInBAckground...");
//            queue = Volley.newRequestQueue(getActivity());
//            jsonArrRequest = new StringRequest(Request.Method.GET,
//                    url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.v("Async====", "onResponse...");
//                            try {
//                                js = new JSONArray(response);
//                                Log.d("aakash", "" + js);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.d("HackEditor", "Error: " + error.getMessage());
//                    error.printStackTrace();
//                }
//            });*/
//
//
////            jsonArrRequest.setRetryPolicy(new DefaultRetryPolicy(
////                    20000,
////                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
////                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////            jsonArrRequest.setShouldCache(false);
//
//            while (true) {
//                queue = Volley.newRequestQueue(getActivity());
//                jsonArrRequest = new StringRequest(Request.Method.GET,
//                        url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                try {
//                                    js = new JSONArray(response);
//
//                                    ArrayList entries = new ArrayList<>();
//                                    for (int j = 0; j < js.length(); j++) {
//                                        entries.add(new Entry((float) js.getJSONObject(j).getDouble("a_current"), j));
//                                    }
//
//                                    updateData(js);
//                                    Log.d("aakash", "" + js);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("aakash", "Error: " + error.getMessage());
//                        error.printStackTrace();
//                    }
//                });
//                jsonArrRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        150000,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                queue.add(jsonArrRequest);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mTask = new Task();
//        mTask.execute();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        //check the state of the task
//        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING)
//            mTask.cancel(true);
//    }
//}
