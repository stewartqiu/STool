package net.schooldroid.stool.Permission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.schooldroid.stool.R;

public class RequestCameraPermission extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 1111;

    public static HandlePermission.Listener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_camera_permission);

        Button button = findViewById(R.id.reqButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(RequestCameraPermission.this, android.Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(RequestCameraPermission.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST);
                } else {
                    ActivityCompat.requestPermissions(RequestCameraPermission.this, new String[]{android.Manifest.permission.CAMERA}, PERMISSIONS_REQUEST);
                }
            }
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case PERMISSIONS_REQUEST:
                for (int res : grantResults){
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }
        if (allowed){
            this.finish();
            if (listener!=null) {
                listener.onPermissionGranted();
            }
        }
        else {
            // we will give warning to user that they haven't granted permissions.
            Toast.makeText(this, "Aplikasi tidak dapat dilanjutkan. Mohon berikan izin akses kamera.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
