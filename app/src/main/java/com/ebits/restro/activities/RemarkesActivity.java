package com.ebits.restro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.ebits.restro.R;
import com.ebits.restro.activities.model.TableModel;
import com.ebits.restro.activities.utils.CustomDialogClass;
import com.ebits.restro.activities.utils.JsonParsingUtils;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
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
 * Created by Admin on 11/12/2017.
 */

public class RemarkesActivity extends AppCompatActivity  implements CallbackItemTouch{
    RecyclerView remarks_recycler_view;
    private List<Remarks_set_Get> remarks_set_gets;
    private RemarksTypeHolderAdapter remarksTypeHolderAdapter;
    private GeneralOperationTools generalOperationTools;
    private Context context = this;
    private CoordinatorLayout coordinatorLayout;
    private String tableNo="",kotNumber="",formattedDate="";
    private SharedPreferences sp;
    private String  outletData, outletCode, currentDate;
    private String status;
    ArrayList<Remarks_set_Get> arrlist = new ArrayList<Remarks_set_Get>();
    private List<TableModel> tableList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remarks_dialog);
//        Bundle bundle = getIntent().getExtras();
//        tableNo = bundle.getString("TABLE_NO");
      //  Log.d("table_no",tableNo);
        remarks_recycler_view = (RecyclerView)findViewById(R.id.remarks_recycler_view);
        intrecyclerview();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        sp  =  getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
        outletData  =   sp.getString("OUTLET","");
        currentDate =   sp.getString("CUR_DATE","");
        status = sp.getString("STATUS","");
        String[] tokenOutlet    =   outletData.split("#");
        outletCode  =   tokenOutlet[0];
        generalOperationTools   =   GeneralOperationTools.generalOperationToolsInstance();

        Intent intent = getIntent();
        tableNo = intent.getStringExtra("Table");
     //   kotNumber = intent.getStringExtra("Kot");
        System.out.println("tableNo_Kot"+tableNo+" ");

        //prepareTables();

    }
    private void intrecyclerview(){

        remarks_set_gets = new ArrayList<>();
        remarksTypeHolderAdapter = new RemarksTypeHolderAdapter(this, remarks_set_gets);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 5);
        remarks_recycler_view.setLayoutManager(mLayoutManager);
        remarks_recycler_view.addItemDecoration(new GridSpacingItemDecoration(5, GridSpacingItemDecoration.dpToPx(5, this), true));
        remarks_recycler_view.setItemAnimator(new DefaultItemAnimator());
        remarks_recycler_view.setAdapter(remarksTypeHolderAdapter);
        System.out.println("INSIDE ON CREATE VIEW");
         Remarks();

        remarks_recycler_view.addOnItemTouchListener(new TableTransfer.RecyclerTouchListener(getApplicationContext(), remarks_recycler_view, new TableTransfer.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Toast.makeText(getApplicationContext(), arrlist.get(position).getName(), Toast.LENGTH_LONG).show();
//                TablePosition = position;
                //  selected_kot();
                Intent i = getIntent();
                tableNo = i.getStringExtra("Table");
                Intent intent = new Intent(RemarkesActivity.this, OrderTakingActivityNew.class);
                intent.putExtra("Values", arrlist.get(position).getName());
                intent.putExtra("tableNo",tableNo);
                Log.d("Values", arrlist.get(position).getName());
                Log.d("retable",tableNo);
                startActivity(intent);



            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private TableTransfer.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final TableTransfer.ClickListener clickListener) {
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
    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        remarks_set_gets.add(newPosition,remarks_set_gets.remove(oldPosition));// change position
        remarksTypeHolderAdapter.notifyItemMoved(oldPosition, newPosition);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
    private void Remarks() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("FLAG", "GET_REMARKS");
        params.put("SPNAME", "KOT_HDR_DTL_INSERT_APP_SV");
        params.put("PNO", "3");



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
                    CustomDialogClass.showProgressDialog(context,false);
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

                    String  NAME = remarksJSONObject.getString("DESCRIPTION");
                    String  REMARKS = remarksJSONObject.getString("REMARKS");
                    String  COLNAME = remarksJSONObject.getString("COLNAME");
                    objcont.setName(NAME);
                    objcont.setDescripition(REMARKS);
                    objcont.setRemarks(COLNAME);
                    remarks_set_gets.add(objcont);
                    remarksTypeHolderAdapter.notifyDataSetChanged();
                    arrlist.add(objcont);

                    Log.d("Rems----------",NAME+ " "+REMARKS+ " "+COLNAME+" ");





                }


                CustomDialogClass.showProgressDialog(context, false);
//                generalOperationTools.showSnackMessage("ITEM(S) FOUND", "OK", coordinatorLayout);
            } else {
                CustomDialogClass.showProgressDialog(context, false);
                generalOperationTools.showSnackMessage("NO ITEM FOUND", "OK", coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context, false);
            e.printStackTrace();
        }

    }

    private void prepareTables() {

        Map<String,String> params = new HashMap<String, String>();
        //   params.put("FLAG","TABLE_STATUS_EMPTY");
        params.put("FLAG","TABLE_STATUS");
        params.put("ORDERDATE",currentDate.trim());
        Log.d("prepareTables",currentDate);
        // params.put("ORDERDATE","2017/10/24");
        //params.put("LOCATIONPREFIX","PT");
        // params.put("LOCATIONPREFIX", "PLATTER - Dine In");
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

//                    for (int i = 0; i < jsonArray.length(); i++) {
//                       JSONObject explrObject = jsonArray.getJSONObject(i);
//                   //    JSONObject jObj3 = jsonArray.getJSONObject(3);
//
//                        if(i==0) {
//                            status = Integer.parseInt(explrObject.getString("STATUS"));
//                            Log.d("status----------", String.valueOf(status));
//                            parseValidTableJson(response,status);
//                       }
//                    }

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

//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject explrObject = jsonArray.getJSONObject(i);
//
//                    /**For table data adapter***/
//                    if (i == 1) {
//                        JSONArray tableStatusJsonArray = explrObject.getJSONArray("TABLESTATUS");
//                        for (int j = 0; j < tableStatusJsonArray.length(); j++) {
//                            JSONObject tableStatusJsonObj = tableStatusJsonArray.getJSONObject(j);
//
//                            TableModel tm = new TableModel(tableStatusJsonObj.getString("STATUS"), tableStatusJsonObj.getString("TYPE"), tableStatusJsonObj.getString("TABLE_ID"));
//                            tableList.add(tm);
//                        }
//                        tableHolderAdapter.notifyDataSetChanged();
//                    }
//                }

                JSONObject OBJ_TABLE = jsonArray.getJSONObject(0);
                JSONArray TABLE_STATUS = OBJ_TABLE.getJSONArray("TABLE_STATUS");
                Log.d("TABLE_STATUS", String.valueOf(TABLE_STATUS));
                for (int i = 0; i < TABLE_STATUS.length(); i++) {
                    JSONObject tableStatusJsonObj = TABLE_STATUS.getJSONObject(i);
                    Log.d("TABLE_OBJ", String.valueOf(tableStatusJsonObj));
//                    String TABLE_ID = TABLE_OBJ.getString("TABLE_ID");
//                    String TYPE    = TABLE_OBJ.getString("TYPE");
//                    String STATUS = TABLE_OBJ.getString("STATUS");
//                    String COLNAME = TABLE_OBJ.getString("COLNAME");
                    TableModel tm = new TableModel(tableStatusJsonObj.getString("STATUS"), tableStatusJsonObj.getString("TYPE"), tableStatusJsonObj.getString("TABLE_ID"));
                     tableNo = tableStatusJsonObj.getString("TABLE_ID");
                    tableList.add(tm);





                }
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage("TABLE(S) FOUND","OK",coordinatorLayout);
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
}
