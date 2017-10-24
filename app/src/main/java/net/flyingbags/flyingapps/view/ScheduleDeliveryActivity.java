package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OneDayDecorator;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.MainPresenter;
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
    private MaterialCalendarView materialCalendarView;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private ImageButton imageButtonBoxHelp;
    private BoxHelpDialog boxHelpDialog;
    private Button buttonToOrderConfirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_delivery);

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
        materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        LinearLayout linearLayout = (LinearLayout)materialCalendarView.getChildAt(0);
        linearLayout.setBackgroundResource(R.drawable.calback);
        materialCalendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
        materialCalendarView.addDecorators(oneDayDecorator);
        imageButtonBoxHelp = (ImageButton) findViewById(R.id.imageButton_box_help);
        imageButtonBoxHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxHelpDialog = new BoxHelpDialog(ScheduleDeliveryActivity.this);
                boxHelpDialog.show();
            }
        });
        buttonToOrderConfirm = (Button) findViewById(R.id.button_to_order_confirm);
        buttonToOrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ScheduleDeliveryActivity.this, OrderConfirmActivity.class), ActionBarPresenter.REQUEST_CODE_TAB);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ActionBarPresenter.REQUEST_CODE_TAB) {
            if(resultCode == ActionBarPresenter.RESULT_HOME) {
                showHome();
            }
            else if(resultCode == ActionBarPresenter.RESULT_PROFILE) {
                showProfile();
            }
        }
    }
}
