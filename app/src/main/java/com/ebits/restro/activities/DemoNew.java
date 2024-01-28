package com.ebits.restro.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ebits.restro.R;
import com.ebits.restro.activities.adapter.Set_Get;
import com.ebits.restro.activities.utils.CustomDialogClass;
import com.ebits.restro.activities.utils.JsonParsingUtils;
import com.ebits.restro.activities.utils.NetworkUtil;
import com.ebits.restro.activities.utils.constant.Consts;
import com.ebits.restro.activities.utils.interfaces.ServiceCallBack;
import com.ebits.restro.activities.utils.tools.GeneralOperationTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.design.widget.Snackbar.make;


/**
 * Created by Admin on 9/20/2017.
 */

public class DemoNew extends AppCompatActivity {
    private EditText    userName, password;
    private Spinner     finYearSpinner, outletSpinner, sessionSpinner;
    //    Button login_bg, registration_bg;
    private Button      loginButton,registerButton;
    private CoordinatorLayout coordinatorLayout;
    private Context context=this;
    private ArrayAdapter<String> finYearAdapter;
    private List<String> finYearList;
    private ArrayAdapter<String> outletAdapter;
    private List<String> outletList;
    private ArrayAdapter<String> sessionAdapter;
    private List<String> sessionList;
    private GeneralOperationTools   generalOperationTools;
    private SharedPreferences   sp;
    ProgressDialog progressDialog;
    ArrayList<Set_Get> arrayList = new ArrayList<Set_Get>();
    ArrayList<String> M_FINYR_GET_LIST = new ArrayList<String>();
    ArrayList<String> OUTLET_GET_LIST = new ArrayList<String>();
    ArrayList<String> M_SESSION_LIST = new ArrayList<String>();
    Snackbar snackbar1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        finYearSpinner = (Spinner) findViewById(R.id.finYearSpinner);
        outletSpinner = (Spinner) findViewById(R.id.outletSpinner);
        sessionSpinner = (Spinner) findViewById(R.id.sessionSpinner);
        loginButton     =   (Button)    findViewById(R.id.login_loginButton);
        registerButton  =   (Button)    findViewById(R.id.login_registerButton);
        sp  =   getSharedPreferences(Consts.SP_NAME, MODE_PRIVATE);
        init();
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Sign Up Disable", Snackbar.LENGTH_LONG)
//                        .setAction("OK", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Thanks!", Snackbar.LENGTH_SHORT);
//                       snackbar1.show();
//                            }
//                        });
//                View snackBarView = snackbar.getView();
//          snackBarView.setBackgroundColor(Color.rgb(23,158,222));
//
//          snackbar.show();
//            }
//        });
    }
    private void init() {


        disableEnableWidgets(false);


        generalOperationTools = GeneralOperationTools.generalOperationToolsInstance();

        /***
         * Check valid user or not from username
         ***/
        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                //boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //    Toast.makeText(getApplicationContext(), "Searching for " + userName.getText().toString(), Toast.LENGTH_SHORT).show();
//                    handled = true;

                    if (!userName.getText().toString().trim().equals("")) {
                        if (NetworkUtil.getConnectivityStatus(context) != 0)
                            findValidUser(userName.getText().toString().trim());

                        else {
                         //   generalOperationTools.showSnackMessage("NO INTERNET CONNECTION", "OK", coordinatorLayout);

                            Snackbar snackbar = make(findViewById(android.R.id.content),"NO INTERNET CONNECTION", Snackbar.LENGTH_LONG)
                                    .setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
//                                  snackbar1 = make(coordinatorLayout, "Thanks", Snackbar.LENGTH_SHORT);
//                                               snackbar1.show();
                                        //    Toast.makeText(getApplicationContext(), " is selected!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
//                         .setActionTextColor(Color.rgb(23,158,222))
//                         .show();
                            View snackBarView = snackbar.getView();
                            snackBarView.setBackgroundColor(Color.rgb(23,158,222));

                            snackbar.show();


                        }
                    } else {
                       // user
                        userName.requestFocus();
                     //   generalOperationTools.showSnackMessage("ENTER VALID USERNAME", "OK", coordinatorLayout);
                        //Toast.makeText(getApplicationContext(), "ENTER VALID USERNAME", Toast.LENGTH_SHORT).show();
//                        AlertDialog alertDialog = new AlertDialog.Builder(DemoNew.this).create();
//                        alertDialog.setTitle("USER");
//                        alertDialog.setMessage("ENTER VALID USERNAME");
//                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                        alertDialog.show();

                        Snackbar snackbar = make(findViewById(android.R.id.content),"ENTER VALID USERNAME", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                 Snackbar snackbar1 = make(coordinatorLayout, "VALID USER", Snackbar.LENGTH_SHORT);
                                        //   snackbar1.show();
                                    }
                                });
//                         .setActionTextColor(Color.rgb(23,158,222))
//                         .show();
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(Color.rgb(23,158,222));

                        snackbar.show();

                    }

                    generalOperationTools.hideSoftKeyboard(DemoNew.this);

                    return true;
                }
                return false;
            }
        });
    }

    public void onRegister(View v){

        //     generalOperationTools.showSnackMessage("Sign Up Disable","OK",coordinatorLayout);

        Snackbar snackbar = make(findViewById(android.R.id.content), "Sign Up Disable", Snackbar.LENGTH_LONG)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    Snackbar snackbar1 = make(coordinatorLayout, "Thanks!", Snackbar.LENGTH_SHORT);
                     //   snackbar1.show();
                    }
                });

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.rgb(23,158,222));
        snackbar.show();

    }



    private void disableEnableWidgets(boolean flag) {

        password.setEnabled(flag);
        finYearSpinner.setEnabled(flag);
        outletSpinner.setEnabled(flag);
        sessionSpinner.setEnabled(flag);
//       loginButton.setEnabled(flag);
    }

    public void onLogin(View view){

        if(!userName.getText().toString().trim().equals(""))
        {
            if(!password.getText().toString().trim().equals("")){
                if(NetworkUtil.getConnectivityStatus(context)!=0)
                    signIn(userName.getText().toString().trim(),password.getText().toString().trim());
                else
                {
                    generalOperationTools.showSnackMessage("NO INTERNET CONNECTION","OK",coordinatorLayout);
                }
            }
            else{
                password.requestFocus();
             //   generalOperationTools.showSnackMessage("ENTER PASSWORD","OK",coordinatorLayout);

                Snackbar snackbar = make(findViewById(android.R.id.content), "ENTER PASSWORD", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                 Snackbar snackbar1 = make(coordinatorLayout, "VALID USER", Snackbar.LENGTH_SHORT);
                                //   snackbar1.show();
                            }
                        });
//                         .setActionTextColor(Color.rgb(23,158,222))
//                         .show();
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.rgb(23,158,222));

                snackbar.show();
            }
        }
        else
        {
            userName.requestFocus();
//            AlertDialog alertDialog = new AlertDialog.Builder(DemoNew.this).create();
//            alertDialog.setTitle("USER");
//            alertDialog.setMessage("ENTER USERNAME");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//            alertDialog.show();
            //generalOperationTools.showSnackMessage("ENTER USERNAME","OK",coordinatorLayout);

            Snackbar snackbar = make(findViewById(android.R.id.content), "ENTER USERNAME", Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                 Snackbar snackbar1 = make(coordinatorLayout, "VALID USER", Snackbar.LENGTH_SHORT);
                            //   snackbar1.show();
                        }
                    });
//                         .setActionTextColor(Color.rgb(23,158,222))
//                         .show();
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.rgb(23,158,222));

            snackbar.show();
        }

        generalOperationTools.hideSoftKeyboard(DemoNew.this);

    }

    private void signIn(String userName, String password) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETUSERNAME");
        params.put("USERID",userName);
        params.put("PASS",password);
        params.put("SPNAME", "SPA_LOGIN1_SV");
        params.put("PNO", "5");

        CustomDialogClass.showProgressDialog(context,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();


        jsonParsingUtils.stringRequest(params, new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response) {
                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

//                    for (int i = 0; i < jsonArray.length(); i++) {
//                      //  JSONObject explrObject = jsonArray.getJSONObject(i);
//                        JSONObject explrObject = jsonArray.getJSONObject(3);
//
//                        if(i==0) {
//                            status = Integer.parseInt(explrObject.getString("STATUS"));
//                            String  msg =   explrObject.getString("MESSAGE");
//                            Log.d("status----------signIn", String.valueOf(status));
//                            Log.d("MESSAGE----------signIn",msg);
//                            parseLoginUserJson(response, status, msg);
//                        }
//                    }
//                    JSONObject explrObject = jsonArray.getJSONObject(3);
//                    status = Integer.parseInt(explrObject.getString("STATUS"));
//                    String  msg =   explrObject.getString("MESSAGE");
//                    Log.d("status----------signIn", String.valueOf(status));
//                    Log.d("MESSAGE----------signIn",msg);
//                    parseLoginUserJson(response, status, msg);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            String USERNAME = explrObject.getString("USERNAME");
                            //    int PASSWORD = explrObject.getInt("PASSWORD");
                            String  msg =   explrObject.getString("DETAIL");
                            String USERPHOTO = explrObject.getString("USERPHOTO");
                            String NAME = explrObject.getString("NAME");
                            Log.d("msg------------",msg);
                            Log.d("USERNAME-------------",USERNAME);
                            //   Log.d("PASSWORD------------", String.valueOf(PASSWORD));
                            Log.d("USERPHOTO-------------",USERPHOTO);
                            Log.d("NAME-------------",NAME);
                            Log.d("status_signIn---------", String.valueOf(status));
                            parseLoginUserJson(response, status, msg);
                        }
                    }
                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseLoginUserJson(String response, int status, String msg) {
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(response.trim());
            Log.d("jsonArray-------------", String.valueOf(jsonArray));

            if (status==1){
                System.out.println(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(0);
                    Log.d("explrObject------------", String.valueOf(explrObject));
                    String USERNAME = explrObject.getString("USERNAME");
                    Log.d("USERNAME1-------------",USERNAME);
                    String STATUS = explrObject.getString("STATUS");
                    Log.d("STATUS1-------------",STATUS);
                    String DETAIL = explrObject.getString("DETAIL");
                    Log.d("DETAIL1-------------",DETAIL);
                    String USERPHOTO = explrObject.getString("USERPHOTO");
                    Log.d("USERPHOTO-------------",USERPHOTO);
                    String Name = explrObject.getString("NAME");
                    Log.d("NAME-------------",Name);
                    String PASSWORD = explrObject.getString("PASSWORD");
                    Log.d("PASSWORD-------------", PASSWORD);
                    String CUR_TIME = explrObject.getString("CUR_TIME");
                    Log.d("CUR_TIME-------------", CUR_TIME);
                    String CUR_DATE = explrObject.getString("CUR_DATE");
                    Log.d("CUR_DATE-------------", CUR_DATE);
//
//                     if (i == 1) {
//                         String USERNAME = explrObject.getString("USERNAME");
//                         Log.d("USERNAME1-------------",USERNAME);
//                         SharedPreferences.Editor    edit    =   sp.edit();
//                         edit.putString("USERNAME",  explrObject.getString("USERNAME"));
//                         edit.putString("PASSWORD",  password.getText().toString().trim());
//                         edit.putString("USERPHOTO", explrObject.getString("USERPHOTO"));
//                         edit.putString("NAME",      explrObject.getString("NAME"));
//                         edit.putString("CUR_TIME",  explrObject.getString("CUR_TIME"));
//                         edit.putString("CUR_DATE",  explrObject.getString("CUR_DATE"));
//                         edit.putString("FIN_YEAR",  finYearSpinner.getSelectedItem().toString());
//                         edit.putString("OUTLET",    outletSpinner.getSelectedItem().toString());
//                         edit.putString("SESSION",   sessionSpinner.getSelectedItem().toString());
//                         edit.commit();
////                         String email = userName.getText().toString();
////                         String pass = password.getText().toString();
////                         sp=getSharedPreferences("login",MODE_PRIVATE);
////                         if(sp.contains(email) && sp.contains(String.valueOf(pass))){
////                             startActivity(new Intent(DemoNew.this,NavigationDrawerActivity.class));
////                             finish();
////                         }
//                         Intent intent = new Intent(DemoNew.this, NavigationDrawerActivity.class);
//                         intent.putExtra("userName", String.valueOf(userName));
//                         intent.putExtra("password", String.valueOf(password));
//                         startActivity(intent);
//                        // goToNextActivity();
//                     }
                    SharedPreferences.Editor    edit    =   sp.edit();
                    edit.putString("PASSWORD",PASSWORD);
                    edit.putString("PASSWORD",  password.getText().toString().trim());
                    edit.putString("USERNAME",USERNAME);
                    edit.putString("STATUS",STATUS);
                    edit.putString("DETAIL",DETAIL);
                    edit.putString("USERPHOTO",USERPHOTO);
                    edit.putString("NAME",Name);
                    edit.putString("CUR_DATE",CUR_DATE);
                    edit.putString("CUR_TIME",CUR_TIME);
                    edit.putString("FIN_YEAR",  finYearSpinner.getSelectedItem().toString());
                    edit.putString("OUTLET",    outletSpinner.getSelectedItem().toString());
                    edit.putString("SESSION",   sessionSpinner.getSelectedItem().toString());
                    edit.commit();
                    goToNextActivity();
                }
                CustomDialogClass.showProgressDialog(context,false);
            }

            else {
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage(msg,"OK",coordinatorLayout);
            }
        }
        catch (JSONException e){
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
    }
    private void goToNextActivity() {

        startActivity(new Intent(this, NavigationDrawerActivity.class));
    }
    private void findValidUser(String userName ){

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","VALID_USER");
        params.put("USERNAME",userName);
        params.put("SPNAME", "USP_APP_LOGIN_SV");
        params.put("PNO", "4");

        CustomDialogClass.showProgressDialog(context,true);
        JsonParsingUtils jsonParsingUtils= JsonParsingUtils.getInstance();
        jsonParsingUtils.stringRequest(params,  new ServiceCallBack() {
            @Override
            public void onServiceError(String error) {
                CustomDialogClass.showProgressDialog(context,false);
                disableEnableWidgets(false);
                generalOperationTools.showSnackMessage(error.toUpperCase(),"OK",coordinatorLayout);
            }

            @Override
            public void onServiceResponse(String response ) {

                int status=0;
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response.trim());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj3 = jsonArray.getJSONObject(3);

                        if(i==0) {
                            status = Integer.parseInt(jObj3.getString("STATUS"));
                            Log.d("status----------", String.valueOf(status));
                            parseValidUserJson(response,status);
                        }
                    }

//


                } catch (JSONException e) {
                    CustomDialogClass.showProgressDialog(context,false);
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseValidUserJson(String response, int status) {
        JSONArray jsonArray = null;
        try{
            jsonArray = new JSONArray(response.trim());
//            if(status==1){
//
//                disableEnableWidgets(true);
//                System.out.println(jsonArray.length());
//                JSONObject jObj = jsonArray.getJSONObject(0);
//                JSONArray M_FINYR_ARRAY = jObj.getJSONArray("M_FINYR");
//                Log.d("M_FINYR------------", String.valueOf(M_FINYR_ARRAY));
//                for (int i = 0; i < M_FINYR_ARRAY.length(); i++) {
//                    JSONObject M_FINYR_OBJ = M_FINYR_ARRAY.getJSONObject(i);
//                    String FINYR = M_FINYR_OBJ.getString("FINYR");
//                    String COLNAME = M_FINYR_OBJ.getString("COLNAME");
//                    Log.d("FINYR-----------", FINYR);
//                    Log.d("COLNAME-----------", COLNAME);
//                    M_FINYR_GET_LIST.add(FINYR);
//                }
//                JSONObject jObj1 = jsonArray.getJSONObject(1);
//                JSONArray OUTLET_ARRAY = jObj1.getJSONArray("OUTLET");
//                Log.d("OUTLET------------", String.valueOf(OUTLET_ARRAY));
//                for (int j = 0; j < OUTLET_ARRAY.length(); j++) {
//                    JSONObject OUTLET_OBJ = OUTLET_ARRAY.getJSONObject(j);
//                    String DESCR = OUTLET_OBJ.getString("DESCR");
//                    String CODE = OUTLET_OBJ.getString("CODE");
//                    String COLNAME = OUTLET_OBJ.getString("COLNAME");
//                    Log.d("DESCR-----------", DESCR);
//                    Log.d("CODE-----------", CODE);
//                    Log.d("COLNAME-----------", COLNAME);
//
//                    OUTLET_GET_LIST.add(DESCR);
//                    OUTLET_GET_LIST.add(CODE);
//                    //   OUTLET_GET_LIST.add(COLNAME);
//
//                }
//                JSONObject jObj2 = jsonArray.getJSONObject(2);
//                JSONArray M_SESSION_ARRAY = jObj2.getJSONArray("M_SESSION");
//                Log.d("M_SESSION------------", String.valueOf(M_SESSION_ARRAY));
//                for (int k = 0; k < M_SESSION_ARRAY.length(); k++) {
//                    JSONObject M_SESSION_OBJ = M_SESSION_ARRAY.getJSONObject(k);
//                    String SESSIONNAME = M_SESSION_OBJ.getString("SESSIONNAME");
//                    String ACT = M_SESSION_OBJ.getString("ACT");
//                    String COLNAME = M_SESSION_OBJ.getString("COLNAME");
//                    Log.d("SESSIONNAME-----------", SESSIONNAME);
//                    Log.d("ACT-----------", ACT);
//                    Log.d("COLNAME-----------", COLNAME);
//
//                    M_SESSION_LIST.add(SESSIONNAME);
//                    M_SESSION_LIST.add(ACT);
//                    //      M_SESSION_LIST.add(COLNAME);
//                }
//
//                ArrayAdapter<String> adapter;
//                adapter = new ArrayAdapter<String>(DemoNew.this, android.R.layout.simple_dropdown_item_1line, M_FINYR_GET_LIST);
//                finYearSpinner.setAdapter(adapter);
//                finYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getBaseContext(), finYearSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//                ArrayAdapter<String> adapter1;
//                adapter1 = new ArrayAdapter<String>(DemoNew.this, android.R.layout.simple_dropdown_item_1line, OUTLET_GET_LIST);
//                outletSpinner.setAdapter(adapter1);
//                outletSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getBaseContext(), outletSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
//
//                ArrayAdapter<String> adapter2;
//                adapter2 = new ArrayAdapter<String>(DemoNew.this, android.R.layout.simple_dropdown_item_1line, M_SESSION_LIST);
//                sessionSpinner.setAdapter(adapter2);
//                sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getBaseContext(), sessionSpinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
//
////                CustomDialogClass.showProgressDialog(context,false);
////                generalOperationTools.showSnackMessage("VALID USER","OK",coordinatorLayout);
//            }
//            else{
//                CustomDialogClass.showProgressDialog(context,false);
//                generalOperationTools.showSnackMessage("USER NOT VALID","OK",coordinatorLayout);
//                disableEnableWidgets(false);
//            }

            if(status==1){
                disableEnableWidgets(true);
                finYearList = new ArrayList<String>();
                outletList  = new ArrayList<String>();
                sessionList = new ArrayList<String>();
                System.out.println(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    //   JSONObject explrObject = jsonArray.getJSONObject(i);
                    JSONObject explrObject = jsonArray.getJSONObject(0);
                    JSONObject explrObject1 = jsonArray.getJSONObject(1);
                    JSONObject explrObject2 = jsonArray.getJSONObject(2);
                    if (i == 1) {

                        JSONArray finYearJsonArray = explrObject.getJSONArray("M_FINYR");
                        for (int j = 0; j < finYearJsonArray.length(); j++) {
                            JSONObject finYearJsonObj = finYearJsonArray.getJSONObject(j);
                            String FINYR = finYearJsonObj.getString("FINYR");
                            String COLNAME = finYearJsonObj.getString("COLNAME");
                            Log.d("FINYR-----------", FINYR);
                            Log.d("COLNAME-----------", COLNAME);
                            finYearList.add(finYearJsonObj.getString("FINYR"));
                        }

                        finYearAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, finYearList);
                        finYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        finYearSpinner.setAdapter(finYearAdapter);
                    }
                    if (i == 2) {
                        JSONArray outletJsonArray = explrObject1.getJSONArray("OUTLET");
                        for (int j = 0; j < outletJsonArray.length(); j++) {
                            JSONObject outletJsonObj = outletJsonArray.getJSONObject(j);
                            String DESCR = outletJsonObj.getString("DESCR");
                            String CODE = outletJsonObj.getString("CODE");
                            String COLNAME = outletJsonObj.getString("COLNAME");
                            Log.d("DESCR-----------", DESCR);
                            Log.d("CODE-----------", CODE);
                            Log.d("COLNAME-----------", COLNAME);
                            outletList.add(outletJsonObj.getString("DESCR"));
                            //   outletList.add(outletJsonObj.getString("CODE"));
                            //  outletList.add(outletJsonObj.getString("COLNAME"));


                        }
                        outletAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, outletList);
                        outletAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        outletSpinner.setAdapter(outletAdapter);
                    }
                    if (i == 3) {
                        JSONArray sessionJsonArray = explrObject2.getJSONArray("M_SESSION");
                        for (int j = 0; j < sessionJsonArray.length(); j++) {
                            JSONObject sessionJsonObj = sessionJsonArray.getJSONObject(j);

                            sessionList.add(sessionJsonObj.getString("SESSIONNAME"));
                            //   sessionList.add(sessionJsonObj.getString("ACT"));
                            //  sessionList.add(sessionJsonObj.getString("COLNAME"));
                        }
                        sessionAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, sessionList);
                        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sessionSpinner.setAdapter(sessionAdapter);

                    }

                }
                CustomDialogClass.showProgressDialog(context,false);
//                  generalOperationTools.showSnackMessage("VALID USER","OK",coordinatorLayout);
                Snackbar snackbar = make(findViewById(android.R.id.content), "VALID USER", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                 Snackbar snackbar1 = make(coordinatorLayout, "VALID USER", Snackbar.LENGTH_SHORT);
                                //   snackbar1.show();
                            }
                        });
//                         .setActionTextColor(Color.rgb(23,158,222))
//                         .show();
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(Color.rgb(23,158,222));

                snackbar.show();
                //   Toast.makeText(DemoNew.this,"VALID USER",Toast.LENGTH_LONG).show();
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage("USER NOT VALID","OK",coordinatorLayout);
//                 Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "USER NOT VALID", Snackbar.LENGTH_LONG)
//                         .setAction("NOT_OK", new View.OnClickListener() {
//                             @Override
//                             public void onClick(View v) {
//                                 Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "USER NOT VALID", Snackbar.LENGTH_SHORT);
//                                 snackbar1.show();
//                             }
//                         });
////                         .setActionTextColor(Color.rgb(23,158,222))
////                         .show();
//                 View snackBarView = snackbar.getView();
//                 snackBarView.setBackgroundColor(Color.rgb(23,158,222));
//
//                 snackbar.show();
                disableEnableWidgets(false);
            }


        }
        catch (JSONException e){
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }


    }


}
