//package com.ebits.restro.activities.fragments;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.Resources;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.support.design.widget.CoordinatorLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.ebits.restro.R;
//import com.ebits.restro.activities.adapter.TableHolderAdapter;
//import com.ebits.restro.activities.model.TableModel;
//import com.ebits.restro.activities.utils.CustomDialogClass;
//import com.ebits.restro.activities.utils.JsonParsingUtils;
//import com.ebits.restro.activities.utils.constant.Consts;
//import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
//import com.ebits.restro.activities.utils.tools.GeneralOperationTools;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Mr. Satyaki Mukherjee  on 14/07/16.
// */
//public class TableShowFragmentt extends Fragment{
//
//    private RecyclerView recyclerView;
//    private CoordinatorLayout coordinatorLayout;
//    private TableHolderAdapter tableHolderAdapter;
//    private List<TableModel> tableList;
//    private SharedPreferences   sp;
//    private String  outletData, outletCode, currentDate;
//    private String status;
//    private GeneralOperationTools generalOperationTools;
//    private boolean flagLoad;
//    //  String formattedDate;
//    public TableShowFragmentt() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//
//        sp  =   getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
//        outletData  =   sp.getString("OUTLET","");
//        currentDate =   sp.getString("CUR_DATE","");
//        status = sp.getString("STATUS","");
//
//
//        String[] tokenOutlet    =   outletData.split("#");
//        outletCode  =   tokenOutlet[0];
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_table_show, container, false);
//
//        coordinatorLayout = (CoordinatorLayout) rootView.findViewById(R.id.coordinatorLayout);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        flagLoad=false;
//
//        System.out.println();
//
//        initOperation();
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }
//
//    /**
//     * For initialize different operations
//     */
//    private void initOperation() {
//
//        generalOperationTools   =   GeneralOperationTools.generalOperationToolsInstance();
//
//        tableList = new ArrayList<>();
//        tableHolderAdapter = new TableHolderAdapter(getActivity(), tableList);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 10);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(10, dpToPx(10), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(tableHolderAdapter);
//
//        System.out.println("INSIDE ON CREATE VIEW");
//        prepareTables();
//    }
//
//    /**
//     * Adding few albums for testing
//     */
//    private void prepareTables() {
//
//        Map<String,String> params = new HashMap<String, String>();
//        params.put("FLAG","TABLE_STATUS");
//        params.put("ORDERDATE",currentDate.trim());
//        Log.d("table_date",currentDate);
//        params.put("LOCATIONPREFIX",outletCode.trim());
//        params.put("SPNAME", "USP_APP_LOGIN_SV");
//        params.put("PNO", "5");
//
//        final Calendar cal = Calendar.getInstance();
//        System.out.println("INSIDE PREPARE TABLE" +cal.getTime());
//
//
//        CustomDialogClass.showProgressDialog(getActivity(),true);
//        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
//        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
//            @Override
//            public void onServiceError(String error) {
//                CustomDialogClass.showProgressDialog(getActivity(),false);
//                generalOperationTools.showSnackMessage(error.toUpperCase()
//                        ,"OK",coordinatorLayout);
//            }
//
//            @Override
//            public void onServiceResponse(String response) {
//                int status=0;
//                JSONArray jsonArray = null;
//                try {
//                    jsonArray = new JSONArray(response.trim());
//                    Log.d("jsonArray------------", String.valueOf(jsonArray));
//                    JSONObject OBJ_TABLE1 = jsonArray.getJSONObject(1);
//                    JSONArray  JSONSTATUS = OBJ_TABLE1.getJSONArray("JSONSTATUS");
//                    Log.d("TABLE_STATUS---------", String.valueOf(JSONSTATUS));
//                    for (int i = 0; i<JSONSTATUS.length();i++){
//                        JSONObject JSONSTATUS_OBJ = JSONSTATUS.getJSONObject(i);
//                        if (i==0){
//                            //   String STATUS = JSONSTATUS_OBJ.getString("STATUS");
//                            status = Integer.parseInt(JSONSTATUS_OBJ.getString("STATUS"));
//                            String COLNAME = JSONSTATUS_OBJ.getString("COLNAME");
//                            Log.d("status-------", String.valueOf(status));
//                            Log.d("COLNAME-------------",COLNAME);
//                            parseValidTableJson(response,status);
//                            System.out.println("INSIDE PREPARE TABLE" +cal.getTime());
//                        }
//                    }
//                } catch (JSONException e) {
//                    CustomDialogClass.showProgressDialog(getActivity(),false);
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//
//    }
//
//
//    private void parseValidTableJson(String response, int status) {
//
//        JSONArray jsonArray = null;/*
//        int[] covers = new int[]{
//                R.drawable.circle_red,
//                R.drawable.circle_green};*/
//        try {
//            jsonArray = new JSONArray(response.trim());
//
//            if(status==1){
//                JSONObject OBJ_TABLE = jsonArray.getJSONObject(0);
//                JSONArray TABLE_STATUS = OBJ_TABLE.getJSONArray("TABLE_STATUS");
//                Log.d("TABLE_STATUS", String.valueOf(TABLE_STATUS));
//                for (int i = 0; i < TABLE_STATUS.length(); i++) {
//                    JSONObject tableStatusJsonObj = TABLE_STATUS.getJSONObject(i);
//                    Log.d("TABLE_OBJ", String.valueOf(tableStatusJsonObj));
//                    TableModel tm = new TableModel(tableStatusJsonObj.getString("STATUS"), tableStatusJsonObj.getString("TYPE"), tableStatusJsonObj.getString("TABLE_ID"));
//                    tableList.add(tm);
//
//
//
//                    tableHolderAdapter.notifyDataSetChanged();
//                }
//                CustomDialogClass.showProgressDialog(getActivity(),false);
//                //   generalOperationTools.showSnackMessage("TABLE(S) FOUND","OK",coordinatorLayout);
//            }
//            else{
//                CustomDialogClass.showProgressDialog(getActivity(),false);
//                //    generalOperationTools.showSnackMessage("NO TABLE FOUND","OK",coordinatorLayout);
//            }
//        } catch (JSONException e) {
//            CustomDialogClass.showProgressDialog(getActivity(),false);
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * RecyclerView item decoration - give equal margin around grid item
//     */
//
//
//
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }
//
//    /**
//     * Converting dp to pixel
//     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        if(flagLoad){
//            flagLoad=false;
//            doReload();
//        }
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        flagLoad=true;
//    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_main, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        if(id == R.id.action_reload){
//            doReload();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void doReload() {
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.detach(this).attach(this).commit();
//    }
//}
