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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.CustomPlaceAutocompleteFragment;
import net.flyingbags.flyingapps.etc.OneDayDecorator;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.ScheduleDeliveryPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.ScheduleDeliveryService;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private TextView textViewPackageType;
    private CustomPlaceAutocompleteFragment placeAutocompleteFragment1;
    private EditText editTextAddressTo;
    private CustomPlaceAutocompleteFragment placeAutocompleteFragment2;
    private EditText editTextAddressFrom;
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
        imageButtonHome = (ImageButton) viewActionBar.findViewById(R.id.back_button);
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

        placeAutocompleteFragment1 = (CustomPlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);

        editTextAddressTo = (EditText)placeAutocompleteFragment1.getView().findViewById(R.id.place_autocomplete_search_input);
        editTextAddressTo.setTextSize(15.0f);
        editTextAddressTo.setText("");
        editTextAddressTo.setHint("To");

        placeAutocompleteFragment2 = (CustomPlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);

        editTextAddressFrom = (EditText)placeAutocompleteFragment2.getView().findViewById(R.id.place_autocomplete_search_input);
        editTextAddressFrom.setTextSize(15.0f);
        editTextAddressFrom.setText(invoice.getDeparture());
        editTextAddressFrom.setHint("From");


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
        if(checkedRadioButtonId == -1) {
            Toast.makeText(this, "choose \"Deliver type\".", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton radioButton = (RadioButton) findViewById(checkedRadioButtonId);
        String deliveryType = radioButton.getText().toString();

        EditText editTextAddressToDetail = (EditText)findViewById(R.id.editText_address_to_detail);
        String target = editTextAddressTo.getText().toString() + ", " + editTextAddressToDetail.getText().toString();
        String departure = editTextAddressFrom.getText().toString();
        if(editTextAddressTo.getText().toString().equals("") || departure.equals("")) {
            Toast.makeText(this, "fill out \"Destination\".", Toast.LENGTH_SHORT).show();
            return;
        }

        MaterialCalendarView materialCalendarView = (MaterialCalendarView)findViewById(R.id.calendarView);
        List<CalendarDay> selectedDates = materialCalendarView.getSelectedDates();
        if(selectedDates.size() == 0) {
            Toast.makeText(this, "choose \"Date for Delivery\".", Toast.LENGTH_SHORT).show();
            return;
        }
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
        invoice.setRoute(null);

        Intent intent = new Intent(this, OrderConfirmActivity.class);
        intent.putExtra("invoiceID", invoiceID);
        intent.putExtra("invoice", invoice);
        startActivityForResult(intent, ActionBarPresenter.REQUEST_CODE_TAB);
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
}
