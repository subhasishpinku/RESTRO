package com.ebits.restro.activities.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.update_set_get;

import java.util.List;

/**
 * Created by Admin on 13/11/2017.
 */

public class TableHolderAdapter_kot_add extends RecyclerView.Adapter<TableHolderAdapter_kot_add.MyViewHolder> {
    private Context mContext;
    private List<update_set_get> itemOrderList1;
    private String  tableId="";
    int type=0;

    public TableHolderAdapter_kot_add(Context mContext, List<update_set_get> itemOrderList1) {
        this.mContext   = mContext;
        this.itemOrderList1  = itemOrderList1;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableCategory, tableid;
        public Button tableName;
        public CardView cardview;
        public CheckBox checkbox2;

        public MyViewHolder(View view) {
            super(view);
            tableName       = (Button) view.findViewById(R.id.cardmenu_tableName);
            tableCategory   = (TextView) view.findViewById(R.id.cardmenu_tableCategory);
            tableid         = (TextView) view.findViewById(R.id.cardmenu_tableid);
            cardview        = (CardView)view.findViewById(R.id.cardview);
            checkbox2       = (CheckBox)view.findViewById(R.id.checkbox2);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_grid1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

     final update_set_get update_set_get = itemOrderList1.get(position);
      holder.tableName.setText(update_set_get.getTablenum());
      holder.tableCategory.setText(update_set_get.getKotnumber());
      holder.cardview.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              type = position;
              notifyDataSetChanged();

          }
      });

    }

    @Override
    public int getItemCount() {
        return itemOrderList1.size();
    }


}
