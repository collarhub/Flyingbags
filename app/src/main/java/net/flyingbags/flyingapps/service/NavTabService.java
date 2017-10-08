package net.flyingbags.flyingapps.service;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.NavTabPresenter;

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
        TabHost tabHost = (TabHost) activity.findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec tabSpecTab1 = tabHost.newTabSpec("TAB1").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.bell));
        tabSpecTab1.setContent(R.id.tab1);
        tabHost.addTab(tabSpecTab1);

        TabHost.TabSpec tabSpecTab2 = tabHost.newTabSpec("TAB2").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.wing));
        tabSpecTab2.setContent(R.id.tab2);
        tabHost.addTab(tabSpecTab2);

        TabHost.TabSpec tabSpecTab3 = tabHost.newTabSpec("TAB3").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.list));
        tabSpecTab3.setContent(R.id.tab3);
        tabHost.addTab(tabSpecTab3);

        TabHost.TabSpec tabSpecTab4 = tabHost.newTabSpec("TAB4").setIndicator(null, ContextCompat.getDrawable(activity, R.drawable.gear));
        tabSpecTab4.setContent(R.id.tab4);
        tabHost.addTab(tabSpecTab4);

        final TabWidget tabWidget = tabHost.getTabWidget();
        for (int i = 0; i < tabWidget.getTabCount(); i++) {
            final View tab = tabWidget.getChildTabViewAt(i);
            tab.setPadding(0, 30, 0, 30);
            tab.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.tab_indicator));
        }

        tabHost.setCurrentTab(0);
    }
}
