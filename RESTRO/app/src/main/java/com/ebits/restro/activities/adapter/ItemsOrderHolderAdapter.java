package com.ebits.restro.activities.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ebits.restro.R;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.model.ItemOrderModel;
import com.ebits.restro.activities.utils.interfaces.CleanAdapterInterface;
import com.ebits.restro.activities.utils.tools.CircularWithoutNetworkImageView;
import com.ebits.restro.activities.utils.tools.lazyimageloader.ImageLoader;

import java.util.List;

/**
 * Created by Mr. Satyaki Mukherjee on 01/08/16.
 */
public class ItemsOrderHolderAdapter extends RecyclerView.Adapter<ItemsOrderHolderAdapter.MyViewHolder> {

    private Context mContext;
    private List<ItemOrderModel> itemOrderModelList;
    private double  priceValue  =   0.0;
    private int numberQty   =   0;
    public EditText desMsg;
    public ImageLoader imageLoader;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView recipeTextView, recipeRateTextView, recipeValueTextView, recipeQtyTextView,descriptionTextView,
                recipeId,chargableNonChargable;
        public CircularWithoutNetworkImageView foodImageView;
        public ImageButton plusQtyBtn,minusQtyBtn,descriptionBtn,nonChargableBtn,deleteBtn;


        public MyViewHolder(View view) {
            super(view);
            recipeTextView      = (TextView) view.findViewById(R.id.foodOrder_recipeTextView);
            recipeRateTextView  = (TextView) view.findViewById(R.id.foodOrder_recipeRateTextView);
            recipeValueTextView = (TextView) view.findViewById(R.id.foodOrder_recipeValueTextView);
            recipeQtyTextView   = (TextView) view.findViewById(R.id.foodOrder_recipeQtyTextView);
            descriptionTextView = (TextView) view.findViewById(R.id.foodOrder_descriptionTextView);
            recipeId            = (TextView) view.findViewById(R.id.foodOrder_recipeId);
            chargableNonChargable   =    (TextView) view.findViewById(R.id.foodOrder_chargableNonChargable);
            foodImageView       = (CircularWithoutNetworkImageView) view.findViewById(R.id.foodOrder_foodImageView);

            plusQtyBtn         = (ImageButton) view.findViewById(R.id.foodOrder_plusQtyBtn);
            minusQtyBtn        = (ImageButton) view.findViewById(R.id.foodOrder_minusQtyBtn);
            descriptionBtn     = (ImageButton) view.findViewById(R.id.foodOrder_descriptionBtn);
            nonChargableBtn    = (ImageButton) view.findViewById(R.id.foodOrder_nonChargableBtn);
            deleteBtn          = (ImageButton) view.findViewById(R.id.foodOrder_deleteBtn);
        }
    }


    public ItemsOrderHolderAdapter(Context mContext, List<ItemOrderModel> itemOrderModelList) {
        this.mContext       = mContext;
        this.itemOrderModelList  = itemOrderModelList;
        imageLoader =   new ImageLoader(mContext);
    }

    public void clearRecyclerView(CleanAdapterInterface cleanAdapterInterface) {
        int size = this.itemOrderModelList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                itemOrderModelList.remove(0);
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
                .inflate(R.layout.card_view_order_making_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final ItemOrderModel itemOrderModel = itemOrderModelList.get(position);


        holder.recipeTextView.setText(itemOrderModel.getItemName());
        holder.recipeId.setText(itemOrderModel.getItemNameId());
        holder.recipeRateTextView.setText(itemOrderModel.getItemRate());
        holder.recipeValueTextView.setText(itemOrderModel.getItemValue());
        holder.recipeQtyTextView.setText(itemOrderModel.getItemQty());
        holder.chargableNonChargable.setText(itemOrderModel.getItemChargaqbleOrNot());
        holder.descriptionTextView.setText(itemOrderModel.getItemDescription());
        holder.descriptionTextView.setSelected(true);

        /*ImageLoader imageLoader = RestroApplication.getInstance().getImageLoader();
        holder.foodImageView.setImageUrl(itemOrderModel.getItemImage().trim(),imageLoader);*/

        if(itemOrderModel.getItemImage().trim()!=null && !itemOrderModel.getItemImage().trim().equals("")){
            System.out.println("Image Url- "+itemOrderModel.getItemImage().trim());
            imageLoader.DisplayImage(itemOrderModel.getItemImage().trim(), holder.foodImageView);
            //holder.itemsImageView.setImageUrl(itemModel.getItemImage().trim(),imageLoader);
        } else {
            holder.foodImageView.setBackgroundResource(R.mipmap.ic_launcher);
        }

        holder.plusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                numberQty   =   Integer.parseInt(holder.recipeQtyTextView.getText().toString().trim());
                numberQty   =   numberQty+1;
                holder.recipeQtyTextView.setText(""+numberQty);
                itemOrderModel.setItemQty(String.valueOf(numberQty));
                priceValue  =   Double.parseDouble(holder.recipeRateTextView.getText().toString().trim());
                priceValue  =   priceValue  *   numberQty;
                holder.recipeValueTextView.setText(""+priceValue);
            }
        });

        holder.minusQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberQty   =   Integer.parseInt(holder.recipeQtyTextView.getText().toString().trim());
                if(numberQty<=1){
                    numberQty   =   1;
                    holder.recipeQtyTextView.setText(""+numberQty);
                    itemOrderModel.setItemQty(String.valueOf(numberQty));
                    priceValue  =   Double.parseDouble(holder.recipeRateTextView.getText().toString().trim());
                    priceValue  =   priceValue  *   numberQty;
                    holder.recipeValueTextView.setText(""+priceValue);
                }
                else {
                    numberQty   =   numberQty-1;
                    holder.recipeQtyTextView.setText(""+numberQty);
                    itemOrderModel.setItemQty(String.valueOf(numberQty));
                    priceValue  =   Double.parseDouble(holder.recipeRateTextView.getText().toString().trim());
                    priceValue  =   priceValue  *   numberQty;
                    holder.recipeValueTextView.setText(""+priceValue);
                }
            }
        });

        holder.nonChargableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int ncOrC   =   Integer.parseInt(holder.chargableNonChargable.getText().toString().trim());
                if(ncOrC==1){
                    holder.nonChargableBtn.setImageResource(R.drawable.cbtn_noncharge);
                    ncOrC=0;
                    holder.chargableNonChargable.setText(""+ncOrC);
                    itemOrderModel.setItemChargaqbleOrNot(""+ncOrC);
                }
                else{
                    holder.nonChargableBtn.setImageResource(R.drawable.cbtn);
                    ncOrC=1;
                    holder.chargableNonChargable.setText(""+ncOrC);
                    itemOrderModel.setItemChargaqbleOrNot(""+ncOrC);
                }
            }
        });

        holder.descriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialogDescription = new Dialog(mContext, R.style.TransparentProgressDialog);
                dialogDescription.setContentView(R.layout.description_dialog);
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogDescription.setCancelable(false);
                desMsg 	= (EditText) dialogDescription.findViewById(R.id.dialog_descriptionEditText);
                Button	cancelBtn	=	(Button) dialogDescription.findViewById(R.id.dialog_cancelBtn);
                Button saveBtn      = (Button) dialogDescription.findViewById(R.id.dialog_saveBtn);

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        dialogDescription.dismiss();
                    }
                });

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on click
                        holder.descriptionTextView.setText(""+desMsg.getText().toString().trim());
                        itemOrderModel.setItemDescription(""+desMsg.getText().toString().trim());
                        dialogDescription.dismiss();
                    }
                });

                dialogDescription.show();
            }


        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(itemOrderModel);
            }


        });
    }


    @Override
    public int getItemCount() {
        return itemOrderModelList.size();
    }


    /*// Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, ItemModel data) {
        itemModelList.add(position, data);
        notifyItemInserted(position);
    }*/

    // Remove a RecyclerView item containing a specified Data object
    public void remove(ItemOrderModel data) {
        int position = itemOrderModelList.indexOf(data);
        itemOrderModelList.remove(position);
        notifyItemRemoved(position);
    }
}
