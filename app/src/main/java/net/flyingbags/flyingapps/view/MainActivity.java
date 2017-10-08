package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.NavTabPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.NavTabService;

/**
 * Created by User on 2017-10-07.
 * 구현 안됨
 */

public class MainActivity extends AppCompatActivity implements ActionBarPresenter.view, NavTabPresenter.view{

    private ActionBarService actionBarService;
    /*private ImageButton imageButtonHome;*/
    private NavTabService navTabService;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBarService = new ActionBarService(this, "My Location");
        showActionBar();

        /*View view = getSupportActionBar().getCustomView();
        imageButtonHome = (ImageButton) view.findViewById(R.id.home_button);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/

        navTabService = new NavTabService(this);
        showNavTab();

        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case "TAB1":
                        setTitle("Notifications");
                        break;
                    case "TAB2":
                        setTitle("My Delivery");
                        break;
                    case "TAB3":
                        setTitle("My Order");
                        break;
                    case "TAB4":
                        setTitle("Settings");
                        break;
                }
            }
        });
    }

    @Override
    public void showActionBar() {
        actionBarService.showActionBar();
    }

    @Override
    public void showNavTab() {
        navTabService.showNavTab();
    }

    @Override
    public void setTitle(String title) {
        actionBarService.setTitle(title);
    }
}
