package com.ebits.restro.activities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.utils.interfaces.CleanAdapterInterface;
import com.ebits.restro.activities.utils.tools.CircularWithoutNetworkImageView;
import com.ebits.restro.activities.utils.tools.lazyimageloader.ImageLoader;

import java.util.List;

/**
 * Created by Mr. Satyaki Mukherjee on 01/08/16.
 */
public class ItemsHolderAdapter extends RecyclerView.Adapter<ItemsHolderAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemModel> itemModelList;
    public ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView menuItemName, menuItemId, menuRate, itemImage;
        public CircularWithoutNetworkImageView itemsImageView;
        public RelativeLayout   relative;

        public MyViewHolder(View view) {
            super(view);


            menuItemName     = (TextView) view.findViewById(R.id.foodOrder_menuItemName);
            menuItemId       = (TextView) view.findViewById(R.id.foodOrder_menuItemId);
            menuRate         = (TextView) view.findViewById(R.id.foodOrder_menuRate);
            itemImage        = (TextView) view.findViewById(R.id.foodOrder_itemImage);
            itemsImageView   = (CircularWithoutNetworkImageView) view.findViewById(R.id.foodOrder_itemsImageView);
            relative         = (RelativeLayout) view.findViewById(R.id.foodOrder_relative);
        }
    }


    public ItemsHolderAdapter(Context mContext, List<ItemModel> itemModelList) {
        this.mContext       = mContext;
        this.itemModelList  = itemModelList;
        imageLoader =   new ImageLoader(mContext);
    }

    public void clearRecyclerView(CleanAdapterInterface cleanAdapterInterface) {
        int size = this.itemModelList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                itemModelList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
            cleanAdapterInterface.cleanStatus(true);
        }
        else
            cleanAdapterInterface.cleanStatus(false);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_menu_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ItemModel itemModel = itemModelList.get(position);


        holder.menuItemName.setText(itemModel.getItemName());
        holder.menuItemId.setText(itemModel.getItemNameId());
        holder.menuRate.setText(itemModel.getItemRate());
        holder.itemImage.setText(itemModel.getItemImage());

        /*ImageLoader imageLoader = RestroApplication.getInstance().getImageLoader();
        holder.itemsImageView.setImageUrl(null,imageLoader);*/

        if(itemModel.getItemImage().trim()!=null && !itemModel.getItemImage().trim().equals("")){
            imageLoader.DisplayImage(itemModel.getItemImage().trim(), holder.itemsImageView);
            //holder.itemsImageView.setImageUrl(itemModel.getItemImage().trim(),imageLoader);
        } else {
            holder.itemsImageView.setBackgroundResource(R.mipmap.ic_launcher);
        }

    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    /*// Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, ItemModel data) {
        itemModelList.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(ItemModel data) {
        int position = itemModelList.indexOf(data);
        itemModelList.remove(position);
        notifyItemRemoved(position);
    }*/
}
