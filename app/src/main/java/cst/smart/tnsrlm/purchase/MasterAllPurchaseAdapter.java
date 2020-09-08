package cst.smart.tnsrlm.purchase;

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


public class MasterAllPurchaseAdapter extends RecyclerView.Adapter<MasterAllPurchaseAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Purchase> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView purno, purcrops, purname;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            purno = (TextView) view.findViewById(R.id.purno);
            purcrops = (TextView) view.findViewById(R.id.purcrops);
            purname = (TextView) view.findViewById(R.id.purname);
            editImage = (ImageView) view.findViewById(R.id.editImage);
            locationImage = (ImageView) view.findViewById(R.id.locationImage);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllPurchaseAdapter(Context mainActivityUser, ArrayList<Purchase> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<Purchase> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchasesurvey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Purchase purchase = moviesList.get(position);
        holder.purno.setText(String.valueOf(position + 1));
        holder.purcrops.setText(purchase.getCrops());
        holder.purname.setText(purchase.getVariety());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, PurchaseRegistration.class);
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
