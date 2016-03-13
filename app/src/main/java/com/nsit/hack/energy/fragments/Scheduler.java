package com.nsit.hack.energy.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsit.hack.energy.R;

/**
 * Created by joydeep on 7/3/16.
 */
public class Scheduler extends Fragment {
    public Scheduler() {
    }
    String url = "http://enter-your-url.com";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_billing, container, false);

        return rootView;

    }
}
