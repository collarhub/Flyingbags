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
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OneDayDecorator;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.MainService;
import net.flyingbags.flyingapps.service.ScheduleDeliveryService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by User on 2017-10-10.
 */

public class ScheduleDeliveryActivity extends AppCompatActivity implements ActionBarPresenter.view, ScheduleDeliveryPresenter.view, MainPresenter.view {
    private ActionBarService actionBarService;
    private ScheduleDeliveryService scheduleDeliveryService;
    private MainService mainService;
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
    private TextView textViewPackageType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_delivery);

        invoiceID = getIntent().getStringExtra("invoiceID");
        invoice = (Invoice) getIntent().getSerializableExtra("invoice");

        actionBarService = new ActionBarService(this, "Schedule Delivery");
        scheduleDeliveryService = new ScheduleDeliveryService(this);
        mainService = new MainService(this);

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

        setPackageType();
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
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        String orderDate = dateFormat.format(calendar.getTime());

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup_delivery_type);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
        String deliveryType = radioButton.getText().toString();

        EditText editTextTo = (EditText) findViewById(R.id.editText_to_address);
        EditText editTextFrom = (EditText) findViewById(R.id.editText_from_address);
        String target = editTextTo.getText().toString();
        String departure = editTextFrom.getText().toString();

        MaterialCalendarView materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        List<CalendarDay> selectedDates = materialCalendarView.getSelectedDates();
        CalendarDay minDate = selectedDates.get(0);
        CalendarDay maxDate = selectedDates.get(0);
        for(CalendarDay date : selectedDates) {
            if(minDate.isAfter(date)) {
                minDate = date;
            }
            if(maxDate.isBefore(date)) {
                maxDate = date;
            }
        }
        String minDateExpected = dateFormat.format(minDate.getDate());
        String maxDateExpected = dateFormat.format(maxDate.getDate());

        invoice.setOrderDate(orderDate);
        invoice.setDeliveryType(deliveryType);
        invoice.setTarget(target);
        invoice.setDeparture(departure);
        invoice.setMinDateExpected(minDateExpected);
        invoice.setMaxDateExpected(maxDateExpected);

        mainService.registerInvoiceOnInvoices(invoiceID, invoice);
    }

    private void setPackageType() {
        switch (invoice.getPackageType()) {
            case "small":
                textViewPackageType = (TextView) findViewById(R.id.textView_small);
                break;
            case "medium":
                textViewPackageType = (TextView) findViewById(R.id.textView_medium);
                break;
            case "large":
                textViewPackageType = (TextView) findViewById(R.id.textView_large);
                break;
        }
        textViewPackageType.setText("1");
    }

    @Override
    public void onGetInvoicesVectorFailed() {

    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {

    }

    @Override
    public void onGetInvoiceSuccess(String invoiceID, Invoice invoice) {

    }

    @Override
    public void onGetInvoiceFailed() {

    }

    @Override
    public void onRegisterInvoiceOnNewOrderFailed() {
        Toast.makeText(this, "neworderfail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterInvoiceOnNewOrderSuccess() {
        mainService.registerInvoiceOnMyList(invoiceID);
    }

    @Override
    public void onRegisterInvoiceOnMyListFailed() {
        Toast.makeText(this, "mylistfail", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterInvoiceOnMyListSuccess() {
        Intent intent = new Intent(this, OrderConfirmActivity.class);
        intent.putExtra("invoiceID", invoiceID);
        intent.putExtra("invoice", invoice);
        startActivityForResult(intent, ActionBarPresenter.REQUEST_CODE_TAB);
    }
}
