package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.activities.PhysicalConditionsCharts;

/**
 * Created by aman on 12/3/16.
 */
public class PhysicalConditions extends Fragment {

    public PhysicalConditions() {
        //
    }
    CardView tempCard,humidCard, lightCard;
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

        tempCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "temperature");
                startActivity(intent);
            }
        });

        humidCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "humidity");
                startActivity(intent);
            }
        });

        lightCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhysicalConditionsCharts.class);
                intent.putExtra("condition", "ambient light");
                startActivity(intent);
            }
        });


        return rootView;
    }
}
