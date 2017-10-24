package net.flyingbags.flyingapps.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;

/**
 * Created by User on 2017-10-11.
 */

public class ScheduleDeliveryService implements ScheduleDeliveryPresenter.presenter {
    private AppCompatActivity activity;

    public ScheduleDeliveryService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showHome() {
        activity.setResult(ScheduleDeliveryPresenter.RESULT_HOME, null);
        activity.finish();
    }

    @Override
    public void showProfile() {
        activity.setResult(ScheduleDeliveryPresenter.RESULT_PROFILE, null);
        activity.finish();
    }
}
