package com.assignment.blackoffer;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TrendingFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<DataModel> dataModelList;
    private MyAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    public TrendingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trending, container, false);

            dataModelList=new ArrayList<>();
            dataModelList.add(new DataModel("#TechModay","12 People NearBy"));
            dataModelList.add(new DataModel("#Covid19","13 People NearBy"));
            dataModelList.add(new DataModel("#fifacup","8 People Nearby"));
            dataModelList.add(new DataModel("#lockdown","7 People Nearby"));
            dataModelList.add(new DataModel("#Foodie","8 People Nearby"));
            dataModelList.add(new DataModel("#StayHome","12 People NearBy"));
            dataModelList.add(new DataModel("#Startup","12 People NearBy"));



            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapter = new MyAdapter(dataModelList, getContext());
            recyclerView.setAdapter(adapter);

        }
        return view;
    }
}


