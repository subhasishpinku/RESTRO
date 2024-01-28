package com.ebits.restro.activities;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ebits.restro.R;
import com.ebits.restro.activities.model.TableModel;

import java.util.List;

/**
 * Created by Admin on 11/11/2017.
 */

public class TableHolderAdapter_new extends RecyclerView.Adapter<TableHolderAdapter_new.MyViewHolder> implements View.OnTouchListener {
    private Context mContext;
    private List<String> list;
    private List<TableModel> tableList;
    private String  tableId="";
    int type=-1;
    int typee=0;
    private Listener listener;
    private OnStartDragListener mDragStartListener;

    public TableHolderAdapter_new(OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

    }


    public TableHolderAdapter_new(Context mContext, List<TableModel> tableList) {
        this.mContext   = mContext;
        this.tableList  = tableList;
        this.listener = listener;
    }

    public TableHolderAdapter_new(List<String> list, Listener listener) {
        this.list = list;
        this.listener = listener;
    }




    public List<String> getList() {
        return list;
    }
    public void updateList(List<String> list) {
        this.list = list;
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0);
                } else {
                    v.startDrag(data, shadowBuilder, v, 0);
                }
                return true;
        }
        return false;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableCategory, tableid;
        public Button tableName;
        public View frameLayout;
        public CardView cardview1;

        public MyViewHolder(View view) {
            super(view);
            tableName       = (Button) view.findViewById(R.id.cardmenu_tableName);
            tableCategory   = (TextView) view.findViewById(R.id.cardmenu_tableCategory);
            tableid         = (TextView) view.findViewById(R.id.cardmenu_tableid);
            cardview1       = (CardView)view.findViewById(R.id.cardview1);
         //   ButterKnife.bind(this, view);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_grid_new, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
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
               System.out.println("check");
              type = position;
              notifyDataSetChanged();


            }
        });

        if  (type == position){
                holder.cardview1.setBackgroundColor(Color.parseColor("#FF00A7"));

        }

        else {

           holder.cardview1.setBackgroundColor(Color.parseColor("#179EDE"));

        }
    }


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,String tableId, String tableStatus) {
       //  inflate menu
         this.tableId =   tableId;
          PopupMenu popup = new PopupMenu(mContext, view);
          MenuInflater inflater = popup.getMenuInflater();
        if(tableStatus.trim().equalsIgnoreCase("AVALABLE")) {
            inflater.inflate(R.menu.table_menu_avail_tabletransfer, popup.getMenu());
           popup.setOnMenuItemClickListener(new MyMenuItemClickListenerAvailable());
        }
//
        else{
            inflater.inflate(R.menu.table_menu_book, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListenerBook());
        }
        popup.show();

    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListenerBook implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListenerBook() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                /*case R.id.book_add_item:
                    Toast.makeText(mContext, "Add Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;*/
                case R.id.book_update_item:
                    Intent myIntent =   new Intent(mContext, EditOrderTakingActivity.class);
                    Bundle bundle  =   new Bundle();
                    bundle.putString("TABLE_NO",tableId);
                    myIntent.putExtras(bundle);
                    mContext.startActivity(myIntent);
                    Toast.makeText(mContext, "Edit Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.book_table_status:
                    Toast.makeText(mContext, "Table Status "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListenerAvailable implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListenerAvailable() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.avail_add_item_transfer:
                    Intent myIntent =   new Intent(mContext, EditOrderTakingActivity_Edit.class);
                    Bundle  bundle  =   new Bundle();
                    bundle.putString("TABLE_NO",tableId);
                    myIntent.putExtras(bundle);
                    mContext.startActivity(myIntent);
                    Toast.makeText(mContext, "Table Transfer "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }


}
