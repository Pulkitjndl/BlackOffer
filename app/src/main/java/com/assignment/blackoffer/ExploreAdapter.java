package com.assignment.blackoffer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder> {

    private List<DataModelExplore> dataModelList;
    private Context mContext;


    public static class ExploreViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image;
        public TextView titleTextView;
        public TextView subTitleTextView;
        private TextView description;

        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            subTitleTextView = itemView.findViewById(R.id.subTitle);
            image=itemView.findViewById(R.id.circleImage);
            description=itemView.findViewById(R.id.description);

        }

    }




    // View holder class whose objects represent each list item



    public ExploreAdapter(List<DataModelExplore> modelList, Context context) {
        dataModelList = modelList;
        mContext = context;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detail, parent, false);
        // Return a new view holder

        return new ExploreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        // Bind data for the item at position
        holder.titleTextView.setText(dataModelList.get(position).getTitle().toString());
        holder.subTitleTextView.setText((dataModelList.get(position).getSubTitle().toString()));
        holder.description.setText(dataModelList.get(position).getDescription());
        holder.image.setImageResource(dataModelList.get(position).getCircleImage());
    }

    @Override
    public int getItemCount() {
        // Return the total number of items

        return dataModelList.size();
    }
}