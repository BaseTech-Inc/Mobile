package com.example.tupa_mobile.Rides;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tupa_mobile.Alerts.AlerBairro;
import com.example.tupa_mobile.Markers.MarkerPoint;
import com.example.tupa_mobile.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
        private WebView webview;
        public RidesHolder(@NonNull View itemView) {
            super(itemView);
//            webview = itemView.findViewById(R.id.webview);
//            webview.setWebViewClient(new WebViewClient());
//            webview.loadUrl("http://www.google.com");
            txtDistance = itemView.findViewById(R.id.txtDistance);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNumberEvents = itemView.findViewById(R.id.txtNumberEvents);


        }

        void setDetails(Rides rides){
            Random rand = new Random();
            txtDistance.setText(rides.getDistanciaPercurso() + " km");
            //int time = (int) (Float.parseFloat(rides.getTempoChegada()) - Float.parseFloat(rides.getTempoPartida()));

            String[] c = rides.getTempoChegada().split("T");
            String dataride = c[0].replace("-","/");
            String d = c[1];
            String[] e = d.split(":");
            txtTime.setText(" ("+e[0] + "h:" + e[1]+ "min)");

//          
//            String[] x = rides.getRota().split("!1d");
//            String g = x[1];
//            String[] b = g.split("!2d");
//            String latitudePartida = b[0];
//            String j = b[1];
//            String[] l = j.split("!");
//            String longitudePartida = l[0];
//            String k = l[2];
//            String[] h = k.split("!2d");
//            String latitudeChegada = h[0];
//            String longitudeChegada = h[1];
            int n = rand.nextInt(12);
            txtNumberEvents.setText( n +"");

            String j = rides.getRota();

//            String longitudeChegada, latitudeChegada, longitudePartida,latitudePartida;
//            String htmlDocument = "<html><body>\"https://www.google.com/maps/embed?pb=!1m11!4m9!3e0!4m3!3m2!1d" + latitudePartida + "!2d" + longitudePartida + "!4m3!3m2!1d" + latitudeChegada + "!2d" + longitudeChegada + "!5e0\"</body></html>";
//          webview.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);
//           webview.getSettings().setJavaScriptEnabled(true);
        }
    }


}
