package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OrderArrayAdapter;
import net.flyingbags.flyingapps.etc.OrderListItem;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.Route;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.MainService;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by User on 2017-10-18.
 */

public class OrderConfirmActivity extends AppCompatActivity implements ActionBarPresenter.view, MainPresenter.view {
    private ActionBarService actionBarService;
    private View viewActionBar;
    private ImageButton imageButtonHome;
    private ImageButton imageButtonProfile;
    private ListView listView;
    private OrderArrayAdapter orderArrayAdapter;
    private ArrayList<OrderListItem> arrayList;
    private Button buttonToCommit;
    private String invoiceID;
    private Invoice invoice;
    private MainService mainService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        invoiceID = getIntent().getStringExtra("invoiceID");
        invoice = (Invoice) getIntent().getSerializableExtra("invoice");

        actionBarService = new ActionBarService(this, "My Order");
        mainService = new MainService(this);

        showActionBar();

        viewActionBar = getSupportActionBar().getCustomView();
        imageButtonHome = (ImageButton) viewActionBar.findViewById(R.id.home_button);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ActionBarPresenter.RESULT_HOME, null);
                finish();
            }
        });

        imageButtonProfile = (ImageButton) viewActionBar.findViewById(R.id.profile_button);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ActionBarPresenter.RESULT_PROFILE, null);
                finish();
            }
        });

        arrayList = new ArrayList<>();
        arrayList.add(new OrderListItem(invoiceID, invoice.getOrderDate(),
                invoice.getPackageType(),
                invoice.getPrice(),
                invoice.getPrice(), invoice.getTarget(),
                invoice.getPrice()));

        orderArrayAdapter = new OrderArrayAdapter(this, R.layout.item_order, arrayList);

        listView = (ListView) findViewById(R.id.list_order);
        listView.setAdapter(orderArrayAdapter);

        listView.addHeaderView(getLayoutInflater().inflate(R.layout.header_order_confirm, null));
        listView.addFooterView(getLayoutInflater().inflate(R.layout.footer_order_confirm, null));

        buttonToCommit = (Button) findViewById(R.id.button_commit);
        buttonToCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoice.setStatus("ready");
                //mainService.registerInvoiceOnInvoices(invoiceID, invoice);
                mainService.registerInvoice(invoiceID, invoice, new Route(invoice.getStatus(), invoice.getOrderDate()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ActionBarPresenter.REQUEST_CODE_TAB) {
            if(resultCode == ActionBarPresenter.RESULT_HOME) {
                setResult(ActionBarPresenter.RESULT_HOME, null);
                finish();
            }
            else if(resultCode == ActionBarPresenter.RESULT_PROFILE) {
                setResult(ActionBarPresenter.RESULT_PROFILE, null);
                finish();
            }
        }
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

    }

    @Override
    public void onRegisterInvoiceOnNewOrderSuccess() {
        mainService.registerInvoiceOnMyList(invoiceID);
    }

    @Override
    public void onRegisterInvoiceOnMyListFailed() {

    }

    @Override
    public void onRegisterInvoiceOnMyListSuccess() {
        startActivityForResult(new Intent(this, OrderCommitActivity.class), ActionBarPresenter.REQUEST_CODE_TAB);
    }

    @Override
    public void onRegisterInvoiceSuccess() {
        startActivityForResult(new Intent(this, OrderCommitActivity.class), ActionBarPresenter.REQUEST_CODE_TAB);
    }

    @Override
    public void onRegisterInvoiceFailed() {

    }
}
