package com.example.user.flyingbags.service;

import android.app.Activity;
import android.content.Intent;

import com.example.user.flyingbags.presenter.LoadingPresenter;
import com.example.user.flyingbags.view.LoginActivity;

/**
 * Created by User on 2017-10-07.
 * 실제로 loading을 수행하는 presenter
 * 2초 슬립하고 LoginActivity로 넘어감
 */

public class LoadingService implements LoadingPresenter.presenter {
    @Override
    public void loading(Activity activity) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }
}
