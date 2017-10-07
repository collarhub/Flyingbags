package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.presenter.LoadingPresenter;
import net.flyingbags.flyingapps.service.LoadingService;

/**
 * Created by User on 2017-10-07.
 * 처음 시작하는 activity
 * loading 기능밖에 없음
 */

public class LoadingActivity extends AppCompatActivity implements LoadingPresenter.view {

    LoadingService loadingService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // presenter를 통해서 2초 loading
        // mvp로 안해도 되는데 그냥 통일성 있게 해봤음
        loadingService = new LoadingService();
        loading();
    }

    @Override
    public void loading() {
        loadingService.loading(this);
    }
}
