package com.ebits.restro.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.GroupHolderAdapter;
import com.ebits.restro.activities.adapter.SubGroupHolderAdapter;
import com.ebits.restro.activities.adapter.SubTypeHolderAdapter;
import com.ebits.restro.activities.adapter.TypeHolderAdapter;
import com.ebits.restro.activities.model.GroupModel;
import com.ebits.restro.activities.model.SubGroupModel;
import com.ebits.restro.activities.model.SubTypeModel;
import com.ebits.restro.activities.model.TypeModel;
import com.ebits.restro.activities.utils.CustomDialogClass;
import com.ebits.restro.activities.utils.JsonParsingUtils;
import com.ebits.restro.activities.utils.interfaces.CleanAdapterInterface;
import com.ebits.restro.activities.utils.interfaces.ClickListener;
import com.ebits.restro.activities.utils.interfaces.OnFragmentInteractionListener;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
import com.ebits.restro.activities.utils.tools.GeneralOperationTools;
import com.ebits.restro.activities.utils.tools.GridSpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSubGroupTypeSubTypeActivity extends AppCompatActivity {

    /***
     * Widgets will be added here
     */
    private Spinner gsts_chooseExpandMenu;
    private RelativeLayout gsts_groupLayout,gsts_subGroupLayout,gsts_typeLayout,gsts_subTypeLayout;
    private TextView gsts_headerGroup,gsts_headerSubGroup,gsts_headerType,gsts_headerSubType;
    private ImageButton gests_groupArrow,gests_subGroupArrow,gests_typeArrow,gests_subTypeArrow;
    private RecyclerView gsts_groupRecyclerView,gsts_subGroupRecyclerView,gsts_typeRecyclerView,gsts_subTypeRecyclerView;
    private CoordinatorLayout coordinatorLayout;
    private GroupHolderAdapter groupHolderAdapter;
    private List<GroupModel> groupList;
    private SubGroupHolderAdapter subGroupHolderAdapter;
    private List<SubGroupModel> subGroupList;
    private TypeHolderAdapter typeHolderAdapter;
    private List<TypeModel> typeList;
    private SubTypeHolderAdapter subTypeHolderAdapter;
    private List<SubTypeModel> subTypeList;
    private GeneralOperationTools generalOperationTools;

    private boolean flagGroupArrow=true,flagSubGroupArrow=true,flagTypeArrow=true,flagSubTypeArrow=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_subgroup_type_subtype);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        gsts_chooseExpandMenu=   (Spinner) findViewById(R.id.gsts_chooseExpandMenu);
        gsts_groupLayout    =   (RelativeLayout)    findViewById(R.id.gsts_groupLayout);
        gsts_subGroupLayout =   (RelativeLayout)    findViewById(R.id.gsts_subGroupLayout);
        gsts_typeLayout =   (RelativeLayout)    findViewById(R.id.gsts_typeLayout);
        gsts_subTypeLayout  =   (RelativeLayout)    findViewById(R.id.gsts_subTypeLayout);

        gsts_headerGroup    =   (TextView)    findViewById(R.id.gsts_headerGroup);
        gsts_headerSubGroup =   (TextView)    findViewById(R.id.gsts_headerSubGroup);
        gsts_headerType =   (TextView)    findViewById(R.id.gsts_headerType);
        gsts_headerSubType  =   (TextView)    findViewById(R.id.gsts_headerSubType);

        gests_groupArrow    =   (ImageButton)    findViewById(R.id.gsts_groupArrow);
        gests_subGroupArrow =   (ImageButton)    findViewById(R.id.gsts_subGroupArrow);
        gests_typeArrow =   (ImageButton)    findViewById(R.id.gsts_typeArrow);
        gests_subTypeArrow  =   (ImageButton)    findViewById(R.id.gsts_subTypeArrow);

        gsts_groupRecyclerView  =   (RecyclerView)    findViewById(R.id.gsts_groupRecyclerView);
        gsts_subGroupRecyclerView   =   (RecyclerView)    findViewById(R.id.gsts_subGroupRecyclerView);
        gsts_typeRecyclerView   =   (RecyclerView)    findViewById(R.id.gsts_typeRecyclerView);
        gsts_subTypeRecyclerView    =   (RecyclerView)    findViewById(R.id.gsts_subTypeRecyclerView);

        generalOperationTools   =   GeneralOperationTools.generalOperationToolsInstance();


        /***
         * By default start with subgroup
         */
        gsts_chooseExpandMenu.setSelection(4);
        initRecyclerOperations();
        initSpinnerOperation();
    }

    /***
     * Declare all recycler items here
     ***/
    private void initRecyclerOperations() {

        ///for group
        groupList = new ArrayList<>();
        groupHolderAdapter = new GroupHolderAdapter(this, groupList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 5);
        gsts_groupRecyclerView.setLayoutManager(mLayoutManager);
        gsts_groupRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, GridSpacingItemDecoration.dpToPx(5, this), true));
        gsts_groupRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gsts_groupRecyclerView.setAdapter(groupHolderAdapter);

        gsts_groupRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, gsts_groupRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {       }

            @Override
            public void onLongClick(View view, int position) {
                clearListAdapter(false,true,true,true);
                GroupModel model = groupList.get(position);
                populateSubGroups(model.getGroupId(),false);
            }
        }));

        ///for sub group
        subGroupList = new ArrayList<>();
        subGroupHolderAdapter = new SubGroupHolderAdapter(this, subGroupList);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(this, 5);

        gsts_subGroupRecyclerView.setLayoutManager(mLayoutManager1);
        gsts_subGroupRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, GridSpacingItemDecoration.dpToPx(5, this), true));
        gsts_subGroupRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gsts_subGroupRecyclerView.setAdapter(subGroupHolderAdapter);

        gsts_subGroupRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, gsts_subGroupRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {       }

            @Override
            public void onLongClick(View view, int position) {
                clearListAdapter(false,false,true,true);
                SubGroupModel model = subGroupList.get(position);
                populateTypes(model.getSubGroupId(),false);
            }
        }));

        ///for type
        typeList = new ArrayList<>();
        typeHolderAdapter = new TypeHolderAdapter(this, typeList);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(this, 5);

        gsts_typeRecyclerView.setLayoutManager(mLayoutManager2);
        gsts_typeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, GridSpacingItemDecoration.dpToPx(5, this), true));
        gsts_typeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gsts_typeRecyclerView.setAdapter(typeHolderAdapter);

        gsts_typeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, gsts_typeRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {       }

            @Override
            public void onLongClick(View view, int position) {
                clearListAdapter(false,false,false,true);
                TypeModel model = typeList.get(position);
                populateSubTypes(model.getTypepId(),false);
            }
        }));

        ///for sub type
        subTypeList = new ArrayList<>();
        subTypeHolderAdapter = new SubTypeHolderAdapter(this, subTypeList);
        RecyclerView.LayoutManager mLayoutManager3 = new GridLayoutManager(this, 5);

        gsts_subTypeRecyclerView.setLayoutManager(mLayoutManager3);
        gsts_subTypeRecyclerView.addItemDecoration(new GridSpacingItemDecoration(5, GridSpacingItemDecoration.dpToPx(5, this), true));
        gsts_subTypeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        gsts_subTypeRecyclerView.setAdapter(subTypeHolderAdapter);

        gsts_subTypeRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, gsts_subTypeRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {       }

            @Override
            public void onLongClick(View view, int position) {

                SubTypeModel model = subTypeList.get(position);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("SUBTYPE_ID",model.getSubTypepId());
                setResult(RESULT_OK,returnIntent);
                finish();
            }
        }));
    }


    /***
     * On select on spinner
     */
    private void initSpinnerOperation() {

       /* if(gsts_chooseExpandMenu.getSelectedItemPosition()==4){
            gsts_groupLayout.setVisibility(View.GONE);
            gsts_subGroupLayout.setVisibility(View.GONE);
            gsts_typeLayout.setVisibility(View.GONE);
            gsts_subTypeLayout.setVisibility(View.VISIBLE);
            clearListAdapter(true,true,true,true);
            populateSubTypes("",true);
        }*/

        gsts_chooseExpandMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                if(position==0){
                    gsts_groupLayout.setVisibility(View.GONE);
                    gsts_subGroupLayout.setVisibility(View.GONE);
                    gsts_typeLayout.setVisibility(View.GONE);
                    gsts_subTypeLayout.setVisibility(View.GONE);
                    clearListAdapter(true,true,true,true);
                }
                else if (position==1){
                    gsts_groupLayout.setVisibility(View.VISIBLE);
                    gsts_subGroupLayout.setVisibility(View.GONE);
                    gsts_typeLayout.setVisibility(View.GONE);
                    gsts_subTypeLayout.setVisibility(View.GONE);
                    clearListAdapter(true,true,true,true);
                    populateGroups("",true);
                }
                else if (position==2){
                    gsts_groupLayout.setVisibility(View.GONE);
                    gsts_subGroupLayout.setVisibility(View.VISIBLE);
                    gsts_typeLayout.setVisibility(View.GONE);
                    gsts_subTypeLayout.setVisibility(View.GONE);
                    clearListAdapter(true,true,true,true);
                    populateSubGroups("",true);
                }
                else if (position==3){
                    gsts_groupLayout.setVisibility(View.GONE);
                    gsts_subGroupLayout.setVisibility(View.GONE);
                    gsts_typeLayout.setVisibility(View.VISIBLE);
                    gsts_subTypeLayout.setVisibility(View.GONE);
                    clearListAdapter(true,true,true,true);
                    populateTypes("",true);
                }
                else if (position==4){
                    gsts_groupLayout.setVisibility(View.GONE);
                    gsts_subGroupLayout.setVisibility(View.GONE);
                    gsts_typeLayout.setVisibility(View.GONE);
                    gsts_subTypeLayout.setVisibility(View.VISIBLE);
                    clearListAdapter(true,true,true,true);
                    populateSubTypes("",true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void populateGroups(String id, boolean spinnerFlag) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETITEMGROUP_S");
        if(spinnerFlag)
            params.put("DESCR",id.trim());
        else
            params.put("DESCR","");
        params.put("SPNAME", "SP_POS_KOTBILLING");
        params.put("PNO", "4");

        System.out.println("INSIDE GROUP ");
        CustomDialogClass.showProgressDialog(this,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));

                            parseGroupJson(response,status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateSubGroups(String groupCode, boolean spinnerFlag) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETITEMSUBGROUP_BYGROUP_S");
        if(!spinnerFlag)
            params.put("ITEMGROUP",groupCode.trim());
        else
            params.put("ITEMGROUP","");
        params.put("DESCR","");
        params.put("SPNAME", "SP_POS_KOTBILLING");
        params.put("PNO", "5");

        System.out.println("INSIDE Sub GROUP ");
        CustomDialogClass.showProgressDialog(this,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));

                            parseSubGroupJson(response,status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateTypes(String subGroupCode, boolean spinnerFlag) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETITEMTYPE_BYSUBGROUP_S");
        if(!spinnerFlag)
            params.put("ITEMSUBGROUP",subGroupCode.trim());
        else
            params.put("ITEMSUBGROUP","");
        params.put("DESCR","");
        params.put("SPNAME", "SP_POS_KOTBILLING");
        params.put("PNO", "5");

        System.out.println("INSIDE Type ");
        CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));

                            parseTypeJson(response,status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void populateSubTypes(String typeCode, boolean spinnerFlag) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETITEMSUBTYPE_BYTYPE_S");
        if(!spinnerFlag)
            params.put("ITEMTYPE",typeCode.trim());
        else
            params.put("ITEMTYPE","");
        params.put("DESCR","");
        params.put("SPNAME", "SP_POS_KOTBILLING");
        params.put("PNO", "5");

        System.out.println("INSIDE Type ");
        CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));

                            parseSubTypeJson(response,status);
                        }
                    }

                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                    e.printStackTrace();
                }
            }
        });
    }



    //////Not understand


    private void parseGroupJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);

                    /**For table data adapter***/
                    if (i == 1) {
                        JSONArray parentJsonArray = explrObject.getJSONArray("GROUP");
                        for (int j = 0; j < parentJsonArray.length(); j++) {
                            JSONObject jsonObj = parentJsonArray.getJSONObject(j);

                            GroupModel  gm  =   new GroupModel(jsonObj.getString("GROUPNAME").trim(),jsonObj.getString("GROUPCODE").trim(),jsonObj.getString("IMG").trim());
                            groupList.add(gm);
                        }
                        groupHolderAdapter.notifyDataSetChanged();
                    }
                }

                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("GROUP(S) FOUND","OK",coordinatorLayout);
            }
            else{
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("NO GROUP FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
            e.printStackTrace();
        }
    }

    private void parseSubGroupJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);

                    /**For table data adapter***/
                    if (i == 1) {
                        JSONArray parentJsonArray = explrObject.getJSONArray("SUBGROUP");
                        for (int j = 0; j < parentJsonArray.length(); j++) {
                            JSONObject jsonObj = parentJsonArray.getJSONObject(j);

                            SubGroupModel  sgm  =   new SubGroupModel(jsonObj.getString("DESCR").trim(),jsonObj.getString("SHORTCODE").trim(),jsonObj.getString("IMG").trim());
                            subGroupList.add(sgm);
                        }
                        subGroupHolderAdapter.notifyDataSetChanged();
                    }
                }
                gsts_subGroupLayout.setVisibility(View.VISIBLE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("SUB-GROUP(S) FOUND","OK",coordinatorLayout);
            }
            else{
                gsts_subGroupLayout.setVisibility(View.GONE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("NO SUB-GROUP FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
            e.printStackTrace();
        }
    }

    private void parseTypeJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);

                    /**For table data adapter***/
                    if (i == 1) {
                        JSONArray parentJsonArray = explrObject.getJSONArray("TYPE");
                        for (int j = 0; j < parentJsonArray.length(); j++) {
                            JSONObject jsonObj = parentJsonArray.getJSONObject(j);

                            TypeModel  tm  =   new TypeModel(jsonObj.getString("ITEMTYPENAME").trim(),jsonObj.getString("ITEMTYPECODE").trim(),jsonObj.getString("IMG").trim());
                            typeList.add(tm);
                        }
                        typeHolderAdapter.notifyDataSetChanged();
                    }
                }

                gsts_typeLayout.setVisibility(View.VISIBLE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("TYPE(S) FOUND","OK",coordinatorLayout);
            }
            else{
                gsts_typeLayout.setVisibility(View.GONE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("NO TYPE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
            e.printStackTrace();
        }
    }

    private void parseSubTypeJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);

                    /**For table data adapter***/
                    if (i == 1) {
                        JSONArray parentJsonArray = explrObject.getJSONArray("SUBTYPE");
                        for (int j = 0; j < parentJsonArray.length(); j++) {
                            JSONObject jsonObj = parentJsonArray.getJSONObject(j);

                            SubTypeModel  stm  =   new SubTypeModel(jsonObj.getString("ITEMSUBTYPENAME").trim(),jsonObj.getString("ITEMSUBTYPECODE").trim(),jsonObj.getString("IMG").trim());
                            subTypeList.add(stm);
                        }
                        subTypeHolderAdapter.notifyDataSetChanged();
                    }
                }
                gsts_subTypeLayout.setVisibility(View.VISIBLE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("SUB-TYPE(S) FOUND","OK",coordinatorLayout);
            }
            else{
                gsts_subTypeLayout.setVisibility(View.GONE);
                CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
                generalOperationTools.showSnackMessage("NO SUB-TYPE FOUND","OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this,false);
            e.printStackTrace();
        }
    }

//////Not understand


    /***
     * On recycler touch listener
     */
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

    /***
     * All arrow button sections work here
     */

    public void onGroupArrow(View view){
        if(flagGroupArrow){
            flagGroupArrow=false;
            gsts_groupRecyclerView.setVisibility(View.GONE);
            gests_groupArrow.setImageResource(R.drawable.up_arrow);
        }
        else{
            flagGroupArrow=true;
            gsts_groupRecyclerView.setVisibility(View.VISIBLE);
            gests_groupArrow.setImageResource(R.drawable.down_arrow);
        }
    }

    public void onSubGroupArrow(View view){
        if(flagSubGroupArrow){
            flagSubGroupArrow=false;
            gsts_subGroupRecyclerView.setVisibility(View.GONE);
            gests_subGroupArrow.setImageResource(R.drawable.up_arrow);
        }
        else{
            flagSubGroupArrow=true;
            gsts_subGroupRecyclerView.setVisibility(View.VISIBLE);
            gests_subGroupArrow.setImageResource(R.drawable.down_arrow);
        }
    }

    public void onTypeArrow(View view){
        if(flagTypeArrow){
            flagTypeArrow=false;
            gsts_typeRecyclerView.setVisibility(View.GONE);
            gests_typeArrow.setImageResource(R.drawable.up_arrow);
        }
        else{
            flagTypeArrow=true;
            gsts_typeRecyclerView.setVisibility(View.VISIBLE);
            gests_typeArrow.setImageResource(R.drawable.down_arrow);
        }
    }

    public void onSubTypeArrow(View view){
        if(flagSubTypeArrow){
            flagSubTypeArrow=false;
            gsts_subTypeRecyclerView.setVisibility(View.GONE);
            gests_subTypeArrow.setImageResource(R.drawable.up_arrow);
        }
        else{
            flagSubTypeArrow=true;
            gsts_subTypeRecyclerView.setVisibility(View.VISIBLE);
            gests_subTypeArrow.setImageResource(R.drawable.down_arrow);
        }
    }


    /////Not Understand

    /***
     * clear adapter class
     */
    private void clearListAdapter(boolean group, boolean subGroup, boolean type, boolean subType) {

        if(group==true && subGroup==true && type==true && subType==true){

            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, true);
            groupHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                @Override
                public void cleanStatus(boolean cleanStatus) {
                    subGroupHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                        @Override
                        public void cleanStatus(boolean cleanStatus) {
                            typeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                                @Override
                                public void cleanStatus(boolean cleanStatus) {
                                    subTypeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                                        @Override
                                        public void cleanStatus(boolean cleanStatus) {
                                            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, false);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            });
        }
        else if(group==false && subGroup==true && type==true && subType==true){

            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, true);
            subGroupHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                @Override
                public void cleanStatus(boolean cleanStatus) {
                    typeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                        @Override
                        public void cleanStatus(boolean cleanStatus) {
                            subTypeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                                @Override
                                public void cleanStatus(boolean cleanStatus) {
                                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, false);
                                }
                            });
                        }
                    });
                }
            });
        }
        else if (group==false && subGroup==false && type==true && subType==true){

            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, true);
            typeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                @Override
                public void cleanStatus(boolean cleanStatus) {
                    subTypeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                        @Override
                        public void cleanStatus(boolean cleanStatus) {
                            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, false);
                        }
                    });
                }
            });
        }
        else if (group==false && subGroup==false && type==false && subType==true){
            CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, true);
            subTypeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                @Override
                public void cleanStatus(boolean cleanStatus) {
                    CustomDialogClass.showProgressDialog(GroupSubGroupTypeSubTypeActivity.this, false);
                }
            });
        }
        else {
            /*if (group) {
                CustomDialogClass.showProgressDialog(getActivity(), true);
                groupHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                    @Override
                    public void cleanStatus(boolean cleanStatus) {
                        CustomDialogClass.showProgressDialog(getActivity(), false);
                    }
                });
            }
            if (subGroup) {
                CustomDialogClass.showProgressDialog(getActivity(), true);
                subGroupHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                    @Override
                    public void cleanStatus(boolean cleanStatus) {
                        CustomDialogClass.showProgressDialog(getActivity(), false);
                    }
                });
            }
            if (type) {
                CustomDialogClass.showProgressDialog(getActivity(), true);
                typeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                    @Override
                    public void cleanStatus(boolean cleanStatus) {
                        CustomDialogClass.showProgressDialog(getActivity(), false);
                    }
                });
            }
            if (subType) {
                CustomDialogClass.showProgressDialog(getActivity(), true);
                subTypeHolderAdapter.clearRecyclerView(new CleanAdapterInterface() {
                    @Override
                    public void cleanStatus(boolean cleanStatus) {
                        CustomDialogClass.showProgressDialog(getActivity(), false);
                    }
                });
            }*/
        }
    }
}
