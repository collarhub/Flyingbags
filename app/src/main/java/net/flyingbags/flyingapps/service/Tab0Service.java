package net.flyingbags.flyingapps.service;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.Tab0Presenter;
import net.flyingbags.flyingapps.view.Tab0Fragment;

/**
 * Created by User on 2017-10-09.
 */

public class Tab0Service implements Tab0Presenter.presenter {
    private Tab0Presenter.view view;

    public Tab0Service(Tab0Presenter.view view) {
        this.view = view;
    }
}
