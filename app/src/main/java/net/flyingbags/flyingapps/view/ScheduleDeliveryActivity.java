package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;

/**
 * Created by User on 2017-10-10.
 */

public class ScheduleDeliveryActivity extends AppCompatActivity implements ActionBarPresenter.view {
    ActionBarService actionBarService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBarService = new ActionBarService(this, "Schedule Delivery");
        showActionBar();
    }

    @Override
    public void showActionBar() {
        actionBarService.showActionBar();
    }

    @Override
    public void setTitle(String title) {
        actionBarService.setTitle(title);
    }
}
