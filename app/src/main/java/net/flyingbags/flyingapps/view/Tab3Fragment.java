package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OrderArrayAdapter;
import net.flyingbags.flyingapps.etc.OrderListItem;
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

public class Tab3Fragment extends Fragment implements MainPresenter.view {
    private ListView listView;
    private OrderArrayAdapter orderArrayAdapter;
    private ArrayList<OrderListItem> arrayList;
    private MainService mainService;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        mainService = new MainService(this);

        arrayList = new ArrayList<>();
        mainService.getInvoicesVector();

        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.item_order, arrayList);

        listView = (ListView) view.findViewById(R.id.list_order_inquery);
        listView.setAdapter(orderArrayAdapter);

        listView.addHeaderView(getActivity().getLayoutInflater().inflate(R.layout.header_order_confirm, null));

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
        arrayList.add(new OrderListItem(invoiceID, invoice.getOrderDate(), invoice.getPackageType(),
                invoice.getPrice(), invoice.getPrice(), invoice.getTarget(), invoice.getPrice()));
        orderArrayAdapter.notifyDataSetChanged();
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
