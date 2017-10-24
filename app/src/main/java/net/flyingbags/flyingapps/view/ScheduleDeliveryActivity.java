package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OneDayDecorator;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.ScheduleDeliveryService;

import java.util.List;

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
    private String invoiceID;
    private Invoice invoice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_delivery);

        invoiceID = getIntent().getStringExtra("invoiceID");
        invoice = (Invoice) getIntent().getSerializableExtra("invoice");

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
                toOrderConfirm();
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

    private void showHome() {
        setResult(ActionBarPresenter.RESULT_HOME, null);
        finish();
    }

    private void showProfile() {
        setResult(ActionBarPresenter.RESULT_PROFILE, null);
        finish();
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

    private void toOrderConfirm() {
        Intent intent = new Intent(this, OrderConfirmActivity.class);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_delivery_type);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
        String deliveryType = radioButton.getText().toString();

        EditText editTextTo = (EditText) findViewById(R.id.editText_to_address);
        EditText editTextFrom = (EditText) findViewById(R.id.editText_from_address);
        String to = editTextTo.getText().toString();
        String from = editTextFrom.getText().toString();

        MaterialCalendarView materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        List<CalendarDay> selectedDates = materialCalendarView.getSelectedDates();
        String minDateExpected = selectedDates.toString();
        String maxDateExpected = selectedDates.get(selectedDates.size() - 1).getDate().toString();
        String selected = materialCalendarView.getSelectedDate().getDate().toString();
        Toast.makeText(this, selectedDates.toString(), Toast.LENGTH_SHORT).show();

        intent.putExtra("invoiceID", invoiceID);
        intent.putExtra("invoice", invoice);
        startActivityForResult(intent, ActionBarPresenter.REQUEST_CODE_TAB);
    }
}
