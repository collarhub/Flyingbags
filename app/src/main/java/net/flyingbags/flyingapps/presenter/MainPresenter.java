package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.Route;

import java.util.Vector;

/**
 * Created by User on 2017-10-10.
 */

public class MainPresenter {
    public static final int REQUEST_CODE_TAB = 1001;
    public static final int RESULT_HOME = 1002;
    public static final int RESULT_PROFILE = 1003;
    public interface view {
        void onGetInvoicesVectorFailed();
        void onGetInvoicesVectorSuccess(Vector<String> invoices);

        void onGetInvoiceSuccess(String invoiceID, Invoice invoice);
        void onGetInvoiceFailed();

        void onRegisterInvoiceOnNewOrderFailed();
        void onRegisterInvoiceOnNewOrderSuccess();

        void onRegisterInvoiceOnMyListFailed();
        void onRegisterInvoiceOnMyListSuccess();

        void onRegisterInvoiceSuccess();

        void onRegisterInvoiceFailed();
    }

    public interface presenter {
        void registerInvoiceOnInvoices(final String invoice, Invoice contents);
        void registerInvoiceOnMyList(final String invoice);
        void getInvoicesVector();
        void getInvoice(final String invoice);
        void registerInvoice(final String invoice, Invoice contents, Route route);
    }
}
