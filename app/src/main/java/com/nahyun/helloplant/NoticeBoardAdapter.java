package com.nahyun.helloplant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardAdapter.CustomViewHolder>{

    private ArrayList<NoticeBoardData> noticeboard_list_arrayList;
    private OnItemClickListener itemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public NoticeBoardAdapter(ArrayList<NoticeBoardData> arrayList) {
        this.noticeboard_list_arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeBoardAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_board_list, parent, false);
        NoticeBoardAdapter.CustomViewHolder holder = new NoticeBoardAdapter.CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeBoardAdapter.CustomViewHolder holder, int position) {

        holder.noticeboard_list_ImageView.setImageBitmap(noticeboard_list_arrayList.get(position).getNoticeboard_image());
        holder.noticeboard_list_TextView.setText(noticeboard_list_arrayList.get(position).getNoticeboard_name());
    }

    @Override
    public int getItemCount() {
        return (null != noticeboard_list_arrayList ? noticeboard_list_arrayList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView noticeboard_list_ImageView;
        protected TextView noticeboard_list_TextView;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.noticeboard_list_ImageView = (ImageView) itemView.findViewById(R.id.noticeboard_list_ImageView);
            this.noticeboard_list_TextView = (TextView) itemView.findViewById(R.id.noticeboard_list_TextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(v, getAbsoluteAdapterPosition());
        }
    }

}
