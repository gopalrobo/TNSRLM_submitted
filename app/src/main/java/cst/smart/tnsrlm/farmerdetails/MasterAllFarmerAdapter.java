package cst.smart.tnsrlm.farmerdetails;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cst.smart.tnsrlm.R;


public class MasterAllFarmerAdapter extends RecyclerView.Adapter<MasterAllFarmerAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Farmer> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView comno,comfarmername,comhusband;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            comno = (TextView) view.findViewById(R.id.comno);
            comfarmername = (TextView) view.findViewById(R.id.comfarmername);
            comhusband = (TextView) view.findViewById(R.id.comhusband);

            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }



    public MasterAllFarmerAdapter(Context mainActivityUser, ArrayList<Farmer> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<Farmer> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farmer_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Farmer farmer = moviesList.get(position);
        holder.comno.setText(String.valueOf(position+1));
        holder.comfarmername.setText(farmer.getFarmerName());
        holder.comhusband.setText(farmer.getHusbandFatherName());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, FarmerRegister.class);
                intent.putExtra("object", moviesList.get(position));
                mainActivityUser.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public static String round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return String.valueOf((double) tmp / factor);
    }

}
