package com.ebits.restro.activities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.model.TableModel;

import java.util.List;

/**
 * Created by Admin on 13/11/2017.
 */

public class TableHolderAdapter_booked extends RecyclerView.Adapter<TableHolderAdapter_booked.MyViewHolder> {
    private Context mContext;
    private List<TableModel> tableList;
    private String  tableId="";
    int type=0;
    int typee=0;

    public TableHolderAdapter_booked(List<TableModel> tableList) {
        this.mContext   = mContext;
        this.tableList  = tableList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableCategory, tableid;
        public Button tableName;

        public MyViewHolder(View view) {
            super(view);
            tableName       = (Button) view.findViewById(R.id.cardmenu_tableName);
            tableCategory   = (TextView) view.findViewById(R.id.cardmenu_tableCategory);
            tableid         = (TextView) view.findViewById(R.id.cardmenu_tableid);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_grid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TableModel tableModel = tableList.get(position);

        if(tableModel.getTableStatus().trim().equalsIgnoreCase("AVALABLE")){
            holder.tableName.setBackgroundResource(R.drawable.circle_green);
        }
        else
            holder.tableName.setBackgroundResource(R.drawable.circle_red);

        holder.tableName.setText(tableModel.getTableId());
        holder.tableCategory.setText(tableModel.getTableCategory());
        holder.tableid.setText(tableModel.getTableStatus());

        holder.tableName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.tableName,holder.tableName.getText().toString().trim(),holder.tableid.getText().toString().trim());

            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }
}
