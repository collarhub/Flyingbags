package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.ScheduleDeliveryService;

/**
 * Created by User on 2017-10-10.
 */

public class ScheduleDeliveryActivity extends AppCompatActivity implements ActionBarPresenter.view, ScheduleDeliveryPresenter.view {
    private ActionBarService actionBarService;
    private ScheduleDeliveryService scheduleDeliveryService;
    private View viewActionBar;
    private ImageButton imageButtonHome;
    private ImageButton imageButtonProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBarService = new ActionBarService(this, "Schedule Delivery");
        scheduleDeliveryService = new ScheduleDeliveryService(this);

        showActionBar();

        viewActionBar = getSupportActionBar().getCustomView();
        imageButtonHome = (ImageButton) viewActionBar.findViewById(R.id.home_button);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome();
            }
        });

        imageButtonProfile = (ImageButton) viewActionBar.findViewById(R.id.profile_button);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });
    }

    @Override
    public void showActionBar() {
        actionBarService.showActionBar();
    }

    @Override
    public void setTitle(String title) {
        actionBarService.setTitle(title);
    }

    @Override
    public void showHome() {
        scheduleDeliveryService.showHome();
    }

    @Override
    public void showProfile() {
        scheduleDeliveryService.showProfile();
    }
}
