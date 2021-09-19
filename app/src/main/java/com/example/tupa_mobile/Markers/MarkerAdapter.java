package com.example.tupa_mobile.Markers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerHolder>{

    private final CustomAdapterClickListener clickListener;
    private Context context;
    private ArrayList<MarkersData> markersData;

    public MarkerAdapter(Context context, ArrayList<MarkersData> markersData, final CustomAdapterClickListener clickListener) {
        this.context = context;
        this.markersData = markersData;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MarkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.map_drawer_item, parent, false);
        return new MarkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerHolder holder, int position) {
        MarkersData markerData = markersData.get(position);
        holder.setDetails(markerData);
    }

    @Override
    public int getItemCount() {
        return this.markersData.size();
    }

    public class MarkerHolder extends RecyclerView.ViewHolder {

        private ViewGroup searchLayout;
        private CardView drawerCard;
        private ImageView icoMarker;
        private TextView markerName, markerRegion;
        private EditText etTo;

        public MarkerHolder(@NonNull View view) {
            super(view);
            searchLayout = unwrap(context).findViewById(R.id.searchLayout);
            etTo = unwrap(context).findViewById(R.id.etTo);
            drawerCard = view.findViewById(R.id.drawerCard);
            icoMarker = view.findViewById(R.id.icoLocation);
            markerName = view.findViewById(R.id.txtLocationAddress);
            markerRegion = view.findViewById(R.id.txtLocationRegion);
        }

        public void setDetails(MarkersData markerData) {

            drawerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, markerData);
                }
            });

            switch (markerData.getId()){
                case "1":
                    icoMarker.setImageResource(R.drawable.day_sunny);
                case "2":
                    icoMarker.setImageResource(R.drawable.night_clear);
                case "3":
                    icoMarker.setImageResource(R.drawable.arrow_right_icon_white_black_theme_small);
                default:
                    icoMarker.setImageResource(R.drawable.nibolas);
            }

            markerName.setText(markerData.getNome());
            markerRegion.setText(markerData.getNome());
        }

        private Activity unwrap(Context context) {
            while (!(context instanceof Activity) && context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }

            return (Activity) context;
        }
    }
}
