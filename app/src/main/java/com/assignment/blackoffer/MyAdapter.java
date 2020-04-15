package com.assignment.blackoffer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<DataModel> dataModelList;
    private Context mContext;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView subTitleTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
        }

    }




        // View holder class whose objects represent each list item



        public MyAdapter(List<DataModel> modelList, Context context) {
            dataModelList = modelList;
            mContext = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Inflate out card list item

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            // Return a new view holder

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // Bind data for the item at position
            holder.titleTextView.setText(dataModelList.get(position).getTitle().toString());
            holder.subTitleTextView.setText((dataModelList.get(position).getSubTitle().toString()));
        }

        @Override
        public int getItemCount() {
            // Return the total number of items

            return dataModelList.size();
        }
    }