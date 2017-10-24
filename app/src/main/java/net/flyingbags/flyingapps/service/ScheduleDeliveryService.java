package net.flyingbags.flyingapps.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;

/**
 * Created by User on 2017-10-11.
 */

public class ScheduleDeliveryService implements ScheduleDeliveryPresenter.presenter {
    private ScheduleDeliveryPresenter.view activity;

    public ScheduleDeliveryService(ScheduleDeliveryPresenter.view activity) {
        this.activity = activity;
    }
}
