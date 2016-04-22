package com.nsit.hack.energy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.hotspotManager.WiFiHotspot;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by joydeep on 7/3/16.
 */

public class Hotspot extends Fragment {

    private WiFiHotspot hotUtil;
    private TextView connected;
    private String connection;
    private LinearLayout linear;
    private boolean check;
    ImageView image;

    public Hotspot() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotspot, container, false);

        hotUtil = new WiFiHotspot(getActivity());
        connected = (TextView) rootView.findViewById(R.id.connected);

        //linear = (LinearLayout) rootView.findViewById(R.id.ref);
        image = (ImageView) rootView.findViewById(R.id.refresh);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check) {
                    if (hotUtil.startHotSpot(true)) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Turned On", Toast.LENGTH_SHORT).show();
                        check = true;
                        image.setImageResource(R.drawable.ic_tethering_on);
                        connected.setText("Hotspot on");
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Not Turned On", Toast.LENGTH_SHORT).show();
                        check = false;
                        connected.setText("Hotspot off");
                        image.setImageResource(R.drawable.ic_wifi_tethering);
                    }
                } else {
                    if (hotUtil.startHotSpot(false)) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                " Device HotSpot is Turned Off", Toast.LENGTH_SHORT).show();
                        check = false;
                        image.setImageResource(R.drawable.ic_wifi_tethering);
                        connected.setText("Hotspot off");
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Not Turned Off", Toast.LENGTH_SHORT).show();
                        check = true;
                        image.setImageResource(R.drawable.ic_tethering_on);
                        connected.setText("Hotspot on");
                    }
                }
            }
        });
        //final Switch mButton = (Switch) rootView.findViewById(R.id.hotspot_toggle);

        if (hotUtil.isWifiApEnabled()) {
            check = true;
            image.setImageResource(R.drawable.ic_tethering_on);
            connected.setText("Hotspot on");
        } else {
            check = false;
            image.setImageResource(R.drawable.ic_wifi_tethering);
            connected.setText("Hotspot off");
        }

        /*ImageView refresh = (ImageView) rootView.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click", "clicked");
                if (check) {
                    //int num = countNumMac();
                    getListOfConnectedDevice();
                    connected.setText(connection);
                } else {
                    connected.setText("Hotspot off");
                }
            }
        });*/

        /*mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButton.isChecked()) {
                    if (hotUtil.startHotSpot(true)) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Turned On", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Not Turned On", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (hotUtil.startHotSpot(false)) {
                        Toast.makeText(getActivity().getApplicationContext(),
                                " Device HotSpot is Turned Off", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Device HotSpot is Not Turned Off", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
        return rootView;
    }

    public void getListOfConnectedDevice() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader br = null;
                boolean isFirstLine = true;
                try {
                    br = new BufferedReader(new FileReader("/proc/net/arp"));
                    String line;

                    while ((line = br.readLine()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false;
                            continue;
                        }
                        String[] splitted = line.split(" +");
                        if (splitted != null && splitted.length >= 4) {
                            String ipAddress = splitted[0];
                            String macAddress = splitted[3];

                            boolean isReachable = InetAddress.getByName(
                                    splitted[0]).isReachable(500);
                            if (isReachable) {
                                connection = ipAddress + "; " + macAddress + "; ";
                                Log.d("hotspot", connection);
                            } else {
                                connection = "No devices connected";
                                Log.d("Device Information", connection);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null)
                            br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}