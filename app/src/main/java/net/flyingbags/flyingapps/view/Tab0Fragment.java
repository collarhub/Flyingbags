package net.flyingbags.flyingapps.view;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.CustomPlaceAutocompleteFragment;
import net.flyingbags.flyingapps.etc.Shop;
import net.flyingbags.flyingapps.presenter.Tab0Presenter;
import net.flyingbags.flyingapps.service.Tab0Service;

import org.w3c.dom.Text;

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
    private Marker marker2;
    private Marker marker3;
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

        ((LinearLayout)getActivity().findViewById(R.id.place_autocomplete_fragment_wrapper)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinearLayout)view.findViewById(R.id.linearLayout_shop_info)).setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void menuToggle() {
        final ImageButton imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButton_menu);
        final ImageButton imageButtonScan = (ImageButton) view.findViewById(R.id.imageButton_scan);
        final TextView textViewScan = (TextView) view.findViewById(R.id.textView_scan);
        final ImageButton linearLayoutTransparentGray = (ImageButton) view.findViewById(R.id.transparentGray);
        final LinearLayout placeAutocompleteFragmentWrapper = (LinearLayout)getActivity().findViewById(R.id.place_autocomplete_fragment_wrapper);
        ObjectAnimator objectAnimatorButton;
        ObjectAnimator objectAnimatorText;
        if(imageButtonScan.getVisibility() == View.INVISIBLE) {
            if(marker3 != null && marker3.isInfoWindowShown()) {
                marker3.hideInfoWindow();
            }
            ((LinearLayout)view.findViewById(R.id.linearLayout_shop_info)).setVisibility(View.GONE);
            linearLayoutTransparentGray.setVisibility(View.VISIBLE);
            placeAutocompleteFragmentWrapper.setVisibility(View.INVISIBLE);
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
                            placeAutocompleteFragmentWrapper.setVisibility(View.VISIBLE);
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
            public void onMapReady(final GoogleMap googleMap) {
                Tab0Fragment.this.googleMap = googleMap;
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        ((LinearLayout)view.findViewById(R.id.linearLayout_shop_info)).setVisibility(View.GONE);
                        if(marker3 != null && marker3.isInfoWindowShown()) {
                            marker3.hideInfoWindow();
                        }
                    }
                });
                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker3 = marker;
                        if(marker.getTag() instanceof Shop) {
                            marker3 = marker;
                            Shop shop = (Shop) marker.getTag();
                            ((TextView) view.findViewById(R.id.textView_shop_name)).setText(shop.getId() + ". " + shop.getName());
                            ((TextView) view.findViewById(R.id.textView_shop_address)).setText(shop.getAddress());
                            ((TextView) view.findViewById(R.id.textView_shop_simple_address)).setText(shop.getSimpleAddress());
                            ((ImageView) view.findViewById(R.id.imageView_shop_image)).setImageResource(shop.getDrawable());
                            ((LinearLayout) view.findViewById(R.id.linearLayout_shop_info)).setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                });
                Bitmap bitmap;
                int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getActivity().getResources().getDisplayMetrics());
                int height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getActivity().getResources().getDisplayMetrics());
                // 3.3.1.S : 마포구 서교동 339-1
                bitmap = resizeBitmap("shop1", width, height);
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5549976, 126.9249692))
                        .title("3.3.1.S")
                        .icon(icon));
                marker.setTag(new Shop(1, "3.3.1.S", "마포구 서교동 339-1", "서교동", R.drawable.three));
                // 체크# : 마포구 홍익로10 푸르지오상가 139 140호
                bitmap = resizeBitmap("shop2", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5538075, 126.9212728))
                        .title("check#")
                        .icon(icon));
                marker.setTag(new Shop(2, "check#", "마포구 홍익로10 푸르지오상가 139 140호", "서교동", R.drawable.checkshop));
                // 어나더어썸 : 마포구 서교동 339-3 새봄빌딩 102호
                bitmap = resizeBitmap("shop3", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5542494, 126.9245967))
                        .title("Another Awesome")
                        .icon(icon));
                marker.setTag(new Shop(3, "Another Awesome", "마포구 서교동 339-3 새봄빌딩 102호", "서교동", R.drawable.anotherawesome));
                // Moi오이 : 마포구 서교동 336-15 1층 Moi오이
                bitmap = resizeBitmap("shop4", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5541017, 126.9255936))
                        .title("Moi")
                        .icon(icon));
                marker.setTag(new Shop(4, "Moi", "마포구 서교동 336-15 1층 Moi오이", "서교동", R.drawable.moi));
                // 바이엘 : 서울 마포구 와우산로 27길 38
                bitmap = resizeBitmap("shop5", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5546768, 126.9243302))
                        .title("by L")
                        .icon(icon));
                marker.setTag(new Shop(5, "by L", "서울 마포구 와우산로 27길 38", "서교동", R.drawable.byl));
                // 1# : 서울 마포구 서교동 332-12
                bitmap = resizeBitmap("shop6", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5549583, 126.9237773))
                        .title("1#")
                        .icon(icon));
                marker.setTag(new Shop(6, "1#", "서울 마포구 서교동 332-12", "서교동", R.drawable.oneshop));
                // ANUE : 서울 마포구 서교동 347-17 1층
                bitmap = resizeBitmap("shop7", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5554262, 126.9229183))
                        .title("ANUE")
                        .icon(icon));
                marker.setTag(new Shop(7, "ANUE", "서울 마포구 서교동 347-17 1층", "서교동", R.drawable.anue));
                // 첸트로 : 서울 마포구 노고산동 56-29
                bitmap = resizeBitmap("shop8", width, height);
                icon = BitmapDescriptorFactory.fromBitmap(bitmap);
                bitmap.recycle();
                bitmap = null;
                Tab0Fragment.this.marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(37.5547195, 126.9309247))
                        .title("첸트로")
                        .icon(icon));
                marker.setTag(new Shop(8, "첸트로", "서울 마포구 노고산동 56-29", "노고산동", R.drawable.chen));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.554816, 126.924351)));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(37.554816, 126.924351), 15);
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
        if(marker2 != null) {
            marker2.remove();
        }
        marker2 = googleMap.addMarker(new MarkerOptions()
                .position(place.getLatLng())
                .title(place.getName().toString()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15);
        googleMap.animateCamera(cameraUpdate);
    }

    public void infoClear() {
        ((LinearLayout)view.findViewById(R.id.linearLayout_shop_info)).setVisibility(View.GONE);
        if(marker3 != null && marker3.isInfoWindowShown()) {
            marker3.hideInfoWindow();
        }
    }

    public Bitmap resizeBitmap(String drawableName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(drawableName, "drawable", getActivity().getPackageName()));
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }
}
