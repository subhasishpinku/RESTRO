package com.ebits.restro.activities;

/***
 * Created by Mr. Satyaki Mukherjee  on 14/07/16
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ebits.restro.R;
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

public class LoginActivity extends AppCompatActivity{

    private EditText    userName, password;
    private Spinner     finYearSpinner, outletSpinner, sessionSpinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        userName        =   (EditText)  findViewById(R.id.userName);
        Log.d("Username------>", ""+userName);
        Log.d("Username------>", userName.getText().toString());
        password        =   (EditText)  findViewById(R.id.login_password);
        finYearSpinner  =   (Spinner)   findViewById(R.id.login_finYearSpinner);
        outletSpinner   =   (Spinner)   findViewById(R.id.login_outletSpinner);
        sessionSpinner  =   (Spinner)   findViewById(R.id.login_sessionSpinner);
        loginButton     =   (Button)    findViewById(R.id.login_loginButton);
        registerButton  =   (Button)    findViewById(R.id.login_registerButton);

        sp  =   getSharedPreferences(Consts.SP_NAME, MODE_PRIVATE);

        init();

    }

    private void init() {


       disableEnableWidgets(false);

        generalOperationTools   =   GeneralOperationTools.generalOperationToolsInstance();

        /***
         * Check valid user or not from username
         ***/
        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                //boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Toast.makeText(getApplicationContext(), "Searching for " + userName.getText().toString(), Toast.LENGTH_SHORT).show();
//                    handled = true;

                    if(!userName.getText().toString().trim().equals(""))
                    {
                        if(NetworkUtil.getConnectivityStatus(context)!=0)
                            findValidUser(userName.getText().toString().trim());

                        else
                        {
                            generalOperationTools.showSnackMessage("NO INTERNET CONNECTION","OK",coordinatorLayout);
                        }
                    }
                    else
                    {
                        userName.requestFocus();
                        generalOperationTools.showSnackMessage("ENTER VALID USERNAME","OK",coordinatorLayout);
                    }

                    generalOperationTools.hideSoftKeyboard(LoginActivity.this);

                    return true;
                }
                return false;
            }
        });
    }

    public void onRegister(View v){

        generalOperationTools.showSnackMessage("Sign Up Disable","OK",coordinatorLayout);
    }

    /**
     * Disable Enable widgets
     * @param flag
     */
    private void disableEnableWidgets(boolean flag) {

        password.setEnabled(flag);
        finYearSpinner.setEnabled(flag);
        outletSpinner.setEnabled(flag);
        sessionSpinner.setEnabled(flag);
        loginButton.setEnabled(flag);
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
                generalOperationTools.showSnackMessage("ENTER PASSWORD","OK",coordinatorLayout);
            }
        }
        else
        {
            userName.requestFocus();
            generalOperationTools.showSnackMessage("ENTER USERNAME","OK",coordinatorLayout);
        }

        generalOperationTools.hideSoftKeyboard(LoginActivity.this);

    }

    /***
     * Sign in methods
     * @param userName
     * @param pwd
     */
    private void signIn(String userName, String pwd) {

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","GETUSERNAME");
        params.put("USERID",userName);
        params.put("PASS",pwd);
        params.put("SPNAME", "SPA_LOGIN1");
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

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject explrObject = jsonArray.getJSONObject(i);

                        if(i==0) {
                            status = Integer.parseInt(explrObject.getString("STATUS"));
                            String  msg =   explrObject.getString("MESSAGE");

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

    /***
     * Method for valid user checking
     * @param userName
     */
    private void findValidUser(String userName ){

        Map<String,String> params = new HashMap<String, String>();
        params.put("FLAG","VALID_USER");
        params.put("USERNAME",userName);
        params.put("SPNAME", "USP_APP_LOGIN");
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
                            JSONObject explrObject = jsonArray.getJSONObject(i);

                            if(i==0) {
                                status = Integer.parseInt(explrObject.getString("STATUS"));
//                                status = explrObject.getInt("STATUS");
 //                               Toast.makeText(LoginActivity.this, "You are Valid_User!", Toast.LENGTH_LONG).show();
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

    /***
     * Parse login user json
     * @param response
     * @param status
     */
    private void parseLoginUserJson(String response, int status, String msg) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

            if(status==1){
                System.out.println(jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);


                    if (i == 1) {

                        SharedPreferences.Editor    edit    =   sp.edit();
                        edit.putString("USERNAME",  explrObject.getString("USERNAME"));
                        edit.putString("PASSWORD",  password.getText().toString().trim());
                        edit.putString("USERPHOTO", explrObject.getString("USERPHOTO"));
                        edit.putString("NAME",      explrObject.getString("NAME"));
                        edit.putString("CUR_TIME",  explrObject.getString("CUR_TIME"));
                        edit.putString("CUR_DATE",  explrObject.getString("CUR_DATE"));
                        edit.putString("FIN_YEAR",  finYearSpinner.getSelectedItem().toString());
                        edit.putString("OUTLET",    outletSpinner.getSelectedItem().toString());
                        edit.putString("SESSION",   sessionSpinner.getSelectedItem().toString());
                        edit.commit();

                        goToNextActivity();
                    }
                }

                CustomDialogClass.showProgressDialog(context,false);
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage(msg,"OK",coordinatorLayout);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
    }

    /***
     * Pass to other activities
     */
    private void goToNextActivity() {

        startActivity(new Intent(this, NavigationDrawerActivity.class));
    }

    /***
     * Parse valid user json
     * @param response
     * @param status
     */




    private void parseValidUserJson(String response, int status) {

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(response.trim());

                if(status==1){
                disableEnableWidgets(true);

                finYearList = new ArrayList<String>();
                outletList  = new ArrayList<String>();
                sessionList = new ArrayList<String>();


                System.out.println(jsonArray.length());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject explrObject = jsonArray.getJSONObject(i);


                    /**For fin year adapter***/
                    if (i == 1) {
                        JSONArray finYearJsonArray = explrObject.getJSONArray("M_FINYR");
                        for (int j = 0; j < finYearJsonArray.length(); j++) {
                            JSONObject finYearJsonObj = finYearJsonArray.getJSONObject(j);

                            finYearList.add(finYearJsonObj.getString("FINYR"));
                        }

                        finYearAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, finYearList);
                        finYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        finYearSpinner.setAdapter(finYearAdapter);
                    }

                    /**For outlet adapter***/
                    if (i == 2) {
                        JSONArray outletJsonArray = explrObject.getJSONArray("OUTLET");
                        for (int j = 0; j < outletJsonArray.length(); j++) {
                            JSONObject outletJsonObj = outletJsonArray.getJSONObject(j);

                            outletList.add(outletJsonObj.getString("DESCR") + " # " + outletJsonObj.getString("CODE")+ " # " + outletJsonObj.getString("LOCATIONPREFIX"));
                        }
                        outletAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, outletList);
                        outletAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        outletSpinner.setAdapter(outletAdapter);
                    }

                    /**For session adapter***/
                    if (i == 3) {
                        JSONArray sessionJsonArray = explrObject.getJSONArray("M_SESSION");
                        for (int j = 0; j < sessionJsonArray.length(); j++) {
                            JSONObject sessionJsonObj = sessionJsonArray.getJSONObject(j);

                            sessionList.add(sessionJsonObj.getString("SESSIONNAME") + " # " + sessionJsonObj.getString("ACT"));
                        }
                        sessionAdapter = new ArrayAdapter<String>(this,
                                android.R.layout.simple_spinner_item, sessionList);
                        sessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sessionSpinner.setAdapter(sessionAdapter);

                    }
                }

                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage("VALID USER","OK",coordinatorLayout);
            }
            else{
                CustomDialogClass.showProgressDialog(context,false);
                generalOperationTools.showSnackMessage("USER NOT VALID","OK",coordinatorLayout);
                disableEnableWidgets(false);
            }
        } catch (JSONException e) {
            CustomDialogClass.showProgressDialog(context,false);
            e.printStackTrace();
        }
    }
}
