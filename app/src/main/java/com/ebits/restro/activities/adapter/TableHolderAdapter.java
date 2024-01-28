package com.ebits.restro.activities.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.EditOrderTakingActivity_Add_Kot;
import com.ebits.restro.activities.ItemTransfer;
import com.ebits.restro.activities.Kot_updation;
import com.ebits.restro.activities.OrderTakingActivityNew;
import com.ebits.restro.activities.TableStatus;
import com.ebits.restro.activities.TableTransfer;
import com.ebits.restro.activities.model.TableModel;

import java.util.Calendar;
import java.util.List;

import static com.ebits.restro.R.id.book_update_item;

/**
 * Created by Mr. Satyaki Mukherjee on 18/07/16.
 */
public class TableHolderAdapter extends RecyclerView.Adapter<TableHolderAdapter.MyViewHolder>{

    private Context mContext;
    private List<TableModel> tableList;
    private String  tableId="";
    int type=0;
    int typee=0;

    public TableHolderAdapter(List<TableModel> tableList) {
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tableCategory, tableid;
        public Button   tableName;
        public CardView cardview1;
        public MyViewHolder(View view) {
            super(view);
            tableName       = (Button) view.findViewById(R.id.cardmenu_tableName);
            tableCategory   = (TextView) view.findViewById(R.id.cardmenu_tableCategory);
            tableid         = (TextView) view.findViewById(R.id.cardmenu_tableid);
            cardview1       = (CardView)view.findViewById(R.id.cardview1);
        }
    }


    public  TableHolderAdapter(Context mContext, List<TableModel> tableList) {
        this.mContext   = mContext;
        this.tableList  = tableList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_grid_newnew, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
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
                showPopupMenu(holder.tableName,holder.tableName.getText().toString().trim(),holder.tableid.getText().toString().trim());
           //   showPopupMenuadd(holder.tableName,holder.tableName.getText().toString().trim(),holder.tableid.getText().toString().trim());


            }
        });

//        holder.cardview1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showPopupMenu(holder.tableName,holder.tableName.getText().toString().trim(),holder.tableid.getText().toString().trim());
//                //   showPopupMenuadd(holder.tableName,holder.tableName.getText().toString().trim(),holder.tableid.getText().toString().trim());
//            }
//        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,String tableId, String tableStatus) {
        // inflate menu
        this.tableId =   tableId;
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        if(tableStatus.trim().equalsIgnoreCase("AVALABLE")) {
            inflater.inflate(R.menu.table_menu_avail, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListenerAvailable());
            Calendar cal = Calendar.getInstance();
            System.out.println("INSIDE PREPARE TABLE click" +cal.getTime());


        }

        else{
            inflater.inflate(R.menu.table_menu_book, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListenerBook());
          //  popup.setOnDismissListener(new MyDismissListener() );


        }

        popup.show();



    }



    class MyMenuItemClickListenerBook implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListenerBook() {

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                /*case R.id.book_add_item:
                    Toast.makeText(mContext, "Add Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;*/
                case book_update_item:
                    Intent editIntent =   new Intent(mContext, Kot_updation.class);
                    Bundle  bundle_edit  =   new Bundle();
                    bundle_edit.putString("TABLE_NO",tableId);
                    editIntent.putExtras(bundle_edit);
                    mContext.startActivity(editIntent);
            //        Toast.makeText(mContext, "Update Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.table_transfer:
                    Intent addIntent = new Intent(mContext, TableTransfer.class);
                    Bundle  bundle_add  =   new Bundle();
                    bundle_add.putString("TABLE_NO",tableId);
                    addIntent.putExtras(bundle_add);
                    mContext.startActivity(addIntent);
              //      Toast.makeText(mContext, "Table Transfer "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.book_add_item:
                    Intent addIntent1 = new Intent(mContext, EditOrderTakingActivity_Add_Kot.class);
                    Bundle  bundle_add1  =   new Bundle();
                    bundle_add1.putString("TABLE_NO",tableId);
                    addIntent1.putExtras(bundle_add1);
                    mContext.startActivity(addIntent1);
               //     Toast.makeText(mContext, "Add Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.item_transfer:
                    Intent addIntent_item = new Intent(mContext, ItemTransfer.class);
                    Bundle  bundle_add_item  =   new Bundle();
                    bundle_add_item.putString("TABLE_NO",tableId);
                    addIntent_item.putExtras(bundle_add_item);
                    mContext.startActivity(addIntent_item);
                 //   Toast.makeText(mContext, "Add Item "+tableId, Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.book_table_status:
//                    Toast.makeText(mContext, "Table Status "+tableId, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, TableStatus.class);
                    Bundle table_status = new Bundle();
                    table_status.putString("TABLE_NO",tableId);
                    intent.putExtras(table_status);
                    mContext.startActivity(intent);
                 //   Toast.makeText(mContext, "Table Status"+tableId, Toast.LENGTH_SHORT).show();

                    return true;
                default:
            }
            return false;

        }


    }


    class MyMenuItemClickListenerAvailable implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListenerAvailable() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.avail_add_item:
                    if (menuItem.getItemId()==R.id.avail_add_item) {
                        if (typee == 0) {
                            Intent myIntent = new Intent(mContext, OrderTakingActivityNew.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("TABLE_NO", tableId);
                            myIntent.putExtras(bundle);
                            mContext.startActivity(myIntent);
                       //     Toast.makeText(mContext, "Add Item " + tableId, Toast.LENGTH_SHORT).show();
//
                            return true;
                        }
                    }

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
