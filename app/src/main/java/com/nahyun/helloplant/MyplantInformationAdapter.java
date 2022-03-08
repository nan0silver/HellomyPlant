package com.nahyun.helloplant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyplantInformationAdapter extends RecyclerView.Adapter<MyplantInformationAdapter.CustomViewHolder>{

    private ArrayList<MyplantInformationData> arrayList;

    public MyplantInformationAdapter(ArrayList<MyplantInformationData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public MyplantInformationAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myplant_information_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyplantInformationAdapter.CustomViewHolder holder, int position) {

        holder.myplant_information_attribute.setText(arrayList.get(position).getMyplant_information_attribute());
        holder.myplant_information_value.setText(arrayList.get(position).getMyplant_information_value());
    }

    @Override
    public int getItemCount() { return (null != arrayList ? arrayList.size() : 0); }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView myplant_information_attribute;
        protected TextView myplant_information_value;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.myplant_information_attribute = (TextView) itemView.findViewById(R.id.myplant_information_attribute);
            this.myplant_information_value = (TextView) itemView.findViewById(R.id.myplant_information_value);
        }
    }

    /*public void updateMyplantInformationItems(List<MyplantInformationData> myplants) {
        final MyplantInformationDiffCallback myplantInformationDiffCallback = new MyplantInformationDiffCallback(this.arrayList, myplants);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myplantInformationDiffCallback);

        System.out.println("old : " + this.arrayList);
        System.out.println("new : " + myplants);

        this.arrayList.clear();
        this.arrayList.addAll(myplants);
        diffResult.dispatchUpdatesTo(this);

        System.out.println("update Myplant information Items");

    }*/
}
