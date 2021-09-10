package com.example.tupa_mobile.OnBoarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {

    List<SliderData> list;

    public SliderAdapter(List<SliderData> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        holder.imageView.setImageResource(list.get(position).getImages());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.slider_title);
            description = itemView.findViewById(R.id.slider_desc);
            imageView = itemView.findViewById(R.id.slider_image_view);
        }
    }
}