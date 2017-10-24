package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.NewOrder;
import net.flyingbags.flyingapps.presenter.AdminPresenter;
import net.flyingbags.flyingapps.service.AdminService;

import java.util.Vector;

/**
 * Created by ernest on 17. 10. 24.
 */

public class AdminMainActivity extends AppCompatActivity implements AdminPresenter.view{
    AdminService mAdminService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminService = new AdminService(this);
        setContentView(R.layout.activity_admin_main);


    }

    @Override
    public void onGetNewOrdersSuccess(Vector<NewOrder> newOrders) {

    }

    @Override
    public void onGetNewOrdersFailed() {

    }

    @Override
    public void onSetInvoiceFailed() {

    }

    @Override
    public void onSetInvoiceSuccess() {

    }

    @Override
    public void onGetInvoiceSuccess(String invoice, Invoice presentInfo) {

    }

    @Override
    public void onGetInvoiceFailed() {

    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {

    }

    @Override
    public void onGetInvoicesVectorFailed() {

    }
}
