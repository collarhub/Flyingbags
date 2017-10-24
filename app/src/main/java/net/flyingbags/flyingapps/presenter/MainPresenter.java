package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

import net.flyingbags.flyingapps.model.Invoice;

import java.util.Vector;

/**
 * Created by User on 2017-10-10.
 */

public class MainPresenter {
    public static final int REQUEST_CODE_TAB = 1001;
    public static final int RESULT_HOME = 1002;
    public static final int RESULT_PROFILE = 1003;
    public interface view {
        void verifyQR(int requestCode, int resultCode, Intent data);

        void onGetInvoicesVectorFailed();
        void onGetInvoicesVectorSuccess(Vector<String> invoices);

        void onGetInvoiceSuccess(String invoice, Invoice presentInfo);
        void onGetInvoiceFailed();

        void onRegisterInvoiceOnNewOrderFailed();
        void onRegisterInvoiceOnNewOrderSuccess();

        void onRegisterInvoiceOnMyListFailed();
        void onRegisterInvoiceOnMyListSuccess();
    }

    public interface presenter {
        String verifyQR(int requestCode, int resultCode, Intent data);
        void registerInvoiceOnNewOrder(final String invoice, String location, String target);
        void registerInvoiceOnMyList(final String invoice, String location, String target);
        void getInvoicesVector();
        void getInvoice(final String invoice);
    }
}
