package com.example.administrator.restoapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

/**
 * Created by Kamlesh on 024 24/11/15.
 */
public class UIUtils {
    public static void replaceView(ViewGroup containerView, View newView, View oldView) {
        containerView.addView(newView);
        containerView.removeView(oldView);
    }

    public static AlertDialog buildAlertDialog(Context context, String title, String message, String posBtnText, boolean isCancelable) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, posBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static AlertDialog buildAlertDialog(Context context, String title, String message, String posBtnText, String negativeBtnText, boolean isCancelable, final DialogButtonsListener buttonsListener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, posBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonsListener.onPositiveButtonPress();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, negativeBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonsListener.onNegativeButtonPress();
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public static AlertDialog buildAlertDialog(Context context, String title, String message, String posBtnText, boolean isCancelable, final DialogButtonsListener buttonsListener) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        dialog.setTitle(title);
        dialog.setCancelable(isCancelable);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, posBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                buttonsListener.onPositiveButtonPress();
            }
        });
        return dialog;
    }

    public static void hideKeyboard(Activity activity) {
	    InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }


    public static void reload(Activity activity) {

        Intent intent = activity.getIntent();
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();
        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);



    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

//
//    public static CustomProgress createProgressDialog(Context activityContext , boolean isCancelable) {
//
//        return new CustomProgress(activityContext, isCancelable);
//
////		ProgressDialog progress = new ProgressDialog(activityContext, R.style.ProgressTheme);
////		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////		progress.setCancelable(isCancelable);
////        return progress;
//	}

    public static void changeLanguage(Activity activity, String languageCode) {
        SharedPrefUtils.put(activity,"selectedLanguage",languageCode);
        Resources res = activity.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(languageCode.toLowerCase())); // API 17+ only.
        } else {
            conf.locale = new Locale(languageCode.toLowerCase());
        }

// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);


    }

}

