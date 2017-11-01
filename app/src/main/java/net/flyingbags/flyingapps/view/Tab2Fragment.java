package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.StateArrayAdapter;
import net.flyingbags.flyingapps.etc.StateListItem;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.service.MainService;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by User on 2017-10-09.
 */

public class Tab2Fragment extends Fragment implements MainPresenter.view {
    private ListView listView;
    private StateArrayAdapter stateArrayAdapter;
    private ArrayList<StateListItem> arrayList;
    private MainService mainService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        mainService = new MainService(this);

        arrayList = new ArrayList<>();

        stateArrayAdapter = new StateArrayAdapter(getActivity(), R.layout.item_status, arrayList);

        listView = (ListView) view.findViewById(R.id.list_state);
        listView.setAdapter(stateArrayAdapter);

        mainService.getInvoicesVector();

        return view;
    }

    @Override
    public void onGetInvoicesVectorFailed() {

    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {
        for(String invoiceID : invoices) {
            mainService.getInvoice(invoiceID);
        }
    }

    @Override
    public void onGetInvoiceSuccess(String invoiceID, Invoice invoice) {
        arrayList.add(new StateListItem(invoiceID, invoice.getStatus()));
        stateArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetInvoiceFailed() {

    }

    @Override
    public void onRegisterInvoiceOnNewOrderFailed() {

    }

    @Override
    public void onRegisterInvoiceOnNewOrderSuccess() {

    }

    @Override
    public void onRegisterInvoiceOnMyListFailed() {

    }

    @Override
    public void onRegisterInvoiceOnMyListSuccess() {

    }

    @Override
    public void onRegisterInvoiceSuccess() {

    }

    @Override
    public void onRegisterInvoiceFailed() {

    }
}
