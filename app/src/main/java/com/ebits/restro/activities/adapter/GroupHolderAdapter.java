package com.ebits.restro.activities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.ebits.restro.R;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.GroupModel;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.utils.interfaces.CleanAdapterInterface;
import com.ebits.restro.activities.utils.tools.CircularImageView;

import java.util.List;

/**
 * Created by Mr. Satyaki Mukherjee on 07/08/16.
 */
public class GroupHolderAdapter extends RecyclerView.Adapter<GroupHolderAdapter.MyViewHolder> {

    private Context mContext;
    private List<GroupModel> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView, idView;
        public CircularImageView    imageView;

        public MyViewHolder(View view) {
            super(view);
            nameView     = (TextView) view.findViewById(R.id.group_name);
            idView       = (TextView) view.findViewById(R.id.group_id);
            imageView   = (CircularImageView) view.findViewById(R.id.group_imageView);
        }
    }


    public GroupHolderAdapter(Context mContext, List<GroupModel> list) {
        this.mContext       = mContext;
        this.list  = list;
    }

    public void clearRecyclerView(CleanAdapterInterface cleanAdapterInterface) {
        int size = this.list.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                list.remove(0);
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
                .inflate(R.layout.card_view_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        GroupModel model = list.get(position);


        holder.nameView.setText(model.getGroupName());
        holder.idView.setText(model.getGroupId());

        ImageLoader imageLoader = RestroApplication.getInstance().getImageLoader();

        holder.imageView.setImageUrl(model.getGroupImage().trim(),imageLoader);

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
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
