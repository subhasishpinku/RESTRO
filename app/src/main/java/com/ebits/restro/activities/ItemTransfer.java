package com.ebits.restro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.ItemsHolderAdapter;
import com.ebits.restro.activities.adapter.ItemsOrderHolderAdapter;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemEditOrderModel;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.model.ItemOrderModel;
import com.ebits.restro.activities.model.ItemOrderModel_new;
import com.ebits.restro.activities.model.TableModel;
import com.ebits.restro.activities.utils.CustomDialogClass;
import com.ebits.restro.activities.utils.JsonParsingUtils;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
import com.ebits.restro.activities.utils.tools.GeneralOperationTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 23/11/2017.
 */

public class ItemTransfer extends AppCompatActivity implements CallbackItemTouch{
    private TextView userNameTextView, tableNoTextView, sessionNameTextView, outletNameTextView, loginTimeTextView;
    private TextView toolbar_tableNoTextView, toolbar_timeTextView, toolbar_sessionTextView;
    private NetworkImageView profileImageView;
    private LinearLayout toolbar_linearParent;
    private CoordinatorLayout coordinatorLayout;
    private SharedPreferences sp;
    private GeneralOperationTools generalOperationTools;
    private ItemsHolderAdapter itemHolderAdapter;
    private List<ItemModel> itemList;
    private ItemsOrderHolderAdapter itemOrderHolderAdapter;
    private List<ItemOrderModel> itemOrderList;
    private List<ItemOrderModel_new> itemOrderListnew;
    private Context context = this;
    private String tableNo="",kotNumber="",formattedDate="";
    private int size=0;
    private int sizenew=3;
    private int sizee=1;
    private boolean onSaveFlag=true;
    int tableview = -1;
    int itemview = -1;
    ArrayList<TableModel> arrlist = new ArrayList<TableModel>();
    ArrayList<TableModel> arrlist1 = new ArrayList<TableModel>();
    ArrayList<TableModel> Selectedarrlist1 = new ArrayList<TableModel>();
    private String SelectedItem = "";
    private String itemdeser = "";
    private String ItemCheckStatus="YES";

    /***
     * Order making items section
     ***/
    private EditText searchItemsEdit;
    private Button clearSearchText;
    private ImageButton searchButton, expandButton;
    private RecyclerView recycler_view_search_item;

    /***
     * Order making section
     ***/
    private EditText addRemarksEditText;
    private ImageButton lockBtn, saveBtn, cancelBtn;
    private RecyclerView order_recycler_view_checkbox;
    private Object v;
    ImageButton foodOrder_saveBtn;
    String kotnumber_new;
    //    int k;
    String ITEMCD, ITEMDESCR,QTY,RATE,VALUE;
    private String  outletData, outletCode, currentDate;
    private String status;
    private TableHolderAdapter_Item tableHolderAdapter_transfer_item;
    private Item_Transfer itemTransferr;
    private ArrayList<update_set_get> itemOrderList1;
    CheckBox checkbox1_all;
    CheckBox checkbox1;
    private ArrayList<TableModel> tableList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemtransfer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_linearParent = (LinearLayout) findViewById(R.id.toolbar_linearParent);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        initCollapsingToolbar();

        sp = getSharedPreferences(Consts.SP_NAME, MODE_PRIVATE);

        /***
         * For collapsing tool bar
         */
        userNameTextView = (TextView) findViewById(R.id.userNameTextView);
        tableNoTextView = (TextView) findViewById(R.id.tableNoTextView);
        sessionNameTextView = (TextView) findViewById(R.id.sessionNameTextView);
        outletNameTextView = (TextView) findViewById(R.id.outletNameTextView);
        loginTimeTextView = (TextView) findViewById(R.id.loginTimeTextView);
        profileImageView = (NetworkImageView) findViewById(R.id.profileImageView);


        /***
         * For tool bar
         */
        toolbar_tableNoTextView = (TextView) findViewById(R.id.toolbar_tableNoTextView);
        toolbar_timeTextView = (TextView) findViewById(R.id.toolbar_timeTextView);
        toolbar_sessionTextView = (TextView) findViewById(R.id.toolbar_sessionTextView);

        initToolCollapsingOperation();

        /***
         * Order making items section
         * ***/
        searchItemsEdit = (EditText) findViewById(R.id.searchItems);
        clearSearchText = (Button) findViewById(R.id.clearSearchText);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        expandButton = (ImageButton) findViewById(R.id.expandButton);
        recycler_view_search_item = (RecyclerView) findViewById(R.id.recycler_view_search_item);

        /***
         * Order making section
         * ***/
//        addRemarksEditText = (EditText) findViewById(R.id.foodOrder_addRemarksEditText);
     //   lockBtn = (ImageButton) findViewById(R.id.foodOrder_lockBtn);
        saveBtn = (ImageButton) findViewById(R.id.foodOrder_saveBtn);
        cancelBtn = (ImageButton) findViewById(R.id.foodOrder_cancelBtn);
        order_recycler_view_checkbox = (RecyclerView) findViewById(R.id.order_recycler_view_checkbox);
        checkbox1_all = (CheckBox)findViewById(R.id.checkbox1_all);
        checkbox1  = (CheckBox)findViewById(R.id.checkbox1);
//
//        initOrderMakingItemSelection();
//        initOrderMaking();
//
//        searchEditOnChangeListener();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        sp  =  getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");
        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        initOperation_Empty();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemTransfer.this, NavigationDrawerActivity.class);
                startActivity(i);
            }
        });
        initOperation_item();
        checkbox();
        //checkboxx();

    }


    private void checkbox(){
        checkbox1_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkbox1_all.isChecked()) {
                    //for (update_set_get obj : itemOrderList1) {


                        ItemCheckStatus = "YES";
                        for (int i=0; i<itemOrderList1.size(); i++){

                        update_set_get obj = new update_set_get();
                        itemOrderList1.get(i).setSelected(true);
                        obj.setKotnumber(itemOrderList1.get(i).getKotnumber());
                        obj.setItem_kot(itemOrderList1.get(i).getItem_kot());
                        obj.setItemdescrr(itemOrderList1.get(i).getItemdescrr());
                        obj.setQty_kot(itemOrderList1.get(i).getQty_kot());
                        obj.setRate_kot(itemOrderList1.get(i).getRate_kot());

                        String UtterItem = itemOrderList1.get(i).getItem_kot();
                        if (SelectedItem==""){
                            SelectedItem = SelectedItem + UtterItem + ",";
                            Selectedarrlist1.add(obj);
                        }
                        else{
                            if (Selectedarrlist1.contains(UtterItem)) {
                                Selectedarrlist1.remove(UtterItem);
                                SelectedItem= TextUtils.join(",", Selectedarrlist1);
                                SelectedItem = SelectedItem+",";
                            }
                            else{
                                SelectedItem = SelectedItem + UtterItem + ",";
                                Selectedarrlist1.add(obj);
                            }
                        }
                        }

                   // }
                } else {

//                    for (update_set_get obj : itemOrderList1) {
//                        obj.setSelected(false);
                    for (int i=0; i<itemOrderList1.size(); i++){
                        itemOrderList1.get(i).setSelected(false);
                    }
                }

                itemTransferr.notifyDataSetChanged();

            }
        });
    }
    private void selected_kot(){

        if (Selectedarrlist1.size()>0){
            for (int i=0; i<Selectedarrlist1.size(); i++){

                Map<String, String> params = new HashMap<String, String>();
                params.put("FLAG", "HDR_DTL_ITEM_TRANSFR");
                params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
                params.put("TABLENUM", arrlist.get(tableview).getTableId().trim());
                params.put("ITEMCD", Selectedarrlist1.get(i).getItemdescrr().trim());
                params.put("FROMTABLE", tableNo);
                params.put("FROMKOTNUMBER", Selectedarrlist1.get(i).getKotnumber().trim());
                params.put("ORDERDATE", formattedDate.trim());
                params.put("DTLCOUNT",String.valueOf(Selectedarrlist1.size()));
                params.put("SERIAL", String.valueOf(i));
                params.put("QTY", Selectedarrlist1.get(i).getQty_kot().trim());
                params.put("RATE", Selectedarrlist1.get(i).getRate_kot().trim());
                Double Rate= Double.parseDouble(Selectedarrlist1.get(i).getRate_kot().trim());
                Double Qty= Double.parseDouble(Selectedarrlist1.get(i).getQty_kot().trim());
                Double Value= Rate*Qty;
                params.put("VALUE", String.valueOf(Value));
                params.put("LOCATIONPREFIX", outletCode.trim());
                params.put("USERNAME", sp.getString("NAME", "Name"));
                params.put("FINYR", sp.getString("FIN_YEAR", "FIN_YEAR"));
                params.put("PNO", "16");
                //   CustomDialogClass.showProgressDialog(context,true);
                JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
                jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
                    @Override
                    public void onServiceError(String error) {
                        CustomDialogClass.showProgressDialog(context,false);
                        generalOperationTools.showSnackMessage(error.toUpperCase()
                                ,"OK",coordinatorLayout);
                    }

                    @Override
                    public void onServiceResponse(String response) {
                        int status=0;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response.trim());
                            Log.d("ok_update_kot", String.valueOf(jsonArray));
                            JSONObject jObj1 = jsonArray.getJSONObject(0);
                            JSONArray STATUS = jObj1.getJSONArray("STATUS");
                            Log.d("STATUS_kot", String.valueOf(STATUS));
//                    update_data.clear();

                            for (int j = 0 ;j<STATUS.length();j++){
                                JSONObject explrObject = STATUS.getJSONObject(j);

                                if (j == 0) {
                                    status = Integer.parseInt(explrObject.getString("STATUS"));
                                    Log.d("INSERT_ok_kot", String.valueOf(status));
                                    parseValid(response,status);
//                            prepareTables();

                                    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhh");
//                            update();

                                }
                            }


                        } catch (JSONException e) {
                            //   CustomDialogClass.showProgressDialog(context,false);
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void PreviousTableUpdate_AfterItemTransfer(){

        if (Selectedarrlist1.size()>0){
            for (int i=0; i<Selectedarrlist1.size(); i++){

                Map<String, String> params = new HashMap<String, String>();
                params.put("FLAG", "UPDATE_PREVIOUS_TABLE_ITEMFRANSFER");
                params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
                params.put("FROMTABLE", tableNo);
                params.put("FROMKOTNUMBER", Selectedarrlist1.get(i).getKotnumber().trim());
                params.put("ITEMCD", Selectedarrlist1.get(i).getItem_kot().trim());
                params.put("PNO", "6");
                JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
                jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
                    @Override
                    public void onServiceError(String error) {
                        CustomDialogClass.showProgressDialog(context,false);
                        generalOperationTools.showSnackMessage(error.toUpperCase()
                                ,"OK",coordinatorLayout);
                    }

                    @Override
                    public void onServiceResponse(String response) {
                        int status=0;
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response.trim());
                            Log.d("ok_update_kot", String.valueOf(jsonArray));
                            JSONObject jObj1 = jsonArray.getJSONObject(0);
                            JSONArray STATUS = jObj1.getJSONArray("STATUS");
                            Log.d("STATUS_kot", String.valueOf(STATUS));
//                    update_data.clear();

                            for (int j = 0 ;j<STATUS.length();j++){
                                JSONObject explrObject = STATUS.getJSONObject(j);

                                if (j == 0) {
                                    status = Integer.parseInt(explrObject.getString("STATUS"));
                                    Log.d("INSERT_ok_kot", String.valueOf(status));
                                    parseValid(response,status);
//                            prepareTables();

                                    System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhh");
//                            update();

                                }
                            }


                        } catch (JSONException e) {
                            //   CustomDialogClass.showProgressDialog(context,false);
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    private void parseValid(String response, int status) {

        JSONArray jsonArray = null;/*
        int[] covers = new int[]{
                R.drawable.circle_red,
                R.drawable.circle_green};*/
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
             //   generalOperationTools.showSnackMessage("NO TABLE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
    }
    public void onSave(View v){
        checkbox();
        if (ItemCheckStatus=="YES"){
            selected_kot();
            PreviousTableUpdate_AfterItemTransfer();
        }
        else {

        }
        Intent i = new Intent(ItemTransfer.this, NavigationDrawerActivity.class);
        startActivity(i);
    }

    public void onCheckboxClicked(View v) {
        switch (v.getId()) {
            case R.id.checkbox1:
                if (checkbox1.isChecked()) {
//                    TableCheckStatus = "NO";
//                    KotCheckStatus = "YES";
                    checkbox1.setChecked(true);
                  //  Toast.makeText(getApplicationContext(), arrlist1.get(itemview).getItemdescrr(), Toast.LENGTH_LONG).show();
                }
                //selected_kot();
                break;
        }
    }



    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        tableList.add(newPosition,tableList.remove(oldPosition));// change position
        tableHolderAdapter_transfer_item.notifyItemMoved(oldPosition, newPosition);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        toolbar_linearParent.setVisibility(View.GONE);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(false);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_linearParent.setVisibility(View.VISIBLE);
                    //collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    toolbar_linearParent.setVisibility(View.GONE);
                    //collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void initToolCollapsingOperation() {

        Bundle bundle = getIntent().getExtras();
        tableNo = bundle.getString("TABLE_NO");
        /***
         * For collapsing tool bar
         */
        String imageUrl = sp.getString("USERPHOTO", "http://gshandicraftfashion.com/wp-content/themes/sw_chamy/assets/img/no-thumbnail.png");
        System.out.println("Image Tag- " + imageUrl);

        ImageLoader imageLoader = RestroApplication.getInstance().getImageLoader();

        profileImageView.setImageUrl(imageUrl, imageLoader);

        userNameTextView.setText(sp.getString("NAME", "Name"));
        loginTimeTextView.setText("Time- " + sp.getString("CUR_TIME", "Time"));
        String[] tokenSession = sp.getString("SESSION", "Session Name # 0").split("#");
        sessionNameTextView.setText("Session- " + tokenSession[0]);

        String[] tokenOutlet = sp.getString("OUTLET", "Outlet Name # 0").split("#");
        outletNameTextView.setText("Outlet- " + tokenOutlet[0]);

        tableNoTextView.setText("Table-" + tableNo);

        /***
         * For tool bar
         */

        userNameTextView.setText(sp.getString("NAME", "Name"));
        toolbar_timeTextView.setText("Time- " + sp.getString("CUR_TIME", "Time"));
        toolbar_sessionTextView.setText("Session- " + tokenSession[0]);
        toolbar_tableNoTextView.setText("Table-" + tableNo);
    }

    private void initOperation_item(){
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        itemOrderList1 = new ArrayList<>();
        itemTransferr = new Item_Transfer(getApplicationContext(),itemOrderList1);
        order_recycler_view_checkbox = (RecyclerView)findViewById(R.id.order_recycler_view_checkbox);
        order_recycler_view_checkbox.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(ItemTransfer.this,2);
        order_recycler_view_checkbox.setLayoutManager(mLayoutManager);
        order_recycler_view_checkbox.addItemDecoration(new TableTransfer.GridSpacingItemDecoration(2, dpToPx(2), true));
        order_recycler_view_checkbox.setItemAnimator(new DefaultItemAnimator());
        order_recycler_view_checkbox.setAdapter(itemTransferr);
        order_recycler_view_checkbox.addOnItemTouchListener(new TableTransfer.RecyclerTouchListener(getApplicationContext(), order_recycler_view_checkbox, new TableTransfer.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                itemview = position;
                final update_set_get objItem = new update_set_get();
                objItem.setTablenum(arrlist1.get(itemview).getTableId());
                objItem.setKotnumber(arrlist1.get(itemview).getKotnumber());
                objItem.setItem_kot(arrlist1.get(itemview).getItem_kot());
                objItem.setItemdescrr(arrlist1.get(itemview).getItemdescrr());
                objItem.setQty_kot(arrlist1.get(itemview).getQty_kot());
                objItem.setRate_kot(arrlist1.get(itemview).getRate_kot());

                String UtterItem = arrlist1.get(itemview).getItem_kot();
                if (SelectedItem==""){
                    SelectedItem = SelectedItem + UtterItem + ",";
                    Selectedarrlist1.add(objItem);
                }
                else{
                    if (Selectedarrlist1.contains(UtterItem)) {
                        Selectedarrlist1.remove(UtterItem);
                        SelectedItem= TextUtils.join(",", Selectedarrlist1);
                        SelectedItem = SelectedItem+",";
                    }
                    else{
                        SelectedItem = SelectedItem + UtterItem + ",";
                        Selectedarrlist1.add(objItem);
                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    private void update() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "UPDATE_KOT");
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("TABLENUM", tableNo);
        params.put("PNO", "6");
        params.put("FROMDATE", formattedDate);
        params.put("TODATE", formattedDate);


        CustomDialogClass.showProgressDialog(context,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage(error.toUpperCase()
                        ,"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("update----", String.valueOf(jsonArray));
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray STATUS = jObj1.getJSONArray("STATUS");
                    Log.d("STATUS------------", String.valueOf(STATUS));
//                    update_data.clear();

                    for (int j = 0 ;j<STATUS.length();j++){
                        JSONObject explrObject = STATUS.getJSONObject(j);

                        if (j == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("INSERT_APP_STATUS------", String.valueOf(status));
                            System.out.println(response);
                            update_json(response, status);
                        }
                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void update_json(String response, int status) {
        JSONArray jsonArray = null;
        //  arrlist=new ArrayList<update_set_get>();
        try {
            jsonArray = new JSONArray(response.trim());

            if (status == 1) {

                JSONObject jObj = jsonArray.getJSONObject(0);
                JSONArray update = jObj.getJSONArray("KOT_UPDATE");
                Log.d("update------------", String.valueOf(update));

                for (int i = 0; i<update.length();i++){
                    JSONObject kot_update = update.getJSONObject(i);
                    final update_set_get objcont = new update_set_get();
                    ItemModel itemList = new ItemModel();
                    ItemEditOrderModel iom = new ItemEditOrderModel();
                    tableNo = kot_update.getString("TABLENUM");
                    kotNumber = kot_update.getString("KOTNUMBER");
                    ITEMCD = kot_update.getString("ITEMCD");
                    ITEMDESCR = kot_update.getString("ITEMDESCR");
                     QTY = kot_update.getString("QTY");
                     RATE = kot_update.getString("RATE");
                     VALUE = kot_update.getString("VALUE");
                    String  COLNAME = kot_update.getString("COLNAME");
                    objcont.setTablenum(tableNo);
                    objcont.setTableId(tableNo);
                    objcont.setKotnumber(kotNumber);
                    objcont.setItem_kot(ITEMCD);
                    objcont.setItemdescrr(ITEMDESCR);
                    objcont.setQty_kot(QTY);
                    objcont.setRate_kot(RATE);
                    objcont.setValue_kot(VALUE);
                    objcont.setUpdate(COLNAME);
                    itemOrderList1.add(objcont);
                    itemTransferr.notifyDataSetChanged();
                    //     tableList.add(objcont);
                    arrlist1.add(objcont);
                    Log.d("update----------",tableNo+ " "+kotNumber+ " "+ITEMCD+" "+ITEMDESCR+""+QTY+" "+RATE+""+" "+VALUE+" "+COLNAME+" ");
//
                    Log.d("ITEMCD-----",ITEMCD);



                }


                CustomDialogClass.showProgressDialog(context, false);
              //  generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
            //    generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }

    }
    private void   initOperation_Empty(){
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");


        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        tableList = new ArrayList<>();
        tableHolderAdapter_transfer_item = new TableHolderAdapter_Item(getApplicationContext(),tableList);
        recycler_view_search_item = (RecyclerView) findViewById(R.id.recycler_view_search_item);
        RecyclerView.LayoutManager mlayoutManager = new GridLayoutManager(ItemTransfer.this,4);
        recycler_view_search_item.setLayoutManager(mlayoutManager);
        recycler_view_search_item.addItemDecoration(new TableTransfer.GridSpacingItemDecoration(4, dpToPx(4), true));
        recycler_view_search_item.setItemAnimator(new DefaultItemAnimator());
        recycler_view_search_item.setAdapter(tableHolderAdapter_transfer_item);
        prepareTables();
        System.out.println("INSIDE ON CREATE VIEW");

        recycler_view_search_item.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_search_item, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                tableview = position;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {

            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void prepareTables() {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","TABLE_STATUS");
        params.put("ORDERDATE",currentDate.trim());
        params.put("LOCATIONPREFIX",outletCode.trim());
        params.put("SPNAME", "USP_APP_LOGIN_SV");
        params.put("PNO", "5");
        System.out.println("INSIDE PREPARE TABLE");


        CustomDialogClass.showProgressDialog(context,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage(error.toUpperCase()
                        ,"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("jsonArray----EMPTY", String.valueOf(jsonArray));
                    JSONObject OBJ_TABLE1 = jsonArray.getJSONObject(1);
                    JSONArray  JSONSTATUS = OBJ_TABLE1.getJSONArray("JSONSTATUS");
                    Log.d("TABLE_STATUS---------", String.valueOf(JSONSTATUS));
                    for (int i = 0; i<JSONSTATUS.length();i++){
                        JSONObject JSONSTATUS_OBJ = JSONSTATUS.getJSONObject(i);
                        if (i==0){
                            //   String STATUS = JSONSTATUS_OBJ.getString("STATUS");
                            status = Integer.parseInt(JSONSTATUS_OBJ.getString("STATUS"));
                            String COLNAME = JSONSTATUS_OBJ.getString("COLNAME");
                            Log.d("status-EMPTY", String.valueOf(status));
                            Log.d("COLNAME-------------",COLNAME);
                            parseValidTableJson(response,status);
                            update();
                        }
                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context,false);
                    e.printStackTrace();
                }
            }
        });
    }


    private void parseValidTableJson(String response, int status) {

        JSONArray jsonArray = null;/*
        int[] covers = new int[]{
                R.drawable.circle_red,
                R.drawable.circle_green};*/
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){


                JSONObject OBJ_TABLE = jsonArray.getJSONObject(0);
                JSONArray TABLE_STATUS = OBJ_TABLE.getJSONArray("TABLE_STATUS");
                Log.d("TABLE_STATUS", String.valueOf(TABLE_STATUS));
                for (int i = 0; i < TABLE_STATUS.length(); i++) {
                    JSONObject tableStatusJsonObj = TABLE_STATUS.getJSONObject(i);
                    Log.d("TABLE_OBJ", String.valueOf(tableStatusJsonObj));
                    TableModel tm = new TableModel(tableStatusJsonObj.getString("STATUS"), tableStatusJsonObj.getString("TYPE"), tableStatusJsonObj.getString("TABLE_ID"));
                    tableList.add(tm);
                    arrlist.add(tm);




                    tableHolderAdapter_transfer_item.notifyDataSetChanged();
                }
                CustomDialogClass.showProgressDialog(context,false);
             //   generalOperationTools.showSnackMessage("TABLE(S) FOUND","OK",coordinatorLayout);
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
            //    generalOperationTools.showSnackMessage("NO TABLE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
    }
}
