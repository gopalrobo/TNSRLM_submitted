package cst.smart.tnsrlm.farmerdetails;

import android.content.Context;
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


public class FarmAdapter extends RecyclerView.Adapter<FarmAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<Farm> farmList;

    private FarmOnItemClick farmOnItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView cultivatedcrop,cultivatedcropone,cultivatedcroptwo;
        ImageView editImage, locationImage;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super(view);
            cultivatedcrop = (TextView) view.findViewById(R.id.cultivatedcrop);
            cultivatedcropone = (TextView) view.findViewById(R.id.cultivatedcropone);
            cultivatedcroptwo = (TextView) view.findViewById(R.id.cultivatedcroptwo);
            editImage = (ImageView) view.findViewById(R.id.editImage);
            locationImage = (ImageView) view.findViewById(R.id.locationImage);
            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);
        }
    }


    public FarmAdapter(Context mainActivityUser, ArrayList<Farm> moviesList, FarmOnItemClick farmOnItemClick) {
        this.farmList = moviesList;
        this.mainActivityUser = mainActivityUser;
        this.farmOnItemClick = farmOnItemClick;

    }

    public void notifyData(ArrayList<Farm> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.farmList = myList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.farm_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Farm bean = farmList.get(position);
        holder.cultivatedcrop.setText(bean.cultivatedCrop1);
        holder.cultivatedcropone.setText(bean.cultivatedCrop2);
        holder.cultivatedcroptwo.setText(bean.cultivatedCrop3);
        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farmOnItemClick.itemFarmClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }
}
