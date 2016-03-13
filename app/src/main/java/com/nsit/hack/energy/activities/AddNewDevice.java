package com.nsit.hack.energy.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.utils.ShowMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewDevice extends AppCompatActivity {

    EditText device1,device2,device3,device4,device5, app_power1,app_power2,app_power3,app_power4,app_power5,react_power1,react_power2,react_power3,react_power4,react_power5;

    String json = new String("[");
    Button setJsonBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_device);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add new device");
        setSupportActionBar(toolbar);

        device1 = (EditText)findViewById(R.id.device_1);
        device2 = (EditText)findViewById(R.id.device_2);
        device3 = (EditText)findViewById(R.id.device_3);
        device4 = (EditText)findViewById(R.id.device_4);
        device5 = (EditText)findViewById(R.id.device_5);

        app_power1 = (EditText)findViewById(R.id.apparent_1);
        app_power2 = (EditText)findViewById(R.id.apparent_2);
        app_power3 = (EditText)findViewById(R.id.apparent_3);
        app_power4 = (EditText)findViewById(R.id.apparent_4);
        app_power5 = (EditText)findViewById(R.id.apparent_5);

        react_power1 = (EditText)findViewById(R.id.reactive_1);
        react_power2 = (EditText)findViewById(R.id.reactive_2);
        react_power3 = (EditText)findViewById(R.id.reactive_3);
        react_power4 = (EditText)findViewById(R.id.reactive_4);
        react_power5 = (EditText)findViewById(R.id.reactive_5);

        setJsonBtn = (Button) findViewById(R.id.btn_add_devices);

        setJsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createJSONFromEditText();
                } catch (JSONException e) {
                    e.printStackTrace();
                    ShowMessage.toast("Error occured while adding devices");
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void createJSONFromEditText () throws JSONException {
        if(!device1.getText().toString().equals("")) {
            JSONObject jsonp = new JSONObject();

            jsonp.put("name",device1.getText().toString());
            jsonp.put("apparent_pow",app_power1.getText().toString());
            jsonp.put("reactive_pow",react_power1.getText().toString());

            json = json.concat(jsonp.toString());

        }
        if(!device2.getText().toString().equals("")) {
            JSONObject jsonp = new JSONObject();
            json = json.concat(",");
            jsonp.put("name",device2.getText().toString());
            jsonp.put("apparent_pow",app_power2.getText().toString());
            jsonp.put("reactive_pow",react_power2.getText().toString());

            json = json.concat(jsonp.toString());
        }
        if(!device3.getText().toString().equals("")) {
            JSONObject jsonp = new JSONObject();
            json = json.concat(",");
            jsonp.put("name",device3.getText().toString());
            jsonp.put("apparent_pow",app_power3.getText().toString());
            jsonp.put("reactive_pow",react_power3.getText().toString());

            json = json.concat(jsonp.toString());

        }
        if(!device4.getText().toString().equals("")) {
            JSONObject jsonp = new JSONObject();
            json = json.concat(",");
            jsonp.put("name",device4.getText().toString());
            jsonp.put("apparent_pow",app_power4.getText().toString());
            jsonp.put("reactive_pow",react_power4.getText().toString());

            json = json.concat(jsonp.toString());

        }
        if(!device5.getText().toString().equals("")) {
            JSONObject jsonp = new JSONObject();
            json = json.concat(",");
            jsonp.put("name",device5.getText().toString());
            jsonp.put("apparent_pow",app_power5.getText().toString());
            jsonp.put("reactive_pow",react_power5.getText().toString());

            json = json.concat(jsonp.toString());
        }

        json = json.concat("]");

        ShowMessage.toast(json);


    }

}
