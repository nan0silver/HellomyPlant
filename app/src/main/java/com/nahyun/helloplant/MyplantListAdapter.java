package com.nahyun.helloplant;

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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MyplantListAdapter extends RecyclerView.Adapter<MyplantListAdapter.CustomViewHolder_myplant_list> {

    private List<MyplantListData> myplant_list_arrayList = new ArrayList<>();
    private MyplantListAdapter.OnItemClickListener itemClickListener = null;

    public void setOnItemClickListener(MyplantListAdapter.OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public MyplantListAdapter(ArrayList<MyplantListData> arrayList) {
        this.myplant_list_arrayList.addAll(arrayList);
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
        holder.myplant_list_water_cycle = myplant_list_arrayList.get(position).getMyplant_list_water();
        holder.myplant_list_fertilizer_cycle = myplant_list_arrayList.get(position).getMyplant_list_fertilizer();
        holder.myplant_createdAt = myplant_list_arrayList.get(position).getMyplant_list_createdAt();
        holder.myplant_updatedAt = myplant_list_arrayList.get(position).getMyplant_list_updatedAt();

        String created_day = holder.myplant_createdAt.substring(8, 10);
        String updated_day = holder.myplant_updatedAt.substring(8, 10);
        System.out.println("MyplantList created_day : " + created_day);
        System.out.println("MyplantList updated_day : " + updated_day);

        LocalDate now_date = LocalDate.now();
        int now_day = now_date.getDayOfMonth();
        System.out.println("MyplantList now_day : " + now_day);

        int last_day = now_day - Integer.parseInt(created_day);
        System.out.println("MyplantList last_day : " + last_day);
        System.out.println("MyplantList water_cycle_day" + holder.myplant_list_water_cycle);
        System.out.println("MyplantList fertilizer_cycle_day" + holder.myplant_list_fertilizer_cycle);

        int water_cycle_integer = Integer.parseInt(holder.myplant_list_water_cycle);
        int fertilizer_cycle_integer = Integer.parseInt(holder.myplant_list_fertilizer_cycle);

        if (last_day > water_cycle_integer) { last_day %= water_cycle_integer;}
        if (last_day > fertilizer_cycle_integer) { last_day %= fertilizer_cycle_integer; }

        int water_width = 350 * (water_cycle_integer-last_day) / water_cycle_integer;
        int fertilizer_width = 350 * (fertilizer_cycle_integer-last_day) /fertilizer_cycle_integer;

        holder.myplant_list_water_lastday_TextView.setText(Integer.toString(water_cycle_integer-last_day) + " 일");
        holder.myplant_list_fertilizer_lastday_TextView.setText(Integer.toString(fertilizer_cycle_integer-last_day) + " 일");

        //width max 350

        Bitmap water_bitmap = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(water_bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(149, 215, 132));
        canvas.drawRect(0, 0, water_width, 100, paint);

        Paint paint_gray = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_gray.setColor(Color.rgb(230, 230, 230));
        canvas.drawRect(water_width, 0, 350, 100, paint_gray);
        holder.myplant_list_water_imageview.setImageBitmap(water_bitmap);

        Bitmap fertilizer_bitmap = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(fertilizer_bitmap);
        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.rgb(149, 215, 132));
        canvas2.drawRect(0, 0, fertilizer_width, 100, paint2);

        Paint paint_gray2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_gray2.setColor(Color.rgb(230, 230, 230));
        canvas2.drawRect(fertilizer_width, 0, 350, 100, paint_gray2);
        holder.myplant_list_fertilizer_imageview.setImageBitmap(fertilizer_bitmap);
    }

    @Override
    public int getItemCount() { return (null != myplant_list_arrayList ? myplant_list_arrayList.size() : 0);}

    public class CustomViewHolder_myplant_list extends RecyclerView.ViewHolder {

        protected ImageView myplant_list_image;
        protected TextView myplant_list_name;
        protected ImageView myplant_list_water_imageview;
        protected ImageView myplant_list_fertilizer_imageview;
        protected String myplant_list_water_cycle;
        protected String myplant_list_fertilizer_cycle;
        protected String myplant_createdAt;
        protected String myplant_updatedAt;

        protected TextView myplant_list_water_lastday_TextView;
        protected TextView myplant_list_fertilizer_lastday_TextView;

        public CustomViewHolder_myplant_list(@NonNull @NotNull View itemView) {
            super(itemView);
            this.myplant_list_image = (ImageView) itemView.findViewById(R.id.myplant_list_image);
            this.myplant_list_name = (TextView) itemView.findViewById(R.id.myplant_list_name);
            this.myplant_list_water_imageview = (ImageView) itemView.findViewById(R.id.myplant_list_water_imageView);
            this.myplant_list_fertilizer_imageview = (ImageView) itemView.findViewById(R.id.myplant_list_fertilizer_imageView);
            this.myplant_list_water_lastday_TextView = (TextView) itemView.findViewById(R.id.myplant_list_water_lastday_TextView);
            this.myplant_list_fertilizer_lastday_TextView = (TextView) itemView.findViewById(R.id.myplant_list_fertilizer_lastday_TextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAbsoluteAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (itemClickListener != null) itemClickListener.onItemClick(v, pos);

                        notifyItemChanged(pos);
                    }
                }
            });
        }
    }

    public void updateMyplantListItems(List<MyplantListData> myplants) {
        final MyplantListDiffCallback myplantListDiffCallback = new MyplantListDiffCallback(this.myplant_list_arrayList, myplants);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myplantListDiffCallback);


        System.out.println("old : " + this.myplant_list_arrayList);
        System.out.println("new : " + myplants);

        this.myplant_list_arrayList.clear();
        this.myplant_list_arrayList.addAll(myplants);
        diffResult.dispatchUpdatesTo(this);

        System.out.println("updateMyplantListItems");

    }

}
