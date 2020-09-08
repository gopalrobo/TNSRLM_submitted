package cst.smart.tnsrlm.employeeDetails;

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


public class MasterAllEmployeeAdapter extends RecyclerView.Adapter<MasterAllEmployeeAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Employee> moviesList;
    SharedPreferences sharedpreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView empno, empname, empresponsive;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            empno = (TextView) view.findViewById(R.id.empno);
            empname = (TextView) view.findViewById(R.id.empname);
            empresponsive = (TextView) view.findViewById(R.id.empresponsive);
            editImage = (ImageView) view.findViewById(R.id.editImage);
            locationImage = (ImageView) view.findViewById(R.id.locationImage);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public MasterAllEmployeeAdapter(Context mainActivityUser, ArrayList<Employee> moviesList) {
        this.moviesList = moviesList;
        this.mainActivityUser = mainActivityUser;

    }

    public void notifyData(ArrayList<Employee> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.moviesList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.empsurvey_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Employee employee = moviesList.get(position);
        holder.empno.setText(String.valueOf(position + 1));
        holder.empname.setText(employee.getEmployeeName());
        holder.empresponsive.setText(employee.getResponsibility());

        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivityUser, EmployeeRegister.class);
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
