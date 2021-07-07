package com.example.tupa_mobile.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.R;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder>{

    private Context context;
    private ArrayList<Address> addresses;

    public AddressAdapter(Context context, ArrayList<Address> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        return new AddressHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        Address address = addresses.get(position);
        holder.setDetails(address);
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }


    public class AddressHolder extends RecyclerView.ViewHolder {

        TextView txtLocationAddress, txtLocationRegion;
        ImageView icoClock;

        public AddressHolder(View view) {
            super(view);

            txtLocationAddress = view.findViewById(R.id.txtLocationAddress);
            txtLocationRegion = view.findViewById(R.id.txtLocationRegion);
            icoClock = view.findViewById(R.id.icoClock);
        }

        public void setDetails(Address address) {

            txtLocationAddress.setText(address.getAddress());
            txtLocationRegion.setText(address.getRegion());
            icoClock.setImageResource(address.getType());

        }
    }
}
