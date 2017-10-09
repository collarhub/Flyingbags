package net.flyingbags.flyingapps.service;

import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    public NavTabService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showNavTab() {
        FragmentTabHost tabHost = (FragmentTabHost) activity.findViewById(R.id.tab_host);
        tabHost.setup(activity, activity.getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(tabHost.newTabSpec("TAB0").setIndicator(null, null), Tab0Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.bell)), Tab1Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.wing)), Tab2Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.list)), Tab3Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB4").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.gear)), Tab4Fragment.class, null);
        tabHost.addTab(tabHost.newTabSpec("TAB5").setIndicator(null, null), Tab5Fragment.class, null);

        final TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            final View tab = tabWidget.getChildTabViewAt(i);
            tab.setPadding(0, 30, 0, 30);
            tab.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.tab_indicator));
        }
        tabWidget.getChildAt(0).setLayoutParams(new LinearLayout.LayoutParams(0, 0));
        tabWidget.getChildAt(5).setLayoutParams(new LinearLayout.LayoutParams(0, 0));

        tabHost.setCurrentTab(0);
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
