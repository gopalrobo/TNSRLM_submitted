package cst.smart.tnsrlm.plf;

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
import cst.smart.tnsrlm.farmerdetails.FarmerRegister;


public class MasterAllPlfAdapter extends RecyclerView.Adapter<MasterAllPlfAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<PLF> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView conno, consecretaryname, consecretarymob;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            conno = (TextView) view.findViewById(R.id.conno);
            consecretaryname = (TextView) view.findViewById(R.id.consecretaryname);
            consecretarymob = (TextView) view.findViewById(R.id.consecretarymob);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllPlfAdapter(Context mainActivityUser, ArrayList<PLF> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<PLF> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confsurvey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        PLF PLF = moviesList.get(position);
        holder.conno.setText(String.valueOf(position + 1));
        holder.consecretaryname.setText(PLF.getPlfName());
        holder.consecretarymob.setText(PLF.getContact());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, PLFRegistration.class);
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
