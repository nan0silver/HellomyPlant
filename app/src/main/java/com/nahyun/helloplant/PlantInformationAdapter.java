package com.nahyun.helloplant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlantInformationAdapter extends RecyclerView.Adapter<PlantInformationAdapter.CustomViewHolder> {

    private ArrayList<PlantInformationData> arrayList;

    public PlantInformationAdapter(ArrayList<PlantInformationData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public PlantInformationAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_information_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlantInformationAdapter.CustomViewHolder holder, int position) {

        holder.plant_information_attribute.setText(arrayList.get(position).getPlant_information_attribute());
        holder.plant_information_value.setText(arrayList.get(position).getPlant_information_value());

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView plant_information_attribute;
        protected TextView plant_information_value;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.plant_information_attribute = (TextView) itemView.findViewById(R.id.plant_information_attribute);
            this.plant_information_value = (TextView) itemView.findViewById(R.id.plant_information_value);


        }
    }
}
