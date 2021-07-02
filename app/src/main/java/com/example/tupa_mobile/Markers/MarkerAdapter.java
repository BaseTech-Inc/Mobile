package com.example.tupa_mobile.Markers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;
import com.example.tupa_mobile.WeatherAPI.ForecastDayAdapter;

import java.util.ArrayList;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerHolder>{

    private Context context;
    private ArrayList<Marker> markers;

    public MarkerAdapter(Context context, ArrayList<Marker> markers) {
        this.context = context;
        this.markers = markers;
    }

    @NonNull
    @Override
    public MarkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.map_drawer_item, parent, false);
        return new MarkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerHolder holder, int position) {
        Marker marker = markers.get(position);
        holder.setDetails(marker);
    }

    @Override
    public int getItemCount() {
        return this.markers.size();
    }

    public class MarkerHolder extends RecyclerView.ViewHolder {

        private ImageView icoMarker;
        private TextView markerName;

        public MarkerHolder(@NonNull View view) {
            super(view);
            icoMarker = view.findViewById(R.id.icoLocation);
            markerName = view.findViewById(R.id.txtLocation);
        }

        public void setDetails(Marker marker) {
            icoMarker.setImageResource(marker.getType());
            markerName.setText(marker.getName());
        }
    }
}
