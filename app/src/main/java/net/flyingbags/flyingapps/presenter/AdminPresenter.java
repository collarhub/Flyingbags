package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.NewOrder;

import java.util.Vector;

/**
 * Created by User on 2017-10-10.
 */

public class AdminPresenter {
    public interface view {

        void onGetNewOrdersSuccess(Vector<NewOrder> newOrders);
        void onGetNewOrdersFailed();

        void onSetInvoiceFailed();
        void onSetInvoiceSuccess();

        void onGetInvoiceSuccess(String invoice, Invoice presentInfo);
        void onGetInvoiceFailed();

        void onGetInvoicesVectorSuccess(Vector<String> invoices);
        void onGetInvoicesVectorFailed();
    }

    public interface presenter {
        void getInvoicesVector();
        void generateInvoice(String invoice);
        public void getNewOrders();
        void setInvoice(final String invoice, final Invoice contents);
        void getInvoice(final String invoice);
    }
}
