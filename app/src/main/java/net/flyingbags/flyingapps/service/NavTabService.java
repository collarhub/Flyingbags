package net.flyingbags.flyingapps.service;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.NavTabPresenter;
import net.flyingbags.flyingapps.view.Tab0Fragment;
import net.flyingbags.flyingapps.view.Tab1Fragment;
import net.flyingbags.flyingapps.view.Tab2Fragment;
import net.flyingbags.flyingapps.view.Tab3Fragment;
import net.flyingbags.flyingapps.view.Tab4Fragment;
import net.flyingbags.flyingapps.view.Tab5Fragment;

/**
 * Created by User on 2017-10-08.
 */

public class NavTabService implements NavTabPresenter.presenter {
    private AppCompatActivity activity;
    private LinearLayout linearLayoutPlaceAutocompleteFragmentWrapper;
    private ImageButton imageButtonBack;

    public NavTabService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showNavTab() {
        FragmentTabHost tabHost = (FragmentTabHost) activity.findViewById(R.id.tab_host);
        tabHost.setup(activity, activity.getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("TAB0").setIndicator(null, null), Tab0Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.bell_drawable)), Tab1Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.tracking_drawable)), Tab2Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.list_drawable)), Tab3Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB4").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.gear_drawable)), Tab4Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB5").setIndicator(null, null), Tab5Fragment.class, null);

        final TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            final View tab = tabWidget.getChildTabViewAt(i);
            //tab.setPadding(0, 30, 0, 30);
            tab.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.tab_indicator));
        }
        tabWidget.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
        for(int i = 0; i < tabWidget.getTabCount(); i++) {
            final View tab = tabWidget.getChildAt(i);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(112,112);
            if(i == 1)
                lp.setMargins(0, 0, 20, 0);
            else if(i == 2) {
                lp.setMargins(0, 0, 70, 0);
                tab.setPadding(tab.getPaddingLeft() - 9, tab.getPaddingTop() - 9, tab.getPaddingRight() - 9, tab.getPaddingBottom() - 9);
            }
            else if(i == 3)
                lp.setMargins(70, 0, 0, 0);
            else if(i == 4)
                lp.setMargins(20, 0, 0, 0);
            tab.setLayoutParams(lp);
        }
        //tabWidget.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        //tabWidget.getChildAt(5).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        tabWidget.getChildAt(0).setVisibility(View.GONE);
        tabWidget.getChildAt(5).setVisibility(View.GONE);

        tabHost.setCurrentTab(0);
        linearLayoutPlaceAutocompleteFragmentWrapper = (LinearLayout)activity.findViewById(R.id.place_autocomplete_fragment_wrapper);
        linearLayoutPlaceAutocompleteFragmentWrapper.setVisibility(View.VISIBLE);
        imageButtonBack = (ImageButton)activity.getSupportActionBar().getCustomView().findViewById(R.id.back_button);
        imageButtonBack.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showHome() {
        ((TabHost) activity.findViewById(R.id.tab_host)).setCurrentTab(0);
    }

    @Override
    public void showProfile() {
        ((TabHost) activity.findViewById(R.id.tab_host)).setCurrentTab(5);
    }
}
