package net.flyingbags.flyingapps.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.LoadingPresenter;
import net.flyingbags.flyingapps.service.LoadingService;

/**
 * Created by User on 2017-10-07.
 * 처음 시작하는 activity
 * loading 기능밖에 없음
 */

public class LoadingActivity extends AppCompatActivity implements LoadingPresenter.view {

    private LoadingService loadingService;
    private static final int PERMISSIONS_REQUEST_CAMERA = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // presenter를 통해서 2초 loading
        // mvp로 안해도 되는데 그냥 통일성 있게 해봤음
        loadingService = new LoadingService(this);
        permissionCheck();
        loading();
    }

    @Override
    public void loading() {
        loadingService.loading();
    }

    private void permissionCheck() {
        /*int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionCheck == PackageManager.PERMISSION_DENIED) {

        }*/

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
