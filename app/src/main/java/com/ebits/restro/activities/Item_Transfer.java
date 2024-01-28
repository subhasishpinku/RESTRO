package com.ebits.restro.activities;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ebits.restro.R;

import java.util.List;

/**
 * Created by Admin on 24/11/2017.
 */

public class Item_Transfer extends RecyclerView.Adapter<Item_Transfer.MyViewHolder> {
    private Context mContext;
   private List<update_set_get> itemOrderList1;
    int type=-1;
    private String  tableId="";

    public Item_Transfer(Context mContext, List<update_set_get> itemOrderList1) {
        this.mContext   = mContext;
        this.itemOrderList1  = itemOrderList1;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name, item_qty;
        public CheckBox checkbox1;
        public CardView card_view11;

        public MyViewHolder(View view) {
            super(view);

            item_name   = (TextView) view.findViewById(R.id.item_name);
            item_qty         = (TextView) view.findViewById(R.id.item_qty);
            checkbox1 = (CheckBox)view.findViewById(R.id.checkbox1);
            card_view11 = (CardView)view.findViewById(R.id.card_view11);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_order_making_item_order_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final int pos = position;
        final update_set_get obj = itemOrderList1.get(position);
        holder.item_name.setText(obj.getItemdescrr());
        holder.item_qty.setText(obj.getQty_kot());
        holder.checkbox1.setChecked(itemOrderList1.get(position).isSelected());
        holder.checkbox1.setTag(itemOrderList1.get(position));


        holder.checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               CheckBox cb = (CheckBox) v;
                update_set_get obj = (update_set_get) cb.getTag();
                obj.setSelected(cb.isChecked());
                itemOrderList1.get(pos).setSelected(cb.isChecked());
//                type = position;
//                notifyDataSetChanged();

            }
        });

        holder.card_view11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "LONG CLICK : " +position, Toast.LENGTH_SHORT).show();
//                type = position;
//                notifyDataSetChanged();
//                if  (type == position){
//            holder.card_view11.setBackgroundColor(Color.parseColor("#FF00A7"));
//
//              }
//                else {
//
//            holder.card_view11.setBackgroundColor(Color.parseColor("#179EDE"));
//
//            }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemOrderList1.size();
    }


}
