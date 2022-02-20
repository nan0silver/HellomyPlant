package com.nahyun.helloplant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.Image;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyplantListAdapter.CustomViewHolder_myplant_list holder, int position) {

        holder.myplant_list_image.setImageBitmap(myplant_list_arrayList.get(position).getMyplant_list_image());
        holder.myplant_list_name.setText(myplant_list_arrayList.get(position).getMyplant_list_name());
        holder.myplant_list_water = myplant_list_arrayList.get(position).getMyplant_list_water();
        holder.myplant_list_fertilizer = myplant_list_arrayList.get(position).getMyplant_list_fertilizer();

        //width max 350

        Bitmap water_bitmap = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(water_bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(149, 215, 132));
        canvas.drawRect(0, 0, 350, 100, paint);
        holder.myplant_list_water_imageview.setImageBitmap(water_bitmap);

        Bitmap fertilizer_bitmap = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(fertilizer_bitmap);
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.rgb(149, 215, 132));
        canvas2.drawRect(0, 0, 350, 100, paint2);
        holder.myplant_list_fertilizer_imageview.setImageBitmap(fertilizer_bitmap);

    }

    @Override
    public int getItemCount() { return (null != myplant_list_arrayList ? myplant_list_arrayList.size() : 0);}

    public class CustomViewHolder_myplant_list extends RecyclerView.ViewHolder {

        protected ImageView myplant_list_image;
        protected TextView myplant_list_name;
        protected ImageView myplant_list_water_imageview;
        protected ImageView myplant_list_fertilizer_imageview;
        protected String myplant_list_water;
        protected String myplant_list_fertilizer;

        public CustomViewHolder_myplant_list(@NonNull @NotNull View itemView) {
            super(itemView);
            this.myplant_list_image = (ImageView) itemView.findViewById(R.id.myplant_list_image);
            this.myplant_list_name = (TextView) itemView.findViewById(R.id.myplant_list_name);
            this.myplant_list_water_imageview = (ImageView) itemView.findViewById(R.id.myplant_list_water_imageView);
            this.myplant_list_fertilizer_imageview = (ImageView) itemView.findViewById(R.id.myplant_list_fertilizer_imageView);

        }
    }

}
