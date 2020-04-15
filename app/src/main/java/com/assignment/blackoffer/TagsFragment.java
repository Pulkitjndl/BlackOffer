package com.assignment.blackoffer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skyhope.materialtagview.TagView;


public class TagsFragment extends Fragment {


    View view;
    TagView tagView;

    public TagsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tags, container, false);
            tagView = view.findViewById(R.id.tag_view_test);
            tagView.setHint("Search Tags");
            //tagView.addTagSeparator(TagSeparator.AT_SEPARATOR);
            tagView.addTagLimit(5);
            String[] tagList = new String[]{"#Lockdown", "#foodie"};
            tagView.setTagList(tagList);

        }
        return view;
    }
}
