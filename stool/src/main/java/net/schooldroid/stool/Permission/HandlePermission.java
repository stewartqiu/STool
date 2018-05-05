package net.schooldroid.stool.Permission;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

public class HandlePermission {

    public interface Listener{
        void onPermissionGranted();
    }

    public HandlePermission() {

    }

    public static void location (Activity activity, Listener listener) {
        boolean isAllow = checkLocationPermission(activity);
        if (!isAllow) {
            ReqLocationActivity.listener = listener;
            activity.startActivity(new Intent(activity, ReqLocationActivity.class));
        } else {
            listener.onPermissionGranted();
        }
    }

    public static boolean checkLocationPermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }






    public static void storage (Activity activity, Listener listener) {
        boolean isAllow = checkStoragePermission(activity);
        if (!isAllow) {
            RequestStoragePermission.listener = listener;
            activity.startActivity(new Intent(activity, RequestStoragePermission.class));
        } else {
            listener.onPermissionGranted();
        }
    }

    public static boolean checkStoragePermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }






    public static void camera (Activity activity, Listener listener) {
        boolean isAllow = checkCameraPermission(activity);
        if (!isAllow) {
            RequestCameraPermission.listener = listener;
            activity.startActivity(new Intent(activity, RequestCameraPermission.class));
        } else {
            listener.onPermissionGranted();
        }
    }

    public static boolean checkCameraPermission(Context context) {
        return Build.VERSION.SDK_INT < 23 || ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }


}
