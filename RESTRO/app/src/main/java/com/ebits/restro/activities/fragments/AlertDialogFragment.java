package com.ebits.restro.activities.fragments;

/**
 * Created by Mr. Satyaki Mukherjee on 7/20/2016.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.ebits.restro.R;
import com.ebits.restro.activities.utils.constant.Consts;

public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.mipmap.ic_launcher)
                // Set Dialog Title
                .setTitle("Sign out")
                // Set Dialog Message
                .setMessage("Do you want to sign out?")

                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp  =   getActivity().getSharedPreferences(Consts.SP_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.clear().commit();
                        getActivity().finish();
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        dismiss();
                    }
                }).create();
    }
}