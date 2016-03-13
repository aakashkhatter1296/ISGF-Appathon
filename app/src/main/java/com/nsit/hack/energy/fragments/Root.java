package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.activities.RootDetailActivity;

/**
 * Created by joydeep on 12/3/16.
 */
public class Root extends Fragment {

    public Root() {
        // Required empty public constructor
    }
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
        return rootView;
    }

}
