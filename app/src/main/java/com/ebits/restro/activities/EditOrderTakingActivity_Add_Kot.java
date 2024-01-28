package com.ebits.restro.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.model.ItemOrderModel;
import com.ebits.restro.activities.model.ItemOrderModel_new;
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

/**
 * Created by Admin on 17/10/2017.
 */

public class EditOrderTakingActivity_Add_Kot extends AppCompatActivity {
    private TextView userNameTextView, tableNoTextView, sessionNameTextView, outletNameTextView, loginTimeTextView;
    private TextView toolbar_tableNoTextView, toolbar_timeTextView, toolbar_sessionTextView;
    private NetworkImageView profileImageView;
    private LinearLayout toolbar_linearParent;
    private CoordinatorLayout coordinatorLayout;

    private SharedPreferences sp;
    private GeneralOperationTools generalOperationTools;
    private ItemsHolderAdapter itemHolderAdapter;
    private List<ItemModel> itemList;
    private List<Remarks_set_Get> remarks_set_gets;
    private RemarksTypeHolderAdapter remarksTypeHolderAdapter;
    private ItemsOrderHolderAdapter itemOrderHolderAdapter;
    private List<ItemOrderModel> itemOrderList;
    private List<ItemOrderModel_new> itemOrderListnew;
    private Context context = this;
    private String tableNo="",kotNumber="",formattedDate="";
    private int size=0;
    private int sizenew=3;
    private int sizee=1;
    private boolean onSaveFlag=true;

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
    private ImageButton lockBtn, saveBtn, cancelBtn,BackBtn,RmksBtn,onCancleRemarks,onExistingItem;
    Button PaxBtn;
    private RecyclerView order_recycler_view;
    private Object v;
    ImageButton foodOrder_saveBtn;
    String kotnumber_new;
    String ITEMCD;
    RecyclerView remarks_recycler_view;
    int remarksposition = -1;
    ArrayList<Remarks_set_Get> arrlist1 = new ArrayList<Remarks_set_Get>();
    String   NAME;
    EditText PaxEditText;
    private String PAXXX="NO";
    Dialog dialogpax;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_making_parent_add);

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
        RmksBtn = (ImageButton)findViewById(R.id.foodOrder_RemarksBtn);
        searchItemsEdit = (EditText) findViewById(R.id.searchItems);
        onExistingItem = (ImageButton)findViewById(R.id.existingItem);
        clearSearchText = (Button) findViewById(R.id.clearSearchText);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        expandButton = (ImageButton) findViewById(R.id.expandButton);
        recycler_view_search = (RecyclerView) findViewById(R.id.recycler_view_search);
        PaxEditText = (EditText)findViewById(R.id.dialog_PaxEditText);
        onCancleRemarks = (ImageButton)findViewById(R.id.foodOrder_RemarkCancleBtn);

        /***
         * Order making section
         * ***/
        addRemarksEditText = (EditText) findViewById(R.id.foodOrder_addRemarksEditText);
        BackBtn = (ImageButton)findViewById(R.id.foodOrder_BackBtn);
        PaxBtn  = (Button) findViewById(R.id.foodOrder_PaxBtn);
       // lockBtn = (ImageButton) findViewById(R.id.foodOrder_lockBtn);
        saveBtn = (ImageButton) findViewById(R.id.foodOrder_saveBtn);
        cancelBtn = (ImageButton) findViewById(R.id.foodOrder_cancelBtn);
        order_recycler_view = (RecyclerView) findViewById(R.id.order_recycler_view);
        remarks_recycler_view = (RecyclerView)findViewById(R.id.remarks_recycler_view);


        initOrderMakingItemSelection();
        initOrderMaking();

        searchEditOnChangeListener();
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
     public void onExistingItem(View v){
         Intent intent = new Intent(EditOrderTakingActivity_Add_Kot.this,ExistingItem.class);
         intent.putExtra("Table_No", tableNo);
         startActivity(intent);


    }
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
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

    /***
     * Left panel order section
     */
    private void initOrderMakingItemSelection() {
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();

        itemList = new ArrayList<>();
        itemHolderAdapter = new ItemsHolderAdapter(EditOrderTakingActivity_Add_Kot.this, itemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EditOrderTakingActivity_Add_Kot.this, 4);
        recycler_view_search.setLayoutManager(mLayoutManager);
        recycler_view_search.addItemDecoration(new GridSpacingItemDecoration(4, GridSpacingItemDecoration.dpToPx(4, EditOrderTakingActivity_Add_Kot.this), true));
        recycler_view_search.setItemAnimator(new DefaultItemAnimator());
        recycler_view_search.setAdapter(itemHolderAdapter);

        prepareItems("TOP_20_1", "SP_POS_KOTBILLING_SV", "4");

        recycler_view_search.addOnItemTouchListener(new OrderTakingActivityNew.RecyclerTouchListener(getApplicationContext(), recycler_view_search, new OrderTakingActivityNew.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ItemModel itemModel = itemList.get(position);

                if (!hasDuplicates(itemModel.getItemNameId(),itemModel.getItemName().trim())) {
                   // Toast.makeText(getApplicationContext(), itemModel.getItemName() + " is selected!", Toast.LENGTH_SHORT).show();
                    ItemOrderModel iom = new ItemOrderModel();

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
             //       Toast.makeText(getApplicationContext(), itemModel.getItemName() + " already ordered", Toast.LENGTH_SHORT).show();
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
                    String itemNameWithDescp=   itemOrderList.get(i).getItemName().trim()+" "+itemOrderList.get(i).getItemDescription().trim();
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
        itemOrderHolderAdapter = new ItemsOrderHolderAdapter(EditOrderTakingActivity_Add_Kot.this, itemOrderList);

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

     //   CustomDialogClass.showProgressDialog(context, true);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
             //   CustomDialogClass.showProgressDialog(context, false);
             //   generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
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
                            remarkssearch();
                        }
                    }

                } catch (JSONException e) {
                 //   CustomDialogClass.showProgressDialog(context, false);
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


                JSONObject jObj = jsonArray.getJSONObject(0);
                JSONArray TOP_20_ITEM = jObj.getJSONArray("TOP 20 ITEM");
                Log.d("TOP_20_ITEM1----------", String.valueOf(TOP_20_ITEM));
                for (int i = 0; i < TOP_20_ITEM.length(); i++) {
                    JSONObject TOP_20_ITEM_OBJ = TOP_20_ITEM.getJSONObject(i);
                    String MONTHLYCONSUMP = TOP_20_ITEM_OBJ.getString("MONTHLYCONSUMP");
                    Log.d("MONTHLYCONSUMP------",MONTHLYCONSUMP);
                    String ITEMCD = TOP_20_ITEM_OBJ.getString("ITEMCD");
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

            //    CustomDialogClass.showProgressDialog(context, false);
            //    generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
            //    CustomDialogClass.showProgressDialog(context, false);
            //    generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
        //    CustomDialogClass.showProgressDialog(context, false);
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
    public void onCancleRemarks(View v){
        remarks_recycler_view.setVisibility(View.GONE);
    }
    public void onRemarks(View v){
        remarks_recycler_view.setVisibility(View.VISIBLE);


    }
    private void remarkssearch(){
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        remarks_set_gets = new ArrayList<>();
        remarksTypeHolderAdapter = new RemarksTypeHolderAdapter(getApplicationContext(),remarks_set_gets);
        remarks_recycler_view = (RecyclerView)findViewById(R.id.remarks_recycler_view);
        remarks_recycler_view.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(EditOrderTakingActivity_Add_Kot.this,1);
        remarks_recycler_view.setLayoutManager(mLayoutManager);
        remarks_recycler_view.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(1), true));
        remarks_recycler_view.setItemAnimator(new DefaultItemAnimator());
        remarks_recycler_view.setAdapter(remarksTypeHolderAdapter);
        remarks_recycler_view.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), remarks_recycler_view, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                remarksposition = position;
              //  Toast.makeText(getApplicationContext(), arrlist1.get(position).getName(), Toast.LENGTH_LONG).show();
                addRemarksEditText.setText(arrlist1.get(position).getName());
                remarks_recycler_view.setVisibility(View.GONE);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        Remarks();
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    private void Remarks() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "GET_REMARKS");
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("PNO", "3");



       // CustomDialogClass.showProgressDialog(context,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
//                CustomDialogClass.showProgressDialog(context,false);
//                generalOperationTools.showSnackMessage(error.toUpperCase()
//                        ,"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("REmarks----", String.valueOf(jsonArray));
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray STATUS = jObj1.getJSONArray("STATUS");
                    Log.d("remarks------------", String.valueOf(STATUS));
//                    update_data.clear();

                    for (int j = 0 ;j<STATUS.length();j++){
                        JSONObject explrObject = STATUS.getJSONObject(j);

                        if (j == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("Remarks_STATUS------", String.valueOf(status));
                            System.out.println(response);
                            update_json(response, status);

                        }
                    }
                } catch (JSONException e) {
              //      CustomDialogClass.showProgressDialog(context,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void update_json(String response, int status) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if (status == 1) {

                JSONObject jObj = jsonArray.getJSONObject(0);
                JSONArray remarks = jObj.getJSONArray("REMARKS");
                Log.d("Remarks------------", String.valueOf(remarks));

                for (int i = 0; i<remarks.length();i++){
                    JSONObject remarksJSONObject = remarks.getJSONObject(i);
                    final Remarks_set_Get objcont = new Remarks_set_Get();

                    NAME = remarksJSONObject.getString("DESCRIPTION");
                    String  REMARKS = remarksJSONObject.getString("REMARKS");
                    String  COLNAME = remarksJSONObject.getString("COLNAME");
                    objcont.setName(NAME);
                    objcont.setDescripition(REMARKS);
                    objcont.setRemarks(COLNAME);
                    remarks_set_gets.add(objcont);
                    remarksTypeHolderAdapter.notifyDataSetChanged();
                    arrlist1.add(objcont);


                    Log.d("Rems----------",NAME+ " "+REMARKS+ " "+COLNAME+" ");





                }


           //     CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
         //   CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }

    }
    public void onCancel(View v){
        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Add_Kot.this, true);
        itemOrderHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
            @Override
            public void cleanStatus(boolean cleanStatus) {
                CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Add_Kot.this, false);
            }
        });
    }
    public void onPax(View v){
        dialogpax = new Dialog(EditOrderTakingActivity_Add_Kot.this, R.style.TransparentProgressDialog);
        dialogpax.setContentView(R.layout.pax_dialog);
        dialogpax.setCancelable(false);
        PAXXX="NO";
        PaxEditText = (EditText)dialogpax.findViewById(R.id.dialog_PaxEditText);
        final Button OkBtn = (Button)dialogpax.findViewById(R.id.dialog_OkBtn);
        final Button cancleBtn = (Button)dialogpax.findViewById(R.id.dialog_cancleBtn);
        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaxEditText.getText().toString().trim();
                PAXXX="YES";
                dialogpax.dismiss();

            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpax.dismiss();
            }
        });
        PAXXX="NO";
        dialogpax.show();
    }
    public void onBack(View v){
        Intent i = new Intent(EditOrderTakingActivity_Add_Kot.this, NavigationDrawerActivity.class);
        startActivity(i);

    }
//    public void onLock(View v){
//        final Dialog dialogLock = new Dialog(EditOrderTakingActivity_Add_Kot.this, R.style.TransparentProgressDialog);
//        dialogLock.setContentView(R.layout.lock_dialog);
//        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogLock.setCancelable(false);
//        final EditText userName 	= (EditText) dialogLock.findViewById(R.id.dialog_userNameEditText);
//        final EditText password 	= (EditText) dialogLock.findViewById(R.id.dialog_passwordEditText);
//        Button unlockBtn      = (Button) dialogLock.findViewById(R.id.dialog_unlockBtn);
//
//        unlockBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if(userName.getText().toString().trim().equalsIgnoreCase(sp.getString("USERNAME","").trim())){
//                    if(password.getText().toString().trim().equalsIgnoreCase(sp.getString("PASSWORD","").trim())){
//                        dialogLock.dismiss();
//                    }
//                    else{
//                        generalOperationTools.showSnackMessage("PASSWORD DOES NOT MATCHED", "OK", coordinatorLayout);
//                    }
//                }
//                else{
//                    generalOperationTools.showSnackMessage("USER NAME DOES NOT MATCHED", "OK", coordinatorLayout);
//                }
//
//            }
//        });
//
//        dialogLock.show();
//    }

    public void onSave(View v){
        sizenew = this.itemOrderList.size();
        if (sizenew > 0) {
            if (onSaveFlag) {
                onSaveFlag = false;
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = df.format(c.getTime());
                System.out.println("dddddddddddddd"+sizenew);
                for (int kk = 0; kk < sizenew; kk++) {

                    double totalVal = 0.0;

                    if (this.itemOrderList.get(kk).getItemChargaqbleOrNot().trim().equals("1")) {
                        totalVal = totalVal + Double.valueOf(this.itemOrderList.get(kk).getItemValue().trim());
                    } else
                        totalVal = 0.0;
                    if (PAXXX=="YES"){
                        prepareItemOrderOnSave_HDR_DTL(String.valueOf(itemOrderList.size()), formattedDate,
                                String.valueOf(kk), this.itemOrderList.get(kk).getItemName().trim(), this.itemOrderList.get(kk).getItemQty().trim(),
                                this.itemOrderList.get(kk).getItemRate().trim(), String.valueOf(totalVal), addRemarksEditText.getText().toString().trim(),
                                sp.getString("OUTLET", "Outlet Name # 0"), sp.getString("NAME", "Name"),
                                tableNo,sp.getString("FIN_YEAR", "FIN_YEAR"),PaxEditText.getText().toString(),itemOrderList,kk);
                    }
                    if (PAXXX=="NO"){
                        prepareItemOrderOnSave_HDR_DTL(String.valueOf(itemOrderList.size()), formattedDate,
                                String.valueOf(kk), this.itemOrderList.get(kk).getItemName().trim(), this.itemOrderList.get(kk).getItemQty().trim(),
                                this.itemOrderList.get(kk).getItemRate().trim(), String.valueOf(totalVal), addRemarksEditText.getText().toString().trim(),
                                sp.getString("OUTLET", "Outlet Name # 0"), sp.getString("NAME", "Name"),
                                tableNo,sp.getString("FIN_YEAR", "FIN_YEAR"),"0",itemOrderList,kk);
                    }


                }

            }
        }
        else {//  858952
            generalOperationTools.showSnackMessage("PLEASE ORDER AT LEAST ONE ITEM", "OK", coordinatorLayout);
        }


    }

    private void prepareItemOrderOnSave_HDR_DTL(String dtlcount, String orderDate, String serial, String itemCd, String qty, String rate, String value, String remarks, String location, String userName, String tableNum, String finYr,
                                                String Pax, final List<ItemOrderModel> itemOrderList, int kk) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "HDR_DTL");
        params.put("DTLCOUNT",dtlcount.trim());
        System.out.println("dtlcount"+dtlcount);
        params.put("PNO", "16");
        params.put("ORDERDATE", orderDate.trim());
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("SERIAL", serial.trim());
        params.put("ITEMCD", itemCd.trim());//ITEMCD
      //  params.put("ITEMCD", ITEMCD);
        params.put("QTY", qty.trim());
        params.put("RATE", rate.trim());
        params.put("VALUE", value.trim());
        params.put("REMARKS", remarks.trim());
        params.put("LOCATIONPREFIX", location.trim());
        params.put("TABLENUM", tableNum.trim());
        params.put("USERNAME", userName.trim());
        params.put("FINYR", finYr.trim());
        params.put("PAX", Pax.trim());
//        if(!CustomDialogClass.isShowProgress())
//            CustomDialogClass.showProgressDialog(context, true);
//        else
//            CustomDialogClass.showProgressDialog(context, false);
        JsonParsingUtils jsonParsingUtils = JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                Log.d("hdrresponse", response);
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    Log.d("jsonArray_hdr----", String.valueOf(jsonArray));
                    JSONObject jObj = jsonArray.getJSONObject(1);
                    JSONArray STATUS = jObj.getJSONArray("STATUS");
                    Log.d("STATUS------------", String.valueOf(STATUS));
                    for (int i = 0; i < STATUS.length(); i++) {
                        JSONObject explrObject = STATUS.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("HDR------", String.valueOf(status));
                            //  parseHeaderKotJson(response, status, itemOrderList);
                            //goToPrintActivity(itemOrderList,kotNumber);
                            parseHeaderKotJson(response, status, itemOrderList);
                        }

                    }
                } catch (JSONException e) {
                 //   CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseHeaderKotJson(String response, int status, final List<ItemOrderModel> itemOrderList) {

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
                    Log.d("KOTNUM--COLNAME",kotNumber+ "" +COLNAME+ "");
                    goToPrintActivity(itemOrderList,kotNumber);
                }


           //     generalOperationTools.showSnackMessage("KOT NUMBER FOUND", "OK", coordinatorLayout);
            } else {
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("KOT NUMBER  NOT FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
          //  CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }
    }


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

        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Add_Kot.this, true);
        itemHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
            @Override
            public void cleanStatus(boolean cleanStatus) {
                getItemsByTypeId(subTypeId);
            }
        });
    }

    private void searchItems(final String searchStr){

        CustomDialogClass.showProgressDialog(EditOrderTakingActivity_Add_Kot.this, true);
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
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray Search1 = jObj1.getJSONArray("STATUS");
                    Log.d("Search1------------", String.valueOf(Search1));
                    for (int i = 0; i < Search1.length(); i++) {
                        //   JSONObject explrObject = jsonArray.getJSONObject(i);
                        JSONObject explrObject = Search1.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("Search------", String.valueOf(status));
                            parseValidTableJson(response, status);
                        }
                    }
                } catch (JSONException e) {
                 //   CustomDialogClass.showProgressDialog(context, false);
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
//                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage(error.toUpperCase(), "OK", coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status = 0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());
                    JSONObject jObj1 = jsonArray.getJSONObject(1);
                    JSONArray Search1_item = jObj1.getJSONArray("STATUS");
                    Log.d("Search1_item--", String.valueOf(Search1_item));
                    for (int i = 0; i < Search1_item.length(); i++) {
                        JSONObject explrObject = Search1_item.getJSONObject(i);

                        if (i == 0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            Log.d("Search1_item------", String.valueOf(status));
                            parseValidTableJson(response, status);
                        }
                    }

                } catch (JSONException e) {
                 //   CustomDialogClass.showProgressDialog(context, false);
                    e.printStackTrace();
                }
            }
        });
    }





    private void goToPrintActivity(List<ItemOrderModel> itemOrderList, String kotNo) {

      //  CustomDialogClass.showProgressDialog(context, false);
        onSaveFlag=true;

        JSONArray jsonArray = new JSONArray();
        double totalVal =   0.0;
        for (int i = 0; i < itemOrderList.size(); i++) {

            JSONObject orderItem = new JSONObject();
            try {
                orderItem.put("ITEM_ID", itemOrderList.get(i).getItemNameId().trim());
                orderItem.put("ITEM_NAME", itemOrderList.get(i).getItemName().trim());
                orderItem.put("ITEM_QTY", itemOrderList.get(i).getItemQty().trim());
                orderItem.put("ITEM_RATE", itemOrderList.get(i).getItemRate().trim());
                if(itemOrderList.get(i).getItemChargaqbleOrNot().trim().equals("1")) {
                    //   orderItem.put("ITEM_VALUE", itemOrderList.get(i).getItemValue().trim());
                    orderItem.put("ITEM_VALUE","Chargable");
                    totalVal    =   totalVal+Double.valueOf(itemOrderList.get(i).getItemValue().trim());
//                    orderItem.put("ITEM_CHARGE","Chargable"+itemOrderList.get(i).getCharge().trim());
                }
                else
                    //   orderItem.put("ITEM_VALUE", "0.0");
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
        Intent intent   =   new Intent(EditOrderTakingActivity_Add_Kot.this,Printer_test.class);
        Bundle bundle   =   new Bundle();
        bundle.putString("JSON_ORDER",jsonStr);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
