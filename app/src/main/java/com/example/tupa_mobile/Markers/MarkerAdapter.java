package com.example.tupa_mobile.Markers;

import static com.example.tupa_mobile.Fragments.MapFragment.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Connections.Connection;
import com.example.tupa_mobile.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
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
        holder.setDetails(markerData, position);
    }

    @Override
    public int getItemCount() {
        return this.markersData.size();
    }

    public class MarkerHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerView;
        private MarkerAdapter adapter;
        private ViewGroup searchLayout;
        private CardView drawerCard;
        private ImageView icoMarker;
        private TextView markerName, markerRegion;
        private EditText etTo;

        public MarkerHolder(@NonNull View view) {
            super(view);
            searchLayout = unwrap(context).findViewById(R.id.searchLayout);
            recyclerView = unwrap(context).findViewById(R.id.savedLocationsRecycler);
            adapter = (MarkerAdapter) recyclerView.getAdapter();
            drawerCard = view.findViewById(R.id.drawerCard);
            icoMarker = view.findViewById(R.id.icoLocation);
            markerName = view.findViewById(R.id.txtLocationAddress);
            markerRegion = view.findViewById(R.id.txtLocationRegion);
        }

        public void setDetails(MarkersData markerData, int position) {

            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> addresses = geocoder.getFromLocation(markerData.getPonto().getLatitude(), markerData.getPonto().getLongitude(), 1);
                String street = addresses.get(0).getThoroughfare();
                String stNumber = addresses.get(0).getSubThoroughfare();
                markerRegion.setText(String.format("%s, %s", street, stNumber));
            } catch (Exception e) {
                markerRegion.setText(markerData.getNome());
                Log.e("TAG", e.getMessage());
            }

            drawerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(v, markerData);
                }
            });

            drawerCard.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onItemLongClick(v, markerData, clickListener, position);
                    return true;
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
                    icoMarker.setImageResource(R.drawable.ic_marker);
            }

            markerName.setText(markerData.getNome());

        }

        private Activity unwrap(Context context) {
            while (!(context instanceof Activity) && context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            }

            return (Activity) context;
        }
    }
}
