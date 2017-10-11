package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

/**
 * Created by User on 2017-10-10.
 */

public class AdminPresenter {
    public interface view {
        void onGenerateInvoiceFailed();
        void onGenerateInvoiceSuccess();
    }

    public interface presenter {
    }
}
