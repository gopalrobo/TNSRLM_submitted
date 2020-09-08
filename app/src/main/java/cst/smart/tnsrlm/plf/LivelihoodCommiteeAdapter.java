package cst.smart.tnsrlm.plf;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cst.smart.tnsrlm.R;

/**
 * Created by admin on 03-12-2018.
 */

public class LivelihoodCommiteeAdapter extends RecyclerView.Adapter<LivelihoodCommiteeAdapter.MyViewHolder> {

    private Context mainActivityUser;
    private ArrayList<LivelihoodCommitee> confedlist;
    private OnItemClick onItemClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView descriptionone, descriptiontwo, descriptionthree;
        LinearLayout parentLinear;

        public MyViewHolder(View view) {
            super((view));
            descriptionone = (TextView) view.findViewById(R.id.descriptionone);
            descriptiontwo = (TextView) view.findViewById(R.id.descriptiontwo);
            descriptionthree = (TextView) view.findViewById(R.id.descriptionthree);


            parentLinear = (LinearLayout) view.findViewById(R.id.parentLinear);

        }
    }

    public LivelihoodCommiteeAdapter(Context mainActivityUser, ArrayList<LivelihoodCommitee> confedlist, PLFRegistration onItemClick) {
        this.confedlist = confedlist;
        this.mainActivityUser = mainActivityUser;
        this.onItemClick = onItemClick;
    }

    public void notifyData(ArrayList<LivelihoodCommitee> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.confedlist = myList;
        notifyDataSetChanged();
    }

    public LivelihoodCommiteeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.confed_list_row, parent, false);

        return new LivelihoodCommiteeAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(LivelihoodCommiteeAdapter.MyViewHolder holder, final int position) {
        LivelihoodCommitee bean = confedlist.get(position);
        holder.descriptionone.setText(bean.getDescriptionone());
        holder.descriptiontwo.setText(bean.getDescriptiontwo());
        holder.descriptionthree.setText(bean.getDescriptionthree());


        if (position % 2 == 0) {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.viewBg));
        } else {
            holder.parentLinear.setBackgroundColor(mainActivityUser.getResources().getColor(R.color.white));
        }

        holder.parentLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.itemLivelihoodClick(position);

            }
        });
    }

    public int getItemCount() {
        return confedlist.size();
    }

}
