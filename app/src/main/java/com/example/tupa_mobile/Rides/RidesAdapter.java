package com.example.tupa_mobile.Rides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class RidesAdapter extends RecyclerView.Adapter<RidesAdapter.RidesHolder>{
    private Context context;
    private ArrayList<Rides> ride;

    public RidesAdapter(Context context, ArrayList<Rides> ride) {
        this.context = context;
        this.ride = ride;
    }

    @NonNull
    @Override
    public RidesAdapter.RidesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rides_item,parent, false);
        return new RidesAdapter.RidesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RidesAdapter.RidesHolder holder, int position) {
        Rides rides = ride.get(position);
        holder.setDetails(rides);
    }

    @Override
    public int getItemCount() {
        return ride.size();
    }


    public class RidesHolder extends RecyclerView.ViewHolder{

        private TextView txtDistance, txtTime, txtNumberEvents, txtEvents, txtNumberFloods, txtFloods;

        public RidesHolder(@NonNull View itemView) {
            super(itemView);

            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtTime = itemView.findViewById(R.id.txtTime);
        }

        void setDetails(Rides rides){
            txtDistance.setText(rides.getDistance());
            txtTime.setText(rides.getTime());
        }
    }


}
