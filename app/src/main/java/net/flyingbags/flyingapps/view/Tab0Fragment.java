package net.flyingbags.flyingapps.view;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.Tab0Presenter;
import net.flyingbags.flyingapps.service.Tab0Service;

/**
 * Created by User on 2017-10-09.
 */

public class Tab0Fragment extends Fragment implements Tab0Presenter.view {

    private Tab0Service tab0Service;
    private ImageButton imageButtonMenu;
    private ImageButton imageButtonScan;
    private View view;
    private ProgressDialog progressDialog;
    private GoogleMap googleMap;
    private Marker marker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab0, container, false);

        tab0Service = new Tab0Service(this);
        showMap();
        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressbar_spin);

        imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButton_menu);
        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuToggle();
            }
        });
        imageButtonScan = (ImageButton) view.findViewById(R.id.imageButton_scan);
        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });

        return view;
    }

    private void menuToggle() {
        final ImageButton imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButton_menu);
        final ImageButton imageButtonScan = (ImageButton) view.findViewById(R.id.imageButton_scan);
        final TextView textViewScan = (TextView) view.findViewById(R.id.textView_scan);
        final ImageButton linearLayoutTransparentGray = (ImageButton) view.findViewById(R.id.transparentGray);
        ObjectAnimator objectAnimatorButton;
        ObjectAnimator objectAnimatorText;
        if(imageButtonScan.getVisibility() == View.INVISIBLE) {
            linearLayoutTransparentGray.setVisibility(View.VISIBLE);
            imageButtonMenu.setImageResource(R.drawable.x);
            imageButtonScan.setVisibility(View.VISIBLE);
            objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop(), imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorButton.setDuration(200);
            objectAnimatorButton.start();
            objectAnimatorButton.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                @Override
                public void onAnimationEnd(Animator animation) {
                    textViewScan.setVisibility(View.VISIBLE);
                    ObjectAnimator objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, 0);
                    objectAnimatorButton.setDuration(300);
                    ObjectAnimator objectAnimatorText = ObjectAnimator.ofFloat(textViewScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, 0);
                    objectAnimatorText.setDuration(300);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(objectAnimatorButton, objectAnimatorText);
                    animatorSet.start();
                }
                @Override
                public void onAnimationCancel(Animator animation) {}
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        }
        else {
            imageButtonMenu.setImageResource(R.drawable.qrcode);
            objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", 0, imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorButton.setDuration(300);
            objectAnimatorText = ObjectAnimator.ofFloat(textViewScan, "translationY", 0, imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorText.setDuration(300);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorButton, objectAnimatorText);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                @Override
                public void onAnimationEnd(Animator animation) {
                    textViewScan.setVisibility(View.INVISIBLE);
                    ObjectAnimator objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, imageButtonMenu.getTop() - imageButtonScan.getTop());
                    objectAnimatorButton.setDuration(200);
                    objectAnimatorButton.start();
                    objectAnimatorButton.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {}
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imageButtonScan.setVisibility(View.INVISIBLE);
                            linearLayoutTransparentGray.setVisibility(View.INVISIBLE);
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {}
                        @Override
                        public void onAnimationRepeat(Animator animation) {}
                    });
                }
                @Override
                public void onAnimationCancel(Animator animation) {}
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        }
    }

    private void scanQR() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.addExtra("PROMPT_MESSAGE", "Scan QR Code within frame");
        //integrator.setResultDisplayDuration(1000);
        integrator.initiateScan();
    }

    private void showMap() {
        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction =
                getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mapFragment, "mapFragment");
        fragmentTransaction.commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Tab0Fragment.this.googleMap = googleMap;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.560933, 126.986293))
                        .title("Marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.560933, 126.986293)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.560933, 126.986293), 15);
                googleMap.animateCamera(cameraUpdate);
                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    public void mymap(Place place) {
        marker.remove();
        marker = googleMap.addMarker(new MarkerOptions()
                .position(place.getLatLng())
                .title(place.getName().toString()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15);
        googleMap.animateCamera(cameraUpdate);
    }
}
