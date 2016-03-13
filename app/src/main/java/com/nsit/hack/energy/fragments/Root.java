package com.nsit.hack.energy.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsit.hack.energy.R;
import com.nsit.hack.energy.RecyclerItemClickListener;
import com.nsit.hack.energy.RootAdapter;
import com.nsit.hack.energy.RootItem;
import com.nsit.hack.energy.SpaceItemDecorator;
import com.nsit.hack.energy.activities.RootDetailActivity;

import java.util.ArrayList;

/**
 * Created by joydeep on 12/3/16.
 */
public class Root extends Fragment {

    RecyclerView recyclerView;
    ArrayList<RootItem> arrayList;

    public Root() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
//        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), this));
//        recyclerView.addItemDecoration(new SpaceItemDecorator(getActivity(), R.dimen.list_space, true, true));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        arrayList = new ArrayList<>();
//        arrayList.add(new RootItem("Voltage Fluctuations", R.drawable.ic_voltage, "0"));
//        arrayList.add(new RootItem("Meter Reading", R.drawable.ic_reading, "0"));
//        arrayList.add(new RootItem("Power", R.drawable.ic_power, "0"));
//        RootAdapter mAdapter = new RootAdapter(arrayList);
//        recyclerView.setAdapter(mAdapter);
        return rootView;
    }

//    @Override
//    public void onItemClick(View childView, int position) {
//        Intent intent = new Intent(getActivity(), RootDetailActivity.class);
//        intent.putExtra("position", position);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onItemLongPress(View childView, int position) {
//
//    }
}
