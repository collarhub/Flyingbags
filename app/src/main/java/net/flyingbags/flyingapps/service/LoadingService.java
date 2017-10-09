package net.flyingbags.flyingapps.service;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.LoadingPresenter;
import net.flyingbags.flyingapps.view.Login2Activity;
import net.flyingbags.flyingapps.view.LoginActivity;

/**
 * Created by User on 2017-10-07.
 * 실제로 loading을 수행하는 presenter
 * 2초 슬립하고 LoginActivity로 넘어감
 */

public class LoadingService implements LoadingPresenter.presenter {

    private AppCompatActivity activity;

    public LoadingService(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void loading() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }
}