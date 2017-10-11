package net.flyingbags.flyingapps.service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.view.LoginActivity;
import net.flyingbags.flyingapps.view.ScheduleDeliveryActivity;

/**
 * Created by User on 2017-10-10.
 */

public class MainService implements MainPresenter.presenter {
    private AppCompatActivity activity;

    public MainService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public String verifyQR(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result.getContents() == null) {
            activity.startActivityForResult(new Intent(activity, ScheduleDeliveryActivity.class), MainPresenter.REQUEST_CODE_TAB);
            return "Scan Failed";
        }
        else {
            activity.startActivityForResult(new Intent(activity, ScheduleDeliveryActivity.class), MainPresenter.REQUEST_CODE_TAB);
            return "Scan Success";
        }
    }
}
