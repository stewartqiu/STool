package net.schooldroid.stool.Permission;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.schooldroid.stool.R;


public class ReqLocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSIONS_REQUEST = 1111;


    public static HandlePermission.Listener listener = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.req_location);

        Button button = findViewById(R.id.reqButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ReqLocationActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(ReqLocationActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSIONS_REQUEST);
                } else {
                    ActivityCompat.requestPermissions(ReqLocationActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSIONS_REQUEST);
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;

        switch (requestCode){
            case LOCATION_PERMISSIONS_REQUEST:
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
            Toast.makeText(this, "Aplikasi tidak dapat dilanjutkan. Mohon berikan izin akses lokasi.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
    }
}
