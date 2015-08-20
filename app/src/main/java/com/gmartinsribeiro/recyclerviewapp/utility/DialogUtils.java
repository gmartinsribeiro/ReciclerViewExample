package com.gmartinsribeiro.recyclerviewapp.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.gmartinsribeiro.recyclerviewapp.R;


/**
 * Created by Gonçalo Martins Ribeiro on 20-08-2015.
 */
public class DialogUtils {

    static AlertDialog.Builder builder;

    public static void createNetErrorDialog(final Activity activity) {

        builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.dialog_network_description))
                .setTitle(activity.getString(R.string.dialog_network_description))
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.settings),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(Settings.ACTION_SETTINGS);
                                activity.startActivity(i);
                            }
                        }
                )
                .setNegativeButton(activity.getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void createApiErrorDialog(final Activity activity) {

        builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.error_api))
                .setTitle(activity.getString(R.string.error_api_title))
                .setCancelable(false)
                .setNegativeButton(activity.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void createInternalErrorDialog(final Activity activity) {

        builder = new AlertDialog.Builder(activity);
        builder.setMessage(activity.getString(R.string.error_internal))
                .setTitle(activity.getString(R.string.error_internal_title))
                .setCancelable(false)
                .setNegativeButton(activity.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = builder.create();
        alert.show();
    }
}
