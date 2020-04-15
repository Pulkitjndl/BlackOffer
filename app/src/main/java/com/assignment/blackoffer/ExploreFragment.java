package com.assignment.blackoffer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ExploreFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<DataModelExplore> dataModelList;
    private ExploreAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View view;

    public ExploreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_explore, container, false);
            dataModelList = new ArrayList<>();
            dataModelList.add(new DataModelExplore(R.drawable.circleimage,"Ellen Michel","Arc Vibe LTD CEO","This is a descirption of ellen who is working in arc "));
            dataModelList.add(new DataModelExplore(R.drawable.circleimage,"Isac Morgan","Joe pipes CEo","This is a descirption of Isac who is working in arc "));
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

            adapter = new ExploreAdapter(dataModelList, getContext());
            recyclerView.setAdapter(adapter);

        }
        return view;
    }
}