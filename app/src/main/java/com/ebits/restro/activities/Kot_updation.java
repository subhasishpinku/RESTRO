package com.ebits.restro.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.ItemsHolderAdapter;
import com.ebits.restro.activities.adapter.ItemsOrderHolderAdapter;
import com.ebits.restro.activities.application.RestroApplication;
import com.ebits.restro.activities.model.ItemEditOrderModel;
import com.ebits.restro.activities.model.ItemModel;
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
 * Created by Admin on 17/10/2017.
 */

public class Kot_updation extends AppCompatActivity implements SearchView.OnQueryTextListener {
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
    private ItemsOrderHolderAdapter itemOrderHolderAdapter;
    private List<ItemEditOrderModel> itemOrderList;
    private Context context = this;
    private String tableNo="",kotNumber="",formattedDate="";
    private String  outletData, outletCode, currentDate;
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
    ArrayList<update_set_get> arrlist = new ArrayList<update_set_get>();
    List<update_set_get> listitem=null;
    ListAdapter adapte;
    private final String TAG = "Kot_updation";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private EditText remarks;
    String ITEMCD;
    String ITEMDESCR;
    String QTY;
    String VALUE;
    String COLNAME;
    String RATE;
    String ITEMIMAGE;
    String DESCR;
    String CODE;
    ListView lv;
    List<HashMap<String,String>> adp;
    String[] tablelist = new String[] {
            tableNo,
            kotNumber,
            ITEMCD,
            ITEMDESCR,
            QTY,
            RATE,
            VALUE,
            COLNAME

    };
    String temp[] = {tableNo,
            kotNumber,
            ITEMCD,
            ITEMDESCR,
            QTY,
            RATE,
            VALUE,
            COLNAME};

    int arr1[]={R.drawable.bb1,R.drawable.bb2,R.drawable.bb4,R.drawable.bb5,R.drawable.bb6,R.drawable.bb1,R.drawable.bb2,R.drawable.bb4,R.drawable.bb5,R.drawable.bb6,R.drawable.bb1,R.drawable.bb2,R.drawable.bb4,R.drawable.bb5,R.drawable.bb6,R.drawable.bb1,R.drawable.bb2,R.drawable.bb4,R.drawable.bb5,R.drawable.bb6};
    EditText search_kot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kot_updation);
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

        clearSearchText = (Button)findViewById(R.id.clearSearchText);
        searchButton = (ImageButton) findViewById(R.id.searchButton);
        expandButton = (ImageButton) findViewById(R.id.expandButton);
        recycler_view_search = (RecyclerView) findViewById(R.id.recycler_view_search);
        saveBtn = (ImageButton) findViewById(R.id.foodOrder_saveBtn);
        cancelBtn = (ImageButton) findViewById(R.id.foodOrder_cancelBtn);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());



        My_async asyn = new My_async();
        asyn.execute();



        lv = (ListView) findViewById(R.id.lv);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Kot_updation.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");



        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        Log.d("outletCode---------",outletCode);

        lv.setAdapter(new My_adap(temp));
        lv.setTextFilterEnabled(true);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        return true;
    }


    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
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


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
//        arrlist.get(Integer.parseInt(query)).getKotnumber();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            lv.clearTextFilter();
        } else {
            lv.setFilterText(newText.toString());
        }
        return true;
    }


    public class My_async extends AsyncTask<String, Integer, String> {

        //  ProgressDialog pd=new ProgressDialog(Kot_updation.this);
        @Override
        protected String doInBackground(String... paramss) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("FLAG", "UPDATE_KOT");
            params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
            params.put("TABLENUM", tableNo);
            params.put("PNO", "6");
            params.put("FROMDATE", formattedDate);
            params.put("TODATE", formattedDate);

//            CustomDialogClass.showProgressDialog(context, true);
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
//                arrlist = new ArrayList<update_set_get>();


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
                                update(response, status);
                            }
                        }
                    } catch (JSONException e) {
                        CustomDialogClass.showProgressDialog(context, false);
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //Toast.makeText(getBaseContext(), ""+arrlist.size(),Toast.LENGTH_SHORT).show();
            lv.setAdapter(new My_adap(temp));

        }


    }

    public class My_adap extends BaseAdapter {


        public My_adap(String[] tablelist) {
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrlist.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            update_set_get obj = new update_set_get();
            obj = arrlist.get(position);
            LayoutInflater inf = getLayoutInflater();
            View rowview = convertView;
            rowview = inf.inflate(R.layout.update_kot_all, parent, false);
            TextView tv_tablenum = (TextView) rowview.findViewById(R.id.tablenum);
            TextView tv_kotnumber = (TextView) rowview.findViewById(R.id.kotnumber);
            TextView tv_qty = (TextView) rowview.findViewById(R.id.qty);
            TextView tv_rate = (TextView) rowview.findViewById(R.id.rate);
            TextView tv_itemdescr = (TextView) rowview.findViewById(R.id.itemdescr);
            tv_tablenum.setText(obj.getTablenum());
            tv_kotnumber.setText(obj.getKotnumber());
            tv_qty.setText(obj.getQty_kot());
            tv_rate.setText(obj.getRate_kot());
            tv_itemdescr.setText(obj.getItemdescrr());
            ImageView imageView = (ImageView) rowview.findViewById(R.id.iv);
            imageView.setImageResource(arr1[position]);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Toast.makeText(getApplicationContext(), arrlist.get(position).getKotnumber(), Toast.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), arrlist.get(position).getTablenum(), Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(Kot_updation.this, EditOrderTakingActivity.class);
                    ii.putExtra("postition", arrlist.get(position).getKotnumber());
                    ii.putExtra("postition1", arrlist.get(position).getTablenum());
                    //  ii.putExtra("postition2",arrlist.get(position).getItem_kot());
                    ii.putExtra("postition3", arrlist.get(position).getItemdescrr());
                    ii.putExtra("postition4", arrlist.get(position).getQty_kot());
                    ii.putExtra("postition5", arrlist.get(position).getRate_kot());
                    //   ii.putExtra("postition6",arrlist.get(position).getValue_kot());
                    //  ii.putExtra("postition7",arrlist.get(position).getUpdate());
                    startActivityForResult(ii, 5);


                    finish();
                }
            });

           return rowview;

        }

    }



    private void update(String response, int status) {
        JSONArray jsonArray = null;
        arrlist=new ArrayList<update_set_get>();
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
                    COLNAME = kot_update.getString("COLNAME");
                    objcont.setTablenum(tableNo);
                    objcont.setKotnumber(kotNumber);
                    objcont.setItem_kot(ITEMCD);
                    objcont.setItemdescrr(ITEMDESCR);
                    objcont.setQty_kot(QTY);
                    objcont.setRate_kot(RATE);
                    objcont.setValue_kot(VALUE);
                    objcont.setUpdate(COLNAME);
                        if (arrlist.add(objcont)){

                    }else {
                        arrlist.clear();
                    }
                  //  arrlist.add(objcont);
                    Log.d("update----------",tableNo+ " "+kotNumber+ " "+ITEMCD+" "+ITEMDESCR+""+QTY+" "+RATE+""+" "+VALUE+" "+COLNAME+" ");
                    Log.d("ITEMCD-----",ITEMCD);
                }


                CustomDialogClass.showProgressDialog(context, false);
              //  generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
             //   generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }

    }


}
