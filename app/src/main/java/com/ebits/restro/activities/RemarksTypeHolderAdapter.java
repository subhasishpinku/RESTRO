package com.ebits.restro.activities;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ebits.restro.R;

import java.util.List;

/**
 * Created by Admin on 11/12/2017.
 */

public class RemarksTypeHolderAdapter extends RecyclerView.Adapter<RemarksTypeHolderAdapter.MyViewHolder> {
    private Context mContext;
    private List<Remarks_set_Get> remarks_set_gets;
    int type = -1;

    public RemarksTypeHolderAdapter(Context mContext, List<Remarks_set_Get> remarks_set_gets) {
        this.mContext   = mContext;
        this.remarks_set_gets  = remarks_set_gets;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView, idView;
        public CardView card_view_remarks;

        public MyViewHolder(View view) {
            super(view);
            nameView     = (TextView) view.findViewById(R.id.RemarksType_name);
            idView       = (TextView) view.findViewById(R.id.RemarksType_id);
            card_view_remarks   = (CardView) view.findViewById(R.id.card_view_remarks);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_recyclerview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Remarks_set_Get obj = remarks_set_gets.get(position);
        holder.nameView.setText(obj.getName());
//        holder.nameView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                type = position;
//                notifyDataSetChanged();
//
//
//            }
//        });
//
//        if  (type == position){
//            holder.card_view_remarks.setBackgroundColor(Color.parseColor("#FFFF00"));
//
//        }
//
//        else {
//
//            holder.card_view_remarks.setBackgroundColor(Color.parseColor("#179EDE"));
//
//        }
//

    }

    @Override
    public int getItemCount() {
        return remarks_set_gets.size();
    }


}
