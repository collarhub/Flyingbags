package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.NewOrder;

import java.util.Vector;


public class AdminPresenter {
    public interface view {

        void onSetInvoiceFailed();
        void onSetInvoiceSuccess();

        void onGetInvoiceSuccess(String invoice, Invoice presentInfo);
        void onGetInvoiceFailed();

        void onGetInvoicesVectorSuccess(Vector<String> invoices);
        void onGetInvoicesVectorFailed();
    }

    public interface presenter {

        void setInvoice(String invoice, String field, Object content);

        void getInvoicesVector();
        void generateInvoice(String invoice);
        void setInvoice(final String invoice, final Invoice contents);

        void getInvoicesVector(String status);

        void getInvoice(final String invoice);
    }
}
