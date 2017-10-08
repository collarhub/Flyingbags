package net.flyingbags.flyingapps.service;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;

/**
 * Created by User on 2017-10-08.
 */

public class ActionBarService implements ActionBarPresenter.presenter {

    private AppCompatActivity activity;
    private String title;

    public ActionBarService(AppCompatActivity activity, String title) {
        this.activity = activity;
        this.title = title;
    }

    @Override
    public void showActionBar() {
        activity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        activity.getSupportActionBar().setCustomView(R.layout.action_bar);
        View view = activity.getSupportActionBar().getCustomView();
        ((TextView) view.findViewById(R.id.main_title)).setText(title);
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        View view = activity.getSupportActionBar().getCustomView();
        ((TextView) view.findViewById(R.id.main_title)).setText(title);
    }
}
