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

public class MyplantListAdapter extends RecyclerView.Adapter<MyplantListAdapter.CustomViewHolder_myplant_list> {

    private ArrayList<MyplantListData> myplant_list_arrayList;

    public MyplantListAdapter(ArrayList<MyplantListData> arrayList) {
        this.myplant_list_arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyplantListAdapter.CustomViewHolder_myplant_list onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myplant_list, parent, false);
        MyplantListAdapter.CustomViewHolder_myplant_list holder = new MyplantListAdapter.CustomViewHolder_myplant_list(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyplantListAdapter.CustomViewHolder_myplant_list holder, int position) {

        holder.myplant_list_image.setImageBitmap(myplant_list_arrayList.get(position).getMyplant_list_image());
        holder.myplant_list_name.setText(myplant_list_arrayList.get(position).getMyplant_list_name());

    }

    @Override
    public int getItemCount() { return (null != myplant_list_arrayList ? myplant_list_arrayList.size() : 0);}

    public class CustomViewHolder_myplant_list extends RecyclerView.ViewHolder {

        protected ImageView myplant_list_image;
        protected TextView myplant_list_name;

        public CustomViewHolder_myplant_list(@NonNull @NotNull View itemView) {
            super(itemView);
            this.myplant_list_image = (ImageView) itemView.findViewById(R.id.myplant_list_image);
            this.myplant_list_name = (TextView) itemView.findViewById(R.id.myplant_list_name);

        }
    }
}
