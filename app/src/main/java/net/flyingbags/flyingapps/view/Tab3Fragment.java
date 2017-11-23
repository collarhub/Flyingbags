package net.flyingbags.flyingapps.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.OrderArrayAdapter;
import net.flyingbags.flyingapps.etc.OrderListItem;
import net.flyingbags.flyingapps.etc.StateArrayAdapter;
import net.flyingbags.flyingapps.etc.StateListItem;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.service.MainService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by User on 2017-10-09.
 */

public class Tab3Fragment extends Fragment implements MainPresenter.view {
    private ListView listView;
    private OrderArrayAdapter orderArrayAdapter;
    private ArrayList<OrderListItem> arrayList;
    private MainService mainService;
    private ProgressDialog progressDialog;
    private boolean validResultCheck = true;
    private View viewHeader;
    private View viewFooter;
    private RadioGroup radioGroupMyOrder;
    private int checkedRadioButtonId;
    private RadioButton checkedRadioButton;
    private String checkedRadioString;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        mainService = new MainService(this);

        arrayList = new ArrayList<>();

        orderArrayAdapter = new OrderArrayAdapter(getActivity(), R.layout.item_order, arrayList);

        listView = (ListView) view.findViewById(R.id.list_order_inquery);
        listView.setAdapter(orderArrayAdapter);

        viewHeader = getActivity().getLayoutInflater().inflate(R.layout.header_order_confirm, null);
        radioGroupMyOrder = (RadioGroup)viewHeader.findViewById(R.id.radioGroup_my_order);
        checkedRadioButtonId = radioGroupMyOrder.getCheckedRadioButtonId();
        checkedRadioButton = (RadioButton)viewHeader.findViewById(checkedRadioButtonId);
        checkedRadioString = checkedRadioButton.getText().toString();
        radioGroupMyOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                checkedRadioButton = (RadioButton)viewHeader.findViewById(checkedId);
                checkedRadioString = checkedRadioButton.getText().toString();
                arrayList.clear();
                if(viewFooter != null) {
                    listView.removeFooterView(viewFooter);
                }
                orderArrayAdapter.notifyDataSetChanged();
                mainService.getInvoicesVector();
                progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progressbar_spin);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog.isShowing()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Please check your network environment.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                progressDialog.dismiss();
                                                validResultCheck = false;
                                                onGetInvoicesVectorFailed();
                                            } catch (Exception e) {
                                            }
                                        }
                                    })
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        }
                    }
                }, 20000);
            }
        });

        listView.addHeaderView(viewHeader);

        mainService.getInvoicesVector();
        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progressbar_spin);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Please check your network environment.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        progressDialog.dismiss();
                                        validResultCheck = false;
                                        onGetInvoicesVectorFailed();
                                    } catch (Exception e) {
                                    }
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
            }
        }, 20000);

        return view;
    }

    @Override
    public void onGetInvoicesVectorFailed() {
        viewFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_my_order, null);
        ((TextView)viewFooter.findViewById(R.id.textView_my_order_message)).setText("Data request error.");
        listView.addFooterView(viewFooter);
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {
        if(validResultCheck) {
            for (String invoiceID : invoices) {
                mainService.getInvoice(invoiceID);
            }
            if (invoices.size() == 0) {
                viewFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_my_order, null);
                ((TextView) viewFooter.findViewById(R.id.textView_my_order_message)).setText("No data found.");
                listView.addFooterView(viewFooter);
            }
        }
        else {
            validResultCheck = true;
        }
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onGetInvoiceSuccess(String invoiceID, Invoice invoice) {
        if(validResultCheck) {
            if(checkedRadioString.equals("History")) {
                arrayList.add(new OrderListItem(invoiceID, invoice.getOrderDate(), invoice.getPackageType(),
                        invoice.getPrice(), invoice.getPrice(), invoice.getTarget(), invoice.getPrice()));
                orderArrayAdapter.notifyDataSetChanged();
            }
            else if(checkedRadioString.equals("Recent")) {
                if(arrayList.size() == 0) {
                    arrayList.add(new OrderListItem(invoiceID, invoice.getOrderDate(), invoice.getPackageType(),
                            invoice.getPrice(), invoice.getPrice(), invoice.getTarget(), invoice.getPrice()));
                    orderArrayAdapter.notifyDataSetChanged();
                }
                else {
                    OrderListItem orderListItem = arrayList.get(0);
                    Date dateA = null;
                    Date dateB = null;
                    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
                    try {
                        dateA = dateFormat.parse(orderListItem.getOrderDate());
                        dateB = dateFormat.parse(invoice.getOrderDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(dateB.getTime() - dateA.getTime() >= 0) {
                        arrayList.clear();
                        arrayList.add(new OrderListItem(invoiceID, invoice.getOrderDate(), invoice.getPackageType(),
                                invoice.getPrice(), invoice.getPrice(), invoice.getTarget(), invoice.getPrice()));
                        orderArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
        else {
            validResultCheck = true;
        }
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onGetInvoiceFailed() {
        viewFooter = getActivity().getLayoutInflater().inflate(R.layout.footer_my_order, null);
        ((TextView)viewFooter.findViewById(R.id.textView_my_order_message)).setText("Data request error.");
        listView.addFooterView(viewFooter);
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
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
