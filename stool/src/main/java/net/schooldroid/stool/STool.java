package net.schooldroid.stool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import net.schooldroid.stool.ReqLocationActivity;


public class STool {

    public interface locAccessListener{
        void onSuccess();
    }


    public static void handleLocationPermission(Activity activity, STool.locAccessListener listener){
        boolean isAllow = checkLocationPermission(activity);
        if (!isAllow) {
            ReqLocationActivity.listener = listener;
            activity.startActivity(new Intent(activity, ReqLocationActivity.class));
        } else {
            listener.onSuccess();
        }
    }

    public static boolean checkLocationPermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}