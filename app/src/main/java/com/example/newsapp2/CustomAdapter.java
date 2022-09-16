package com.example.newsapp2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    Context context;
    ArrayList<model> arr;
    SelectListener listener;

    public CustomAdapter(Context context, ArrayList<model> arr, SelectListener listener) {
        this.context = context;
        this.arr = arr;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.TITLE.setText(arr.get(position).title);
        holder.SOURCE_ID.setText(arr.get(position).source_id);
        Log.d("Gaurav","Image Url : "+ arr.get(position).Image_url);
        if(arr.get(position).Image_url != null){
            Picasso.get().load(arr.get(position).Image_url).into(holder.imageView);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClicked(arr.get(position));

            }
        });



    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TITLE,SOURCE_ID;
        ImageView imageView;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            TITLE = itemView.findViewById(R.id.layout_title);
            SOURCE_ID = itemView.findViewById(R.id.layout_source_id);
            imageView = itemView.findViewById(R.id.layout_image);
            linearLayout = itemView.findViewById(R.id.card_linear_layout);


        }
    }


}
