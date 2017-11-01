package net.flyingbags.flyingapps.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.AdminPresenter;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.service.AdminService;

import java.util.Vector;

public class AdminInvoiceListActivity extends AppCompatActivity implements AdminPresenter.view{
    AdminService mAdminService;
    Vector<String> mInvoicesVector;
    ArrayAdapter mAdapter;
    ListView mListView;

    Spinner mSpinner;
    ArrayAdapter spinnerAdapter;

    Button qrbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminService = new AdminService(this);
        setContentView(R.layout.activity_admin_invoice_list);
        mListView = (ListView) findViewById(R.id.ListView_invoice);
        mSpinner = (Spinner) findViewById(R.id.spinner_category);

        final Vector<String> categoryVector = new Vector<String>();
        categoryVector.add("");
        categoryVector.add("undistributed");
        categoryVector.add("idle");
        categoryVector.add("ready");
        categoryVector.add("delivering");
        categoryVector.add("confirmed");
        categoryVector.add("arrived");

        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categoryVector);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(categoryVector.elementAt(i).length()!=0) {
                    mAdminService.getInvoicesVector(categoryVector.elementAt(i));
                }else{
                    mAdminService.getInvoicesVector();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mAdminService.getInvoicesVector();
            }
        });

        qrbutton = (Button) findViewById(R.id.button_qrscan);
        qrbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                scanQR();
            }
        });

        mAdminService.getInvoicesVector();

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
        mInvoicesVector = invoices;
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mInvoicesVector);
        mListView.setAdapter(mAdapter);

        final Context mContext = this;

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                String invoice = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(mContext, AdminInvoiceDetailActivity.class);
                intent.putExtra("invoice", invoice);
                startActivity(intent);
            }
        });
        Toast.makeText(this, "onGetInvoicesVectorSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetInvoicesVectorFailed() {
        Toast.makeText(this, "onGetInvoicesVectorFailed", Toast.LENGTH_SHORT).show();
    }

    private void scanQR() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.addExtra("PROMPT_MESSAGE", "Scan QR Code within frame");
        integrator.setResultDisplayDuration(1000);
        integrator.initiateScan();
    }

    private void verifyQR(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result.getContents() == null) {
            onGetInvoiceFailed();
        }
        else {
            Intent intent = new Intent(this, AdminInvoiceDetailActivity.class);
            intent.putExtra("invoice", result.getContents());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MainPresenter.REQUEST_CODE_TAB) {
            if(resultCode == MainPresenter.RESULT_HOME) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //showHome();
                    }
                }, 0);
            }
            else if(resultCode == MainPresenter.RESULT_PROFILE) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //showProfile();
                    }
                }, 0);
            }
        }
        else {
            verifyQR(requestCode, resultCode, data);
        }
    }


}
