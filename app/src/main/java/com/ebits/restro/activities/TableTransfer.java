package com.ebits.restro.activities;

import android.app.Activity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.ItemsHolderAdapter;
import com.ebits.restro.activities.adapter.ItemsOrderHolderAdapter;
import com.ebits.restro.activities.adapter.TableHolderAdapter;
import com.ebits.restro.activities.adapter.TableHolderAdapter_booked;
import com.ebits.restro.activities.adapter.TableHolderAdapter_kot_add;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemEditOrderModel;
import com.ebits.restro.activities.model.ItemModel;
import com.ebits.restro.activities.model.ItemOrderModel;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 11/11/2017.
 */

public class TableTransfer extends Activity  implements CallbackItemTouch{
   // private final Listener listener;
    private TextView userNameTextView, tableNoTextView, sessionNameTextView, outletNameTextView, loginTimeTextView;
    private TextView toolbar_tableNoTextView, toolbar_timeTextView, toolbar_sessionTextView;
    private NetworkImageView profileImageView;
    private LinearLayout toolbar_linearParent;
    private CoordinatorLayout coordinatorLayout;

    private SharedPreferences sp;
    private GeneralOperationTools generalOperationTools;
    private ItemsHolderAdapter itemHolderAdapter;
    private List<ItemModel> itemList;
    private List<TableModel> tableList;
    private ItemsOrderHolderAdapter itemOrderHolderAdapter;
    private TableHolderAdapter_new tableHolderAdapter_transfer;
    private TableHolderAdapter_kot_add tableHolderAdapter_kot_add;
    private TableHolderAdapter_booked tableHolderAdapter_booked;
    private TableHolderAdapter tableHolderAdapter;
    private List<ItemOrderModel> itemOrderList;
    private Context context = this;
    private String tableNo="",kotNumber="",formattedDate="";
    private String TABLENUM2="";
    private int size=0;
    private boolean onSaveFlag=true;
    private String kot = "";
    int TablePosition = -1;
    int KotPosition1 = -1;
    private String TableCheckStatus="YES";
    private String KotCheckStatus="";
    /***
     * Order making items section
     ***/
    private EditText searchItemsEdit;
    private Button clearSearchText;
    private ImageButton searchButton, expandButton;
    private RecyclerView recycler_view_table;
    private RecyclerView tablde_recycler_view_booked;
    /***
     * Order making section
     ***/
    private EditText addRemarksEditText;
    private ImageButton lockBtn, saveBtn, cancelBtn;
    private RecyclerView order_recycler_view;
    private RecyclerView recycler_view1;
    private String  outletData, outletCode, currentDate;
    private String status;
    private List<update_set_get> itemOrderList1;
    private boolean isDropped = false;
    CheckBox table_check,kot_check;
    private boolean isCheckedValue;
    RelativeLayout relative;
    ArrayList<TableModel> arrlist = new ArrayList<TableModel>();
    ArrayList<TableModel> arrlist1 = new ArrayList<TableModel>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabletransfer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
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
        initOperation_Empty();
        initOperation_getkotTransfer();
        //table_check();
        //selected_kot();


        /***
         * Order making items section
         * ***/
        searchItemsEdit = (EditText) findViewById(R.id.searchItems);
        clearSearchText = (Button) findViewById(R.id.clearSearchText);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        expandButton = (ImageButton) findViewById(R.id.expandButton);
      // recycler_view_table = (RecyclerView) findViewById(R.id.recycler_view_table);



        /***
         * Order making section
         * ***/
        saveBtn = (ImageButton) findViewById(R.id.foodOrder_saveBtn);
        cancelBtn = (ImageButton) findViewById(R.id.foodOrder_cancelBtn);
        order_recycler_view = (RecyclerView) findViewById(R.id.order_recycler_view);
        relative = (RelativeLayout)findViewById(R.id.relative);
//        relative.setVisibility(View.GONE);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        sp  =  getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");
        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TableTransfer.this, NavigationDrawerActivity.class);
                startActivity(i);
            }
        });
        table_check = (CheckBox)findViewById(R.id.table_check);
        kot_check = (CheckBox)findViewById(R.id.kot_check);


    }
     private void table_check(){
         Map<String, String> params = new HashMap<String, String>();
         params.put("FLAG", "TABLE_TRANFER");
         params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
         params.put("TABLENUM", tableNo);
         Log.d("TABLENUM",tableNo);
         params.put("TABLENUM2", arrlist.get(TablePosition).getTableId());
         params.put("PNO", "9");
         params.put("ORDERDATE", currentDate);
         Log.d("ORDERDATE",currentDate);
         params.put("LOCATIONPREFIX", outletCode);
         params.put("LOCATIONPREFIX2", outletCode);
         Log.d("LOCATIONPREFIX2",outletCode);
         params.put("REFTABLENO", tableNo);


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
                     Log.d("ok_update", String.valueOf(jsonArray));
                     JSONObject jObj1 = jsonArray.getJSONObject(0);
                     JSONArray STATUS = jObj1.getJSONArray("STATUS");
                     Log.d("STATUS_ok", String.valueOf(STATUS));
//                    update_data.clear();

                     for (int j = 0 ;j<STATUS.length();j++){
                         JSONObject explrObject = STATUS.getJSONObject(j);

                         if (j == 0) {
                             status = Integer.parseInt(explrObject.getString("STATUS"));
                             Log.d("INSERT_ok", String.valueOf(status));
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


    public void onCheckboxClicked(View v) {
        switch (v.getId()){
            case R.id.kot_check:
                if (kot_check.isChecked()){
                    TableCheckStatus="NO";
                    KotCheckStatus="YES";
                   table_check.setChecked(false);
                    recycler_view1.setVisibility(View.VISIBLE);
                }
               //selected_kot();
                break;
            case R.id.table_check:
                if (table_check.isChecked()){
                    TableCheckStatus="YES";
                    KotCheckStatus="NO";
                    kot_check.setChecked(false);
                  recycler_view1.setVisibility(View.GONE);
//                    table_check();
                }
               // selected_kot();
                break;


        }
    }

     private void selected_kot(){

         Map<String, String> params = new HashMap<String, String>();
         params.put("FLAG", "KOT_TRANFER");
         params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
         params.put("TABLENUM", arrlist.get(TablePosition).getTableId().trim());
         params.put("LOCATIONPREFIX", outletCode.trim());
         params.put("LOCATIONPREFIX2", outletCode.trim());
         params.put("REFTABLENO", tableNo.trim());
         params.put("KOTNUMBER", kot.trim());
         params.put("ORDERDATE", formattedDate.trim());
         params.put("PNO", "9");

         Log.d("TABLENUM-----", arrlist.get(TablePosition).getTableId().trim());
         Log.d("LOCATIONPREFIX----", outletCode.trim());
         Log.d("REFTABLENO----", tableNo.trim());
         Log.d("KOTNUMBER----",kot.trim());
         Log.d("ORDERDATE----", formattedDate.trim());




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


    public void onSave(View v){
        Log.d("fffffffffff",TableCheckStatus);
        Log.d("KOTNUMBER----",kot.trim());
        if (TableCheckStatus=="YES"){
            table_check();
        }
        else {
            selected_kot();
        }
        Intent i = new Intent(TableTransfer.this, NavigationDrawerActivity.class);
        startActivity(i);
    }
    private void update_table() {


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
                generalOperationTools.showSnackMessage("NO TABLE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
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

    private void   initOperation_Empty(){
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");


        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        tableList = new ArrayList<>();
        tableHolderAdapter_transfer = new TableHolderAdapter_new(getApplicationContext(),tableList);
        recycler_view_table = (RecyclerView) findViewById(R.id.recycler_view_table);
        RecyclerView.LayoutManager mlayoutManager = new GridLayoutManager(TableTransfer.this,4);
        recycler_view_table.setLayoutManager(mlayoutManager);
        recycler_view_table.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(4), true));
        recycler_view_table.setItemAnimator(new DefaultItemAnimator());
        recycler_view_table.setAdapter(tableHolderAdapter_transfer);
        prepareTables();
        System.out.println("INSIDE ON CREATE VIEW");

        recycler_view_table.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view_table, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            //    Toast.makeText(getApplicationContext(), arrlist.get(position).getTableId(), Toast.LENGTH_LONG).show();
                TablePosition = position;
                //  selected_kot();



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    private void initOperation_booked(){
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");


        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        tableList = new ArrayList<>();
        tableHolderAdapter_transfer = new TableHolderAdapter_new(getApplicationContext(),tableList);
        tablde_recycler_view_booked = (RecyclerView) findViewById(R.id.tablde_recycler_view_booked);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(TableTransfer.this,3);
        tablde_recycler_view_booked.setLayoutManager(layoutManager);
        tablde_recycler_view_booked.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(3), true));
        tablde_recycler_view_booked.setItemAnimator(new DefaultItemAnimator());
        System.out.println("INSIDE ON CREATE VIEW");

        prepareTables_booked();

    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        tableList.add(newPosition,tableList.remove(oldPosition));// change position
        tableHolderAdapter_transfer.notifyItemMoved(oldPosition, newPosition);
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

    private void initOperation_getkotTransfer(){
        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();
        itemOrderList1 = new ArrayList<>();
        tableHolderAdapter_kot_add = new TableHolderAdapter_kot_add(getApplicationContext(),itemOrderList1);
        recycler_view1 = (RecyclerView)findViewById(R.id.recycler_view1);
        recycler_view1.setHasFixedSize(true);
       // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(TableTransfer.this,2);
        recycler_view1.setLayoutManager(mLayoutManager);
//        recycler_view1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
//        recycler_view1.setItemAnimator(new DefaultItemAnimator());
//
        //update();
        recycler_view1.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(2), true));
        recycler_view1.setItemAnimator(new DefaultItemAnimator());
        recycler_view1.setAdapter(tableHolderAdapter_kot_add);
        recycler_view1.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler_view1, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), arrlist1.get(position).getKotnumber(), Toast.LENGTH_LONG).show();
                KotPosition1 = position;

                String Utterkot = arrlist1.get(KotPosition1).getKotnumber();
                if (kot==""){
                    kot = kot + Utterkot + ",";
                }
                else{
                    String[] elements = kot.split(",");
                    List<String> fixedLenghtList = Arrays.asList(elements);
                    ArrayList<String> listOfString = new ArrayList<String>(fixedLenghtList);
                    if (listOfString.contains(Utterkot)) {
                        listOfString.remove(Utterkot);
                        kot= TextUtils.join(",", listOfString);
                        kot = kot+",";
                    }
                    else{
                        kot = kot + Utterkot + ",";
                    }
                }
                Log.d("7777777777",kot.trim());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    /**
     * Adding few albums for testing
     */

    private void prepareTables() {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","TABLE_STATUS");
        params.put("ORDERDATE",currentDate.trim());
        Log.d("prepareTables",currentDate);
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
                            initOperation_booked();

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



                    tableHolderAdapter_transfer.notifyDataSetChanged();
                }
                CustomDialogClass.showProgressDialog(context,false);
            //    generalOperationTools.showSnackMessage("TABLE(S) FOUND","OK",coordinatorLayout);
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

    private void prepareTables_booked(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","TABLE_STATUS_BOOKED");
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
                    Log.d("jsonArray----BOOKED", String.valueOf(jsonArray));
                    JSONObject OBJ_TABLE1 = jsonArray.getJSONObject(1);
                    JSONArray  JSONSTATUS = OBJ_TABLE1.getJSONArray("JSONSTATUS");
                    Log.d("TABLE_STATUS---------", String.valueOf(JSONSTATUS));
                    for (int i = 0; i<JSONSTATUS.length();i++){
                        JSONObject JSONSTATUS_OBJ = JSONSTATUS.getJSONObject(i);
                        if (i==0){
                            //   String STATUS = JSONSTATUS_OBJ.getString("STATUS");
                            status = Integer.parseInt(JSONSTATUS_OBJ.getString("STATUS"));
                            String COLNAME = JSONSTATUS_OBJ.getString("COLNAME");
                            Log.d("Status--BOOKED", String.valueOf(status));
                            Log.d("COLNAME-------------",COLNAME);
                            parseValidTableJson_booked(response,status);
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
    private void parseValidTableJson_booked(String response, int status) {

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



                    tableHolderAdapter_transfer.notifyDataSetChanged();
                }
                CustomDialogClass.showProgressDialog(context,false);
           //     generalOperationTools.showSnackMessage("TABLE(S) FOUND","OK",coordinatorLayout);
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
              //  generalOperationTools.showSnackMessage("NO TABLE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
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
                  String  ITEMCD = kot_update.getString("ITEMCD");
                  String ITEMDESCR = kot_update.getString("ITEMDESCR");
                  String  QTY = kot_update.getString("QTY");
                  String  RATE = kot_update.getString("RATE");
                  String  VALUE = kot_update.getString("VALUE");
                  String  COLNAME = kot_update.getString("COLNAME");
                    objcont.setTablenum(tableNo);
                    objcont.setKotnumber(kotNumber);
                    objcont.setItem_kot(ITEMCD);
                    objcont.setItemdescrr(ITEMDESCR);
                    objcont.setQty_kot(QTY);
                    objcont.setRate_kot(RATE);
                    objcont.setValue_kot(VALUE);
                    objcont.setUpdate(COLNAME);
                    itemOrderList1.add(objcont);
                    tableHolderAdapter_kot_add.notifyDataSetChanged();
                    tableList.add(objcont);
                    arrlist1.add(objcont);
                    Log.d("update----------",tableNo+ " "+kotNumber+ " "+ITEMCD+" "+ITEMDESCR+""+QTY+" "+RATE+""+" "+VALUE+" "+COLNAME+" ");
                    Log.d("ITEMCD-----",ITEMCD);
                }


                CustomDialogClass.showProgressDialog(context, false);
            //    generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
             //   generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }

    }



    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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


}
