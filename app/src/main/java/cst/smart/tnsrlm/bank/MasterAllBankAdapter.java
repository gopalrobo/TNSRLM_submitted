package cst.smart.tnsrlm.bank;

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


public class MasterAllBankAdapter extends RecyclerView.Adapter<MasterAllBankAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<BankDetails> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView bankno, bankname, bankbranch;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            bankno = (TextView) view.findViewById(R.id.bankno);
            bankname = (TextView) view.findViewById(R.id.bankname);
            bankbranch = (TextView) view.findViewById(R.id.bankbranch);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllBankAdapter(Context mainActivityUser, ArrayList<BankDetails> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<BankDetails> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banksurvey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        BankDetails bankDetails = moviesList.get(position);
        holder.bankno.setText(String.valueOf(position + 1));
        holder.bankname.setText(bankDetails.getBankname());
        holder.bankbranch.setText(bankDetails.getBankbranch());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, BankDetailsRegister.class);
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
