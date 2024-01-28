package com.ebits.restro.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.ItemsEditOrderHolderAdapter;
import com.ebits.restro.activities.adapter.ItemsHolderAdapter;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemEditOrderModel;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.utils.CustomDialogClass;
import com.ebits.restro.activities.utils.JsonParsingUtils;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.interfaces.CleanAdapterInterface;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
import com.ebits.restro.activities.utils.tools.DividerItemDecoration;
import com.ebits.restro.activities.utils.tools.GeneralOperationTools;
import com.ebits.restro.activities.utils.tools.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditOrderTakingActivity_Edit extends AppCompatActivity {

    private TextView userNameTextView, tableNoTextView,
            sessionNameTextView, outletNameTextView, loginTimeTextView;
    private TextView toolbar_tableNoTextView, toolbar_timeTextView, toolbar_sessionTextView;
    private NetworkImageView profileImageView;
    private LinearLayout toolbar_linearParent;
    private CoordinatorLayout coordinatorLayout;

    private SharedPreferences sp;
    private GeneralOperationTools generalOperationTools;
    private ItemsHolderAdapter itemHolderAdapter;
    private List<ItemModel> itemList;
    private List<ItemEditOrderModel> itemOrderList;
    private ItemsEditOrderHolderAdapter itemOrderHolderAdapter;
    private Context context = this;
    private String tableNo="",kotNumber="",formattedDate="";
    private int size=0;
    private boolean flagFirstTime=true,onSaveFlag=true;


    /***
     * Order making items section
     ***/
    private EditText searchItemsEdit;
    private Button clearSearchText;
    private ImageButton searchButton, expandButton;
    private RecyclerView recycler_view_search;

    /***
     * Order making section
     ***/
    private EditText addRemarksEditText;
    private ImageButton lockBtn, saveBtn, cancelBtn;
    private RecyclerView order_recycler_view;

    private String jsonOrder    =   "";
    private String  outletData, outletCode, currentDate;
//    String ITEMCD;
//    String ITEMDESCR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_making_parent);
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
        recycler_view_search = (RecyclerView) findViewById(R.id.recycler_view_search);

        /***
         * Order making section
         * ***/
        addRemarksEditText = (EditText) findViewById(R.id.foodOrder_addRemarksEditText);
     //   lockBtn = (ImageButton) findViewById(R.id.foodOrder_lockBtn);
        saveBtn = (ImageButton) findViewById(R.id.foodOrder_saveBtn);
        cancelBtn = (ImageButton) findViewById(R.id.foodOrder_cancelBtn);
        order_recycler_view = (RecyclerView) findViewById(R.id.order_recycler_view);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());

        initOrderMakingItemSelection();
        initOrderMaking();

        searchEditOnChangeListener();

    //    Intent i = getIntent();

        // Get the name

//        String  TABLENUM = i.getStringExtra("TABLENUM");
//        String  kotNumber = i.getStringExtra("kotNumber");
//        String  ITEMCD = i.getStringExtra("ITEMCD");
//        String  ITEMDESCR = i.getStringExtra("ITEMDESCR");
//        String  QTY = i.getStringExtra("QTY");
//        String  RATE = i.getStringExtra("RATE");
//        String  VALUE = i.getStringExtra("VALUE");
//        String  COLNAME = i.getStringExtra("COLNAME");

//        tableNo = i.getStringExtra("TABLENUM");
//        kotNumber = i.getStringExtra("kotNumber");
//        String  ITEMCD = i.getStringExtra("ITEMCD");
//        String  ITEMDESCR = i.getStringExtra("ITEMDESCR");
//        String  QTY = i.getStringExtra("QTY");
//        String  RATE = i.getStringExtra("RATE");
//        String  VALUE = i.getStringExtra("VALUE");
//        String  COLNAME = i.getStringExtra("COLNAME");
//        Log.d("all number",tableNo + " "+ kotNumber+ " "+ITEMCD+ " "+ITEMDESCR+ " "+QTY+ " "+RATE+ " "+VALUE+ " "+COLNAME+ " ");
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
//        status = sp.getString("STATUS","");


        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        Log.d("outletCode---------",outletCode);
        Intent i = getIntent();
        tableNo =  i.getStringExtra("TABLE_NO");
   //     kotNumber = i.getStringExtra("Kot");
        Log.d("TABLE_NO",tableNo);
   //     Log.d("Kot",kotNumber);
    }

    private void searchEditOnChangeListener() {

        searchItemsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start,
                                      int before, int count) {

                System.out.println("Start"+start);
                System.out.println("before"+before);
                System.out.println("count"+count);
                if(count>0)
                    clearSearchText.setVisibility(View.VISIBLE);
                else
                    clearSearchText.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */


    ////Not understand

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


    /////Not understand

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


    /***
     * Left panel order section
     */
    private void initOrderMakingItemSelection() {

        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();

        itemList = new ArrayList<>();
        itemHolderAdapter = new ItemsHolderAdapter(EditOrderTakingActivity_Edit.this, itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EditOrderTakingActivity_Edit.this, 3);
        recycler_view_search.setLayoutManager(mLayoutManager);
        recycler_view_search.addItemDecoration(new GridSpacingItemDecoration(3, GridSpacingItemDecoration.dpToPx(3, EditOrderTakingActivity_Edit.this), true));
        recycler_view_search.setItemAnimator(new DefaultItemAnimator());
        recycler_view_search.setAdapter(itemHolderAdapter);

        prepareItems("TOP_20_1", "SP_POS_KOTBILLING_SV", "4");

        recycler_view_search.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext()
                , recycler_view_search, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemModel itemModel = itemList.get(position);

                if (!hasDuplicates(itemModel.getItemNameId(),itemModel.getItemName().trim())) {
                    Toast.makeText(getApplicationContext(), itemModel.getItemName()
                            + " is selected!", Toast.LENGTH_SHORT).show();
                    ItemEditOrderModel iom = new ItemEditOrderModel();

                    iom.setItemName(itemModel.getItemName().trim());
                    iom.setItemNameId(itemModel.getItemNameId());
                    iom.setItemRate(itemModel.getItemRate());
                    iom.setItemImage(itemModel.getItemImage());
                    iom.setItemQty("1");
                    iom.setItemValue(itemModel.getItemRate());
                    iom.setItemDescription("");
                    iom.setItemChargaqbleOrNot("1");
                    itemOrderList.add(iom);
                    itemOrderHolderAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), itemModel.getItemName() + " already ordered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private boolean hasDuplicates(String id, String name) {

        int size = this.itemOrderList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                if (itemOrderList.get(i).getItemNameId().equals(id)) {
                    String itemNameWithDescp=   itemOrderList.get(i).getItemName().trim()
                            +" "+itemOrderList.get(i).getItemDescription().trim();
                    System.out.println(i+" Final Text -"+itemNameWithDescp);
                    if(itemNameWithDescp.trim().equalsIgnoreCase(name.trim())) {
                        int qty = Integer.parseInt(itemOrderList.get(i).getItemQty().trim());
                        System.out.println("Qty pehele- " + qty);
                        qty++;
                        System.out.println("Qty aab- " + qty);
                        double priceValue = Double.parseDouble(itemOrderList.get(i).getItemRate().trim());
                        priceValue = priceValue * qty;
                        itemOrderList.get(i).setItemQty(String.valueOf(qty));
                        itemOrderList.get(i).setItemValue(String.valueOf(priceValue));
                        itemOrderHolderAdapter.notifyItemChanged(i);
                        return true;
                    }
                }
            }
        }
        // No duplicates found.
        return false;
    }

    /***
     * Left panel order section
     */
    private void initOrderMaking() {
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();

        itemOrderList = new ArrayList<>();
        itemOrderHolderAdapter = new ItemsEditOrderHolderAdapter(EditOrderTakingActivity_Edit.this, itemOrderList);

        order_recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        order_recycler_view.setLayoutManager(mLayoutManager);
        order_recycler_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        order_recycler_view.setItemAnimator(new DefaultItemAnimator());
        order_recycler_view.setAdapter(itemOrderHolderAdapter);
    }

    /***
     * Prepare table items
     */
    private void prepareItems(String flag, String spName, String pNo) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", flag.trim());
        params.put("SPNAME", spName.trim());
        params.put("PNO", pNo.trim());
        params.put("DESCR", "");
//        params.put("FLAG","TOP_20_1");
//        params.put("SPNAME", "SP_POS_KOTBILLING");
//        params.put("PNO", "4");
//        params.put("DESCR", "");

        CustomDialogClass.showProgressDialog(context, true);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    //    JSONObject jObj = jsonArray.getJSONObject(0);
                    //    JSONArray TOP_20_ITEM = jObj.getJSONArray("TOP 20 ITEM");
                    //   Log.d("TOP_20_ITEM------------", String.valueOf(TOP_20_ITEM));
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray STATUS = jObj1.getJSONArray("STATUS");
                    Log.d("STATUS------------", String.valueOf(STATUS));
                    for (int i = 0; i < STATUS.length(); i++) {
                        JSONObject explrObject = STATUS.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            //   String STATUS1 = status_obj.getString("STATUS");
                            //   String COLNAME = explrObject.getString("COLNAME");
                            Log.d("STATUS_EditOrder", String.valueOf(status));
                            //    Log.d("COLNAME--",COLNAME);

                            parseValidTableJson(response, status);
                        }
                    }
//                    for (int i = 0; i < TOP_20_ITEM.length(); i++) {
//                        JSONObject TOP_20_ITEM_OBJ = TOP_20_ITEM.getJSONObject(i);
//                        String MONTHLYCONSUMP = TOP_20_ITEM_OBJ.getString("MONTHLYCONSUMP");
//                        String ITEMCD = TOP_20_ITEM_OBJ.getString("ITEMCD");
//                        String ITEMNAME = TOP_20_ITEM_OBJ.getString("ITEMNAME");
//                        String SELRATE = TOP_20_ITEM_OBJ.getString("SELRATE");
//                        String GROUP = TOP_20_ITEM_OBJ.getString("GROUP");
//                        String SUBGROUP = TOP_20_ITEM_OBJ.getString("SUBGROUP");
//                        String TYPE = TOP_20_ITEM_OBJ.getString("TYPE");
//                        String ITEMIMAGE = TOP_20_ITEM_OBJ.getString("ITEMIMAGE");
//                        String SUBTYPE  = TOP_20_ITEM_OBJ.getString("SUBTYPE");
//                        String COLNAME = TOP_20_ITEM_OBJ.getString("COLNAME");
//                        Log.d("MONTHLYCONSUMP------",MONTHLYCONSUMP);
//                        Log.d("ITEMCD---",ITEMCD);
//                        Log.d("ITEMNAME--",ITEMNAME);
//
//                        Log.d("SELRATE------",SELRATE);
//                        Log.d("SELRATE---",SELRATE);
//                        Log.d("GROUP--",GROUP);
//
//                        Log.d("SUBGROUP------",SUBGROUP);
//                        Log.d("TYPE---",TYPE);
//                        Log.d("ITEMIMAGE--",ITEMIMAGE);
//
//                        Log.d("SUBTYPE---",SUBTYPE);
//                        Log.d("COLNAME--",COLNAME);
//
//                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
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
            Log.d("jsonArray1---------", String.valueOf(jsonArray));
            if (status == 1) {

//                              for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject explrObject = jsonArray.getJSONObject(i);
//                    Log.d("explrObject2----------", String.valueOf(explrObject));
//
//                    /**For table data adapter***/
//                    if (i == 1) {
//
//                        JSONArray tableStatusJsonArray = explrObject.getJSONArray("TOP 20 ITEM");
//                        for (int j = 0; j < tableStatusJsonArray.length(); j++) {
//                            JSONObject tableStatusJsonObj = tableStatusJsonArray.getJSONObject(j);
//
//                            ItemModel im = new ItemModel();
//                            im.setItemGroup(tableStatusJsonObj.getString("GROUP").trim());
//                            if(tableStatusJsonObj.getString("SUBGROUP").trim().contains("#")) {
//                                String[] tokenSubGroup = tableStatusJsonObj.getString("SUBGROUP").trim().split("#");
//                                im.setItemSubGroupName(tokenSubGroup[0]);
//                                im.setItemSubGroupId(tokenSubGroup[1]);
//                            }
//                            else{
//                                im.setItemSubGroupName("");
//                                im.setItemSubGroupId("");
//                            }
//                            if(tableStatusJsonObj.getString("TYPE").trim().contains("#")) {
//                                String[] tokenItemType = tableStatusJsonObj.getString("TYPE").trim().split("#");
//                                im.setItemTypeName(tokenItemType[0]);
//                                im.setItemTypeId(tokenItemType[1]);
//                            }
//                            else{
//                                im.setItemTypeName("");
//                                im.setItemTypeId("");
//                            }
//                            if(tableStatusJsonObj.getString("SUBTYPE").trim().contains("#")) {
//                                String[] tokenSubItemType = tableStatusJsonObj.getString("SUBTYPE").trim().split("#");
//                                im.setItemSubTypeName(tokenSubItemType[0]);
//                                im.setItemSubTypeId(tokenSubItemType[1]);
//                            }
//                            else{
//                                im.setItemSubTypeName("");
//                                im.setItemSubTypeId("");
//                            }
//                            String[] tokenItemName = tableStatusJsonObj.getString("ITEMNAME").trim().split("#");
//                            im.setItemName(tokenItemName[0]);
//                            im.setItemNameId(tokenItemName[1]);
//                            im.setItemRate(tableStatusJsonObj.getString("SELRATE").trim());
//                            im.setItemImage(tableStatusJsonObj.getString("ITEMIMAGE").trim());
//                            itemList.add(im);
//                        }
//                        itemHolderAdapter.notifyDataSetChanged();
//                    }
//                }
                JSONObject jObj = jsonArray.getJSONObject(0);
                JSONArray TOP_20_ITEM = jObj.getJSONArray("TOP 20 ITEM");
                Log.d("TOP_20_ITEM1----------", String.valueOf(TOP_20_ITEM));
                for (int i = 0; i < TOP_20_ITEM.length(); i++) {
                    JSONObject TOP_20_ITEM_OBJ = TOP_20_ITEM.getJSONObject(i);
                    String MONTHLYCONSUMP = TOP_20_ITEM_OBJ.getString("MONTHLYCONSUMP");
                    Log.d("MONTHLYCONSUMP------",MONTHLYCONSUMP);
                   String  ITEMCD = TOP_20_ITEM_OBJ.getString("ITEMCD");
                    Log.d("ITEMCD---",ITEMCD);
                    String ITEMNAME = TOP_20_ITEM_OBJ.getString("ITEMNAME");
                    Log.d("ITEMNAME--",ITEMNAME);
                    String SELRATE = TOP_20_ITEM_OBJ.getString("SELRATE");
                    Log.d("SELRATE------",SELRATE);
                    String GROUP = TOP_20_ITEM_OBJ.getString("GROUP");
                    Log.d("GROUP--",GROUP);
                    String SUBGROUP = TOP_20_ITEM_OBJ.getString("SUBGROUP");
                    Log.d("SUBGROUP------",SUBGROUP);
                    String TYPE = TOP_20_ITEM_OBJ.getString("TYPE");
                    Log.d("TYPE---",TYPE);
                    String ITEMIMAGE = TOP_20_ITEM_OBJ.getString("ITEMIMAGE");
                    Log.d("ITEMIMAGE--",ITEMIMAGE);
                    String SUBTYPE  = TOP_20_ITEM_OBJ.getString("SUBTYPE");
                    Log.d("SUBTYPE---",SUBTYPE);
                    String COLNAME = TOP_20_ITEM_OBJ.getString("COLNAME");
                    Log.d("COLNAME--",COLNAME);
                    ItemModel im = new ItemModel();
                    im.setItemGroup(TOP_20_ITEM_OBJ.getString("GROUP"));
                    if (TOP_20_ITEM_OBJ.getString("SUBGROUP").trim().contains("#")){
                        String[] tokenSubGroup = TOP_20_ITEM_OBJ.getString("SUBGROUP").trim().split("#");
                        im.setItemSubGroupName(tokenSubGroup[0]);
                        im.setItemSubGroupId(tokenSubGroup[1]);
                    }
                    else {
                        im.setItemSubGroupName("");
                        im.setItemSubGroupId("");
                    }
                    if(TOP_20_ITEM_OBJ.getString("TYPE").trim().contains("#")) {
                        String[] tokenItemType = TOP_20_ITEM_OBJ.getString("TYPE").trim().split("#");
                        im.setItemTypeName(tokenItemType[0]);
                        im.setItemTypeId(tokenItemType[1]);
                    }
                    else{
                        im.setItemTypeName("");
                        im.setItemTypeId("");
                    }
                    if(TOP_20_ITEM_OBJ.getString("SUBTYPE").trim().contains("#")) {
                        String[] tokenSubItemType = TOP_20_ITEM_OBJ.getString("SUBTYPE").trim().split("#");
                        im.setItemSubTypeName(tokenSubItemType[0]);
                        im.setItemSubTypeId(tokenSubItemType[1]);
                    }
                    else{
                        im.setItemSubTypeName("");
                        im.setItemSubTypeId("");
                    }
                    String[] tokenItemName = TOP_20_ITEM_OBJ.getString("ITEMNAME").trim().split("#");
                    im.setItemName(tokenItemName[0]);
                    im.setItemNameId(tokenItemName[0]);
                    im.setItemRate(TOP_20_ITEM_OBJ.getString("SELRATE").trim());
                    im.setItemImage(TOP_20_ITEM_OBJ.getString("ITEMIMAGE").trim());
                    itemList.add(im);
                    itemHolderAdapter.notifyDataSetChanged();
                }

//
//                JSONObject jObj = jsonArray.getJSONObject(0);
//                JSONObject jObj1 = jsonArray.getJSONObject(1);
//                JSONArray TOP_20_ITEM = jObj.getJSONArray("TOP 20 ITEM");
//                Log.d("TOP_20_ITEM------------", String.valueOf(TOP_20_ITEM));
//                for (int i = 0; i < TOP_20_ITEM.length(); i++) {
//                    JSONObject TOP_20_ITEM_OBJ = TOP_20_ITEM.getJSONObject(i);
//                    String MONTHLYCONSUMP = TOP_20_ITEM_OBJ.getString("MONTHLYCONSUMP");
//                    String ITEMCD = TOP_20_ITEM_OBJ.getString("ITEMCD");
//                    String ITEMNAME = TOP_20_ITEM_OBJ.getString("ITEMNAME");
//
//                    ItemModel im = new ItemModel();
//                    String GROUP = TOP_20_ITEM_OBJ.getString("GROUP");
//                    im.setItemGroup(TOP_20_ITEM_OBJ.getString("GROUP").trim());
//                    if(TOP_20_ITEM_OBJ.getString("SUBGROUP").trim().contains("#")) {
//                               String[] tokenSubGroup = TOP_20_ITEM_OBJ.getString("SUBGROUP").trim().split("#");
//                               im.setItemSubGroupName(tokenSubGroup[0]);
//                                im.setItemSubGroupId(tokenSubGroup[1]);
//                           }
//                            else{
//                                im.setItemSubGroupName("");
//                                im.setItemSubGroupId("");
//                            }
//                    String TYPE = TOP_20_ITEM_OBJ.getString("TYPE");
//                    if(TOP_20_ITEM_OBJ.getString("TYPE").trim().contains("#")) {
//                               String[] tokenItemType = TOP_20_ITEM_OBJ.getString("TYPE").trim().split("#");
//                              im.setItemTypeName(tokenItemType[0]);
//                               im.setItemTypeId(tokenItemType[1]);
//                           }
//                          else{
//                               im.setItemTypeName("");
//                                im.setItemTypeId("");
//                           }
//                    String SUBTYPE  = TOP_20_ITEM_OBJ.getString("SUBTYPE");
//                    if(TOP_20_ITEM_OBJ.getString("SUBTYPE").trim().contains("#")) {
//                                String[] tokenSubItemType = TOP_20_ITEM_OBJ.getString("SUBTYPE").trim().split("#");
//                                im.setItemSubTypeName(tokenSubItemType[0]);
//                                im.setItemSubTypeId(tokenSubItemType[1]);
//                            }
//                            else{
//                                im.setItemSubTypeName("");
//                                im.setItemSubTypeId("");
//                            }
//                    String ITEMIMAGE = TOP_20_ITEM_OBJ.getString("ITEMIMAGE");
//                    String SELRATE = TOP_20_ITEM_OBJ.getString("SELRATE");
//                    String[] tokenItemName = TOP_20_ITEM_OBJ.getString("ITEMNAME").trim().split("#");
//                            im.setItemName(tokenItemName[0]);
////                            im.setItemNameId(tokenItemName[1]);
//                            im.setItemRate(TOP_20_ITEM_OBJ.getString("SELRATE").trim());
//                            im.setItemImage(TOP_20_ITEM_OBJ.getString("ITEMIMAGE").trim());
//                            itemList.add(im);
//                    itemHolderAdapter.notifyDataSetChanged();
//                    String SUBGROUP = TOP_20_ITEM_OBJ.getString("SUBGROUP");
//
//
//                    String COLNAME = TOP_20_ITEM_OBJ.getString("COLNAME");
//                    Log.d("MONTHLYCONSUMP------",MONTHLYCONSUMP);
//                    Log.d("ITEMCD---",ITEMCD);
//                    Log.d("ITEMNAME--",ITEMNAME);
//
//                    Log.d("SELRATE------",SELRATE);
//                    Log.d("SELRATE---",SELRATE);
//                    Log.d("GROUP--",GROUP);
//
//                    Log.d("SUBGROUP------",SUBGROUP);
//                    Log.d("TYPE---",TYPE);
//                    Log.d("ITEMIMAGE--",ITEMIMAGE);
//
//                    Log.d("SUBTYPE---",SUBTYPE);
//                    Log.d("COLNAME--",COLNAME);
//
//                }





                String[] tokenOutlet = sp.getString("OUTLET", "Outlet Name # 0 # 0").split("#");
                if(flagFirstTime) {
                    // preparePreOrderItems("GET_KOT_DETAILS_S", "SP_POS_KOTBILLING", "6", formattedDate.trim(), tableNo.trim(), tokenOutlet[0].trim());
                    //  preparePreOrderItems();
                    preparePreOrderItems("GET_KOT_DETAILS_S", "SP_POS_KOTBILLING_SV", "6", formattedDate, tableNo, tokenOutlet[0]);
                    //preparePreOrderItems_update("UPDATE_KOT","KOT_HDR_DTL_INSERT_APP","6",formattedDate,tableNo, tokenOutlet[0]);
                    flagFirstTime=false;
                }
                else{
                    CustomDialogClass.showProgressDialog(context, false);
                }
                generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }
    }


    private  void  preparePreOrderItems(String GET_KOT_DETAILS_S, String SP_POS_KOTBILLING, String PNO, String ORDERDATE,
                                        String TABLENUM, String LOCATIONPREFIX){
        Map<String, String> params = new HashMap<String, String>();
//            params.put("FLAG", "GET_KOT_DETAILS_S");
//            params.put("SPNAME", "SP_POS_KOTBILLING");
//            params.put("PNO", "6");
//            params.put("ORDERDATE", formattedDate);
//            params.put("TABLENUM", tableNo);
//            params.put("LOCATIONPREFIX", "PT");

        params.put("FLAG", GET_KOT_DETAILS_S);
        params.put("SPNAME", SP_POS_KOTBILLING);
        params.put("PNO", PNO);
        params.put("ORDERDATE", ORDERDATE);
        params.put("TABLENUM", TABLENUM);
          params.put("LOCATIONPREFIX", outletCode);
      //  params.put("LOCATIONPREFIX", "PT");
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("GET_KOT_DETAILS_S---", String.valueOf(jsonArray));
                    JSONObject jsonObject2 = jsonArray.getJSONObject(2);
                    JSONArray STATUS = jsonObject2.getJSONArray("STATUS");
                    for (int i = 0; i < STATUS.length(); i++) {
                        JSONObject explrObject = STATUS.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            //  String STATUS1 = explrObject.getString("STATUS");
                            Log.d("preparePreOrderItems", String.valueOf(status));
                            String COLNAME = explrObject.getString("COLNAME");
                            Log.d("COLNAME----",COLNAME);
                            parseEditTableValidTableJson(response, status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }



    private void parseEditTableValidTableJson(String response, int status) {

        JSONArray jsonArray = null;
        /*
        int[] covers = new int[]{
                R.drawable.circle_red,
                R.drawable.circle_green};*/
        try {
            jsonArray = new JSONArray(response.trim());

            if (status == 1) {

//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject explrObject = jsonArray.getJSONObject(i);
//
//                    /**For table data adapter***/
//
//                    if(i==1){
//                        JSONArray hdrJsonArray = explrObject.getJSONArray("HDR");
//                        for (int j = 0; j < hdrJsonArray.length(); j++) {
//                            JSONObject tableStatusJsonObj = hdrJsonArray.getJSONObject(j);
//                            if(j==0){
//                                String kotNumber    =   tableStatusJsonObj.getString("KOTNUMBER");
//                            }
//                        }
//                    }
//                    if (i == 2) {
//                        JSONArray dtlJsonArray = explrObject.getJSONArray("DTL");
//                        for (int j = 0; j < dtlJsonArray.length(); j++) {
//                            JSONObject dtlJsonObj = dtlJsonArray.getJSONObject(j);
//
//                            int qty =   Double.valueOf(dtlJsonObj.getString("QTY").trim()).intValue();
//                            int valueChargeOrNot    =   Double.valueOf(dtlJsonObj.getString("VALUE").trim()).intValue();
//
//                            ItemEditOrderModel iom = new ItemEditOrderModel();
//
//                            iom.setItemName(dtlJsonObj.getString("DESCR").trim());
//                            iom.setItemNameId(dtlJsonObj.getString("ITEMCD").trim());
//                            iom.setItemRate(dtlJsonObj.getString("RATE").trim());
//                            if(dtlJsonObj.getString("ITEMIMAGE").trim().equals(""))
//                                iom.setItemImage("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");
//                            else
//                                iom.setItemImage(dtlJsonObj.getString("ITEMIMAGE").trim());
//                            iom.setItemQty(String.valueOf(qty));
//                            iom.setItemValue(dtlJsonObj.getString("VALUE").trim());
//                            iom.setItemDescription(dtlJsonObj.getString("ITEMDESCR").trim());
//                            if(valueChargeOrNot==0)
//                                iom.setItemChargaqbleOrNot("0");
//                            else
//                                iom.setItemChargaqbleOrNot("1");
//
//                            if(j==0)
//                                addRemarksEditText.setText(dtlJsonObj.getString("REMARKS").trim());
//                            itemOrderList.add(iom);
//                        }
//
//                        itemOrderHolderAdapter.notifyDataSetChanged();
//                    }
//                }

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                JSONArray HDR = jsonObject.getJSONArray("HDR");
                Log.d("HDR-----", String.valueOf(HDR));
                for (int i = 0 ;i<HDR.length();i++){
                    JSONObject HDR_OBJ = HDR.getJSONObject(i);
                    //    String KOTNUMBER = HDR_OBJ.getString("KOTNUMBER");
                    kotNumber = HDR_OBJ.getString("KOTNUMBER");
                    //   Log.d("KOTNUMBER-----",KOTNUMBER);
                    Log.d("KOTNUMBER-----",kotNumber);
                    String CODE = HDR_OBJ.getString("CODE");
                    Log.d("CODE------",CODE);
                    String COLNAME = HDR_OBJ.getString("COLNAME");
                    Log.d("COLNAME------",COLNAME);
                }
                JSONArray DTL = jsonObject1.getJSONArray("DTL");
                for (int j =0;j<DTL.length();j++){
                    JSONObject DTL_OBJ = DTL.getJSONObject(j);
//                    String VALUE = DTL_OBJ.getString("VALUE");
//                    Log.d("VALUE----",VALUE);


//                    String QTY = DTL_OBJ.getString("QTY");
//                    Log.d("QTY----",QTY);
                    int qty =   Double.valueOf(DTL_OBJ.getString("QTY").trim()).intValue();
                    int valueChargeOrNot    =   Double.valueOf(DTL_OBJ.getString("VALUE").trim()).intValue();
                    Log.d("VALUE----", String.valueOf(valueChargeOrNot));
                    ItemEditOrderModel iom = new ItemEditOrderModel();
                    iom.setItemName(DTL_OBJ.getString("DESCR").trim());
                   String  ITEMCD = DTL_OBJ.getString("ITEMCD");
                    Log.d("ITEMCD----",ITEMCD);
                    String RATE = DTL_OBJ.getString("RATE");
                    Log.d("RATE----",RATE);
                    iom.setItemNameId(DTL_OBJ.getString("ITEMCD").trim());
                    iom.setItemRate(DTL_OBJ.getString("RATE").trim());
                    String ITEMIMAGE = DTL_OBJ.getString("ITEMIMAGE");
                    Log.d("ITEMIMAGE-----",ITEMIMAGE);
                    if(DTL_OBJ.getString("ITEMIMAGE").trim().equals(""))
                        iom.setItemImage("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg");
                    else

                        iom.setItemImage(DTL_OBJ.getString("ITEMIMAGE").trim());
                    iom.setItemQty(String.valueOf(qty));
                    iom.setItemValue(DTL_OBJ.getString("VALUE").trim());
                    iom.setItemDescription(DTL_OBJ.getString("ITEMDESCR").trim());
                    if(valueChargeOrNot==0)
                        iom.setItemChargaqbleOrNot("0");
                    else
                        iom.setItemChargaqbleOrNot("1");

                    if(j==0)

                        addRemarksEditText.setText(DTL_OBJ.getString("REMARKS").trim());
                    String REMARKS = DTL_OBJ.getString("REMARKS");
                    Log.d("REMARKS---",REMARKS);
                    itemOrderList.add(iom);
                    itemOrderHolderAdapter.notifyDataSetChanged();
                    String ITEMDESCR = DTL_OBJ.getString("ITEMDESCR");
                    Log.d("ITEMDESCR-----",ITEMDESCR);
                    String DESCR = DTL_OBJ.getString("DESCR");
                    Log.d("DESCR---",DESCR);

                    String COLNAME = DTL_OBJ.getString("COLNAME");
                    Log.d("COLNAME----",COLNAME);


                }


                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
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

    /*******************
     * All button section placed here
     * *************
     *******/

    public void onExpandMenus(View v) {
        Intent intent = new Intent(this, GroupSubGroupTypeSubTypeActivity.class);
        startActivityForResult(intent, 1);
    }

    public void onSearchMenus(View v){
        if(!searchItemsEdit.getText().toString().trim().equals("")){
            searchItems(searchItemsEdit.getText().toString().trim());
        }
        else
            generalOperationTools.showSnackMessage("PLEASE ENTER ITEM NAME", "OK", coordinatorLayout);
    }

    public void onClearSearchText(View v){
        if(!searchItemsEdit.getText().toString().trim().equals("")){
            searchItemsEdit.setText("");
        }
        searchItems("");
    }

    public void onCancel(View v){
        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Edit.this, true);
        itemOrderHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
            @Override
            public void cleanStatus(boolean cleanStatus) {
                CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Edit.this, false);
            }
        });
    }

    public void onLock(View v){
        final Dialog dialogLock = new Dialog(EditOrderTakingActivity_Edit.this, R.style.TransparentProgressDialog);
        dialogLock.setContentView(R.layout.lock_dialog);
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLock.setCancelable(false);
        final EditText userName 	= (EditText) dialogLock.findViewById(R.id.dialog_userNameEditText);
        final EditText password 	= (EditText) dialogLock.findViewById(R.id.dialog_passwordEditText);
        Button unlockBtn      = (Button) dialogLock.findViewById(R.id.dialog_unlockBtn);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(userName.getText().toString().trim().equalsIgnoreCase(sp.getString("USERNAME","").trim())){
                    if(password.getText().toString().trim().equalsIgnoreCase(sp.getString("PASSWORD","").trim())){
                        dialogLock.dismiss();
                    }
                    else{
                        generalOperationTools.showSnackMessage("PASSWORD DOES NOT MATCHED", "OK", coordinatorLayout);
                    }
                }
                else{
                    generalOperationTools.showSnackMessage("USER NAME DOES NOT MATCHED", "OK", coordinatorLayout);
                }

            }
        });

        dialogLock.show();
    }
    private void KotupdateItem(String kotNumber){

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "UPDATE_KOT_ITEMS");
        params.put("KOTNUMBER", this.kotNumber);
        params.put("ORDERDATE", formattedDate.trim());
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("PNO", "6");
        params.put("TABLENUM", tableNo.trim());
        //params.put("CREATEDBY", createdBy.trim());
        //params.put("ORDERDATE", orderDate.trim());
        //params.put("FINYR", finYr.trim());

        CustomDialogClass.showProgressDialog(context, true);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("KotupdateItem---------", String.valueOf(jsonArray));
                    JSONObject jObj1 = jsonArray.getJSONObject(0);
                    JSONArray GETKOT = jObj1.getJSONArray("STATUS");
                    Log.d("KotupdateItem_INSERT_AP", String.valueOf(GETKOT));
                    for (int i = 0; i < GETKOT.length(); i++) {
                        JSONObject explrObject = GETKOT.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("GETKOTItem------", String.valueOf(status));
                            // getKotJson(response, status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }

    public void onSave(View v){

        size = this.itemOrderList.size();
        if (size > 0) {

            if (onSaveFlag) {
                onSaveFlag=false;
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = df.format(c.getTime());
                System.out.println("Date Format-" + formattedDate);

                System.out.println("KOT Number --" + kotNumber);
           //     KotupdateItem(kotNumber);
                getKotNumber(sp.getString("NAME", "Name"), tableNo, formattedDate.trim(), sp.getString("FIN_YEAR", "FIN_YEAR"));




               // update_item();
            }
        }
        else {//  858952
            generalOperationTools.showSnackMessage("PLEASE ORDER AT LEAST ONE ITEM", "OK", coordinatorLayout);
        }
    }
//    private void update_item(){
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("FLAG", "UPDATE_KOT");
//        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
//        params.put("TABLENUM", tableNo);
//        params.put("PNO", "6");
//        params.put("FROMDATE", formattedDate);
//        params.put("TODATE", formattedDate);
//
//        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
//        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
//            @Override
//            public void onServiceError(String error) {
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
//            }
//
//            @Override
//            public void onServiceResponse(String response) {
//                int status = 0;
//                JSONArray jsonArray = null;
//                try {
//                    jsonArray = new JSONArray(response.trim());
//                    Log.d("update----", String.valueOf(jsonArray));
//                    JSONObject jObj1 = jsonArray.getJSONObject(1);
//                    JSONArray STATUS = jObj1.getJSONArray("STATUS");
//                    Log.d("STATUS------------", String.valueOf(STATUS));
////                    update_data.clear();
//
//                    for (int j = 0 ;j<STATUS.length();j++){
//                        JSONObject explrObject = STATUS.getJSONObject(j);
//
//                        if (j == 0) {
//                            status = Integer.parseInt(explrObject.getString("STATUS"));
//                            Log.d("INSERT_APP_STATUS------", String.valueOf(status));
//                            System.out.println(response);
//                            update(response, status);
//                        }
//                    }
//                } catch (JSONException e) {
//                    CustomDialogClass.showProgressDialog(context, false);
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    private void update(String response, int status) {
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(response.trim());
//
//            if (status == 1) {
//
////                GsonBuilder builder = new GsonBuilder();
////                Gson mGson = builder.create();
////                List<ItemObject> posts = new ArrayList<ItemObject>();
////                posts = Arrays.asList(mGson.fromJson(response, ItemObject[].class));
////
////                adapter = new RecyclerViewAdapter(Kot_updation.this, posts);
////                recyclerView.setAdapter(adapter);
//                JSONObject jObj = jsonArray.getJSONObject(0);
//                JSONArray update = jObj.getJSONArray("KOT_UPDATE");
//                Log.d("update------------", String.valueOf(update));
//
//                for (int i = 0; i<update.length();i++){
//                    JSONObject kot_update = update.getJSONObject(i);
//                    update_set_get objcont = new update_set_get();
//                    ItemModel itemList = new ItemModel();
//                    ItemEditOrderModel iom = new ItemEditOrderModel();
//                    String  TABLENUM = kot_update.getString("TABLENUM");
//                    kotNumber = kot_update.getString("KOTNUMBER");
//                    ITEMCD = kot_update.getString("ITEMCD");
//                    ITEMDESCR = kot_update.getString("ITEMDESCR");
//                    String QTY = kot_update.getString("QTY");
//                    String  RATE = kot_update.getString("RATE");
//                    String  VALUE = kot_update.getString("VALUE");
//                    String  COLNAME = kot_update.getString("COLNAME");
//
//                    //  arrlist.add(objcont);
//                    Log.d("update----------",TABLENUM+ " "+kotNumber+ " "+ITEMCD+" "+ITEMDESCR+""+QTY+" "+RATE+""+" "+VALUE+" "+COLNAME+" ");
////                    Intent intent = new Intent (Kot_updation.this, EditOrderTakingActivity.class);
////                    Bundle bundle = new Bundle();
////                    bundle.putString("UPDATE", kot_update.getString("COLNAME"));
////                    intent.putExtras(bundle);
////                    startActivity(intent);
//                    Log.d("ITEMCD-----",ITEMCD);
//
//
//                }
//
//
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
//            } else {
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
//            }
//        } catch (JSONException e) {
//            CustomDialogClass.showProgressDialog(context, false);
//            e.printStackTrace();
//        }
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String subTypeId=data.getStringExtra("SUBTYPE_ID");
                clearItemsAdapter(subTypeId.trim());
            }
        }
    }//onActivityResult

    private void clearItemsAdapter(final String subTypeId) {

        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Edit.this, true);
        itemHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
            @Override
            public void cleanStatus(boolean cleanStatus) {
                getItemsByTypeId(subTypeId);
            }
        });
    }

    private void searchItems(final String searchStr){

        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Edit.this, true);
        itemHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
            @Override
            public void cleanStatus(boolean cleanStatus) {
                getItemsBySearchString(searchStr);
            }
        });
    }

    private void getItemsBySearchString(String searchStr) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "TOP_20_1");
        params.put("SPNAME", "SP_POS_KOTBILLING_SV");
        params.put("PNO", "4");
        params.put("DESCR", searchStr.trim());

        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray Search = jObj1.getJSONArray("STATUS");
                    Log.d("Search------------", String.valueOf(Search));
                    for (int i = 0; i < Search.length(); i++) {
                        JSONObject explrObject = Search.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("Search------", String.valueOf(status));
                            parseValidTableJson(response, status);
                        }
                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }


    private void getItemsByTypeId(String subTypeId) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "TOP_30_SUBTYPE_S");
        params.put("SPNAME", "SP_POS_KOTBILLING_SV");
        params.put("PNO", "5");
        params.put("DESCR", "");
        params.put("ITEMSUBTYPECODE", subTypeId.trim());

        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));

                            parseValidTableJson(response, status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void getKotNumber(String createdBy, String tableNo, String orderDate, String finYr) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "GET_KOT_NUMBER");
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("PNO", "4");
        //params.put("CREATEDBY", createdBy.trim());
        params.put("TABLENUM", tableNo.trim());
        //params.put("ORDERDATE", orderDate.trim());
        //params.put("FINYR", finYr.trim());

        CustomDialogClass.showProgressDialog(context, true);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("getkot--", String.valueOf(jsonArray));
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray GETKOT = jObj1.getJSONArray("STATUS");
                    Log.d("INSERT_APP------------", String.valueOf(GETKOT));
                    for (int i = 0; i < GETKOT.length(); i++) {
                        JSONObject explrObject = GETKOT.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("GETKOT------", String.valueOf(status));
                            getKotJson(response, status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void getKotJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if (status == 1) {




                System.out.println("RESPONSE OF UPDATE KOT- "+response);
                JSONObject jObj1 = jsonArray.getJSONObject(0);
                JSONArray kotJsonArray = jObj1.getJSONArray("KOT_NUMBER");
                for (int i = 0; i < kotJsonArray.length(); i++) {
                    JSONObject kot = kotJsonArray.getJSONObject(i);
                    kotNumber = kot.getString("KOTNUMBER").trim();

                    Log.d("kotNumber------", kotNumber);
                    String   ITEMCD = kot.getString("ITEMCD");
                    String   ITEMDESCR = kot.getString("ITEMDESCR");
                    String QTY = kot.getString("QTY");
                    String RATE = kot.getString("RATE");
                    String VALUE = kot.getString("VALUE");
                    String SERIAL = kot.getString("SERIAL");
                    String KOTNUMBER = kot.getString("KOTNUMBER");
                    String CODE = kot.getString("CODE");
                    String COLNAME = kot.getString("COLNAME");
                    Log.d("GetKot-----",ITEMCD+ " "+ITEMDESCR+ " "+QTY+ " "+RATE+ " "+VALUE+ " "+SERIAL+ " "+KOTNUMBER+ " "+CODE+ " "+COLNAME+" ");

                    for(int k=0;k<size;k++) {
                        double totalVal = 0.0;

                        if (this.itemOrderList.get(k).getItemChargaqbleOrNot().trim().equals("1")) {
                            totalVal = totalVal + Double.valueOf(this.itemOrderList.get(k).getItemValue().trim());
                        } else
                            totalVal = 0.0;

//                        prepareItemOrderOnSave(kotNumber, String.valueOf(k), this.itemOrderList.get(k).getItemNameId().trim(), addRemarksEditText.getText().toString().trim(), this.itemOrderList.get(k).getItemDescription().trim(),
//                                this.itemOrderList.get(k).getItemQty().trim(), this.itemOrderList.get(k).getItemRate().trim(), String.valueOf(totalVal), sp.getString("NAME", "Name"), formattedDate,
//                                sp.getString("NAME", "Name"), itemOrderList,k);

//                        prepareItemOrderOnSave_HDR_DTL(kotNumber,String.valueOf(itemOrderList.size()), formattedDate,
//                                String.valueOf(k), this.itemOrderList.get(k).getItemName().trim(), this.itemOrderList.get(k).getItemQty().trim(),
//                                this.itemOrderList.get(k).getItemRate().trim(), String.valueOf(totalVal), "",
//                                sp.getString("OUTLET", "Outlet Name # 0"), sp.getString("NAME", "Name"),
//                                tableNo, sp.getString("FIN_YEAR", "FIN_YEAR"),itemOrderList,k);


//                        prepareItemOrderOnSave_HDR_DTL(kotNumber,String.valueOf(k),this.itemOrderList.get(k).getItemNameId().trim(),
//                                addRemarksEditText.getText().toString().trim(),
//                                this.itemOrderList.get(k).getItemDescription().trim(),
//                                this.itemOrderList.get(k).getItemQty().trim(),
//                                this.itemOrderList.get(k).getItemRate().trim(),
//                                String.valueOf(totalVal),
//                                sp.getString("NAME", "Name"),
//                                formattedDate,
//                                sp.getString("OUTLET", "Outlet Name # 0"),
//                                tableNo, sp.getString("FIN_YEAR", "FIN_YEAR"),itemOrderList,k);

//                        prepareItemOrderOnSave_HDR_DTL(kotNumber,String.valueOf(itemOrderList.size()),
//                                formattedDate,
//                                String.valueOf(k), this.itemOrderList.get(k).getItemNameId().trim(),
//                                this.itemOrderList.get(k).getItemQty().trim(),
//                                this.itemOrderList.get(k).getItemRate().trim(),
//                                String.valueOf(totalVal),
//                                addRemarksEditText.getText().toString().trim(),
//                           //    sp.getString("OUTLET", "Outlet Name # 0"), tableNo,sp.getString("NAME", "Name"),
//                                sp.getString("OUTLET", "Outlet Name # 0"),sp.getString("NAME", "Name"), tableNo,
//                                 sp.getString("FIN_YEAR", "FIN_YEAR"),itemOrderList,k);

                        prepareItemOrderOnSave_HDR_DTL(kotNumber,String.valueOf(itemOrderList.size()), formattedDate,
                                String.valueOf(k), this.itemOrderList.get(k).getItemName().trim(), this.itemOrderList.get(k).getItemQty().trim(),
                                this.itemOrderList.get(k).getItemRate().trim(), String.valueOf(totalVal), "",
                                sp.getString("OUTLET", "Outlet Name # 0"), sp.getString("NAME", "Name"),
                                tableNo, sp.getString("FIN_YEAR", "FIN_YEAR"),itemOrderList,k);

                    }
                    /**For table data adapter***/
//                    if (i == 1) {
//                        JSONArray kotJsonArray = explrObject.getJSONArray("KOT_NUMBER");
//                        for (int j = 0; j < kotJsonArray.length(); j++) {
//                            JSONObject kotJsonObj = kotJsonArray.getJSONObject(j);
//                            if(j==0) {
//                                kotNumber = kotJsonObj.getString("KOTNUMBER").trim();
//
//                                for(int k=0;k<size;k++) {
//                                    double totalVal = 0.0;
//
//                                    if (this.itemOrderList.get(k).getItemChargaqbleOrNot().trim().equals("1")) {
//                                        totalVal = totalVal + Double.valueOf(this.itemOrderList.get(k).getItemValue().trim());
//                                    } else
//                                        totalVal = 0.0;
//
//                                    prepareItemOrderOnSave(kotNumber, String.valueOf(k), this.itemOrderList.get(k).getItemNameId().trim(), addRemarksEditText.getText().toString().trim(), this.itemOrderList.get(k).getItemDescription().trim(),
//                                            this.itemOrderList.get(k).getItemQty().trim(), this.itemOrderList.get(k).getItemRate().trim(), String.valueOf(totalVal), sp.getString("NAME", "Name"), formattedDate,
//                                            sp.getString("NAME", "Name"), itemOrderList,k);
//                                }
//                            }
//                        }
//                    }


                }

                generalOperationTools.showSnackMessage("KOT NUMBER FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("SORRY!!! WE CAN NOT UPDATE, KOT NUMBER  NOT FOUND.", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }
    }
    private void prepareItemOrderOnSave_HDR_DTL(final String kotNo , String dtlcount, String orderDate, String serial, String itemCd, String qty, String rate, String value, String remarks, String location, String userName, String tableNum, String finYr,
                                                final List<ItemEditOrderModel> itemOrderList, final int k) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "HDR_DTL");
        params.put("KOTNUMBER",kotNo.trim());
        params.put("DTLCOUNT",dtlcount.trim());
        System.out.println("dtlcount"+dtlcount);
        params.put("PNO", "16");
        params.put("ORDERDATE", orderDate.trim());
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("SERIAL", serial.trim());
       params.put("ITEMCD", itemCd.trim());
      // params.put("ITEMCD", ITEMCD);
        params.put("QTY", qty.trim());
        params.put("RATE", rate.trim());
        params.put("VALUE", value.trim());
        params.put("REMARKS", remarks.trim());
        params.put("LOCATIONPREFIX", location.trim());
        params.put("TABLENUM", tableNum.trim());
        params.put("USERNAME", userName.trim());
        params.put("FINYR", finYr.trim());
        if(!CustomDialogClass.isShowProgress())
            CustomDialogClass.showProgressDialog(context, true);
        else
            CustomDialogClass.showProgressDialog(context, false);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                Log.d("jsonArray_hdrresponse", response);
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("jsonArray_hdr1----", String.valueOf(jsonArray));
                    JSONObject jObj = jsonArray.getJSONObject(1);
                    JSONArray STATUS = jObj.getJSONArray("STATUS");
                    Log.d("STATUS------------", String.valueOf(STATUS));
                    for (int i = 0; i < STATUS.length(); i++) {
                        JSONObject explrObject = STATUS.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("HDR------", String.valueOf(status));
                            //  parseHeaderKotJson(response, status, itemOrderList);
                            //  goToPrintActivity(itemOrderList,kotNumber);
                            parseHeaderKotJson(response, status, itemOrderList);
                            // parseItemOrderWithKotJson(response, status, itemOrderList,kotNo.trim(),k);
                        }

                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }


    private void parseHeaderKotJson(String response, int status, final List<ItemEditOrderModel> itemOrderList) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if (status == 1) {

                JSONObject jObj1 = jsonArray.getJSONObject(0);
                JSONArray KOTNUMBER = jObj1.getJSONArray("KOTNUMBER");
                for (int j = 0;j<KOTNUMBER.length();j++){
                    JSONObject explrObject = KOTNUMBER.getJSONObject(j);
                    //    String KOTNUM = explrObject.getString("KOTNUM");
                    kotNumber = explrObject.getString("KOTNUM").trim();
                    String COLNAME = explrObject.getString("COLNAME");
                    Log.d("KOTNUM--COLNAME",kotNumber+ " " +COLNAME+ " ");
                    goToPrintActivity(itemOrderList,kotNumber);
                }

                generalOperationTools.showSnackMessage("KOT NUMBER FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("KOT NUMBER  NOT FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }
    }


    private void goToPrintActivity(List<ItemEditOrderModel> itemOrderList, String kotNo) {

        CustomDialogClass.showProgressDialog(context, false);
        onSaveFlag=true;
        JSONArray jsonArray = new JSONArray();
        double totalVal =   0.0;
        for (int i = 0; i < itemOrderList.size(); i++) {

            JSONObject orderItem = new JSONObject();
            try {
                //   orderItem.put("ITEM_ID", itemOrderList.get(i).getItemNameId().trim());
                orderItem.put("ITEM_NAME", itemOrderList.get(i).getItemName().trim());
                orderItem.put("ITEM_QTY", itemOrderList.get(i).getItemQty().trim());
                orderItem.put("ITEM_RATE", itemOrderList.get(i).getItemRate().trim());
                if(itemOrderList.get(i).getItemChargaqbleOrNot().trim().equals("1")) {
                    //   orderItem.put("ITEM_VALUE", itemOrderList.get(i).getItemValue().trim());
                    orderItem.put("ITEM_VALUE","Chargable");
                    totalVal    =   totalVal+Double.valueOf(itemOrderList.get(i).getItemValue().trim());
                    //   orderItem.put("ITEM_CHARGE","Chargable");
                }
                else
                    //  orderItem.put("ITEM_VALUE", "0.0");
                    orderItem.put("ITEM_VALUE","Non Chargable");
                orderItem.put("ITEM_NC", itemOrderList.get(i).getItemChargaqbleOrNot().trim());
                orderItem.put("ITEM_DES", itemOrderList.get(i).getItemDescription().trim());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            jsonArray.put(orderItem);
        }

        JSONObject orderGenInfo = new JSONObject();
        try {
            orderGenInfo.put("TABLE_NO", tableNo);
            orderGenInfo.put("SESSION_DETAILS", sp.getString("SESSION", "Session Name # 0"));
            orderGenInfo.put("OUTLET_DETAILS", sp.getString("OUTLET", "Outlet Name # 0"));
            orderGenInfo.put("USER_NAME", sp.getString("NAME", "Name"));
            orderGenInfo.put("ORDER_ITEMS",jsonArray);
            orderGenInfo.put("REMARKS", addRemarksEditText.getText().toString().trim());
            orderGenInfo.put("TOT_PRICE", totalVal);
            orderGenInfo.put("KOT_NO", kotNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonStr = orderGenInfo.toString();
        System.out.println("fINAL jsonString: "+jsonStr);

        //  Intent intent   =   new Intent(EditOrderTakingActivity.this,PrinterActivity.class);
        Intent intent   =   new Intent(EditOrderTakingActivity_Edit.this,Printer_test.class);
        Bundle bundle   =   new Bundle();
        bundle.putString("JSON_ORDER",jsonStr);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
