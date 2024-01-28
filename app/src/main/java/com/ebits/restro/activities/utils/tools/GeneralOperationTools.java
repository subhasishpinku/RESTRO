package com.ebits.restro.activities.utils.tools;

import android.app.Activity;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.ebits.restro.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ebits.restro.R.color.headerColor;

/**
 * Created by Mr. Satyaki Mukherjee on 7/12/2016.
 */
public class GeneralOperationTools {

    private static GeneralOperationTools generalOperationTools=null;

    private GeneralOperationTools(){}

    public static GeneralOperationTools generalOperationToolsInstance(){
        if(generalOperationTools==null)
            generalOperationTools   =   new GeneralOperationTools();
        return generalOperationTools;
    }

    /**
     * Hide soft keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    /***
     * SanckBar Message showing
     * @param info
     * @param actionMsg
     */
    public static void showSnackMessage(String info, String actionMsg, final CoordinatorLayout coordinatorLayout){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, info, Snackbar.LENGTH_LONG)
                .setAction(actionMsg, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Thanks!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(Color.rgb(23,158,222));

        snackbar.show();
    }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss formate date as string
     */
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

}
