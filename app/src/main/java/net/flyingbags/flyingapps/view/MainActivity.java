package net.flyingbags.flyingapps.view;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.presenter.NavTabPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;
import net.flyingbags.flyingapps.service.MainService;
import net.flyingbags.flyingapps.service.NavTabService;
import net.flyingbags.flyingapps.service.Tab0Service;

import java.util.Vector;

/**
 * Created by User on 2017-10-07.
 *
 */

public class MainActivity extends AppCompatActivity implements ActionBarPresenter.view, NavTabPresenter.view, MainPresenter.view{

    private ActionBarService actionBarService;
    private View viewActionBar;
    private ImageButton imageButtonHome;
    private ImageButton imageButtonProfile;
    private NavTabService navTabService;
    private TabHost tabHost;
    private MainService mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBarService = new ActionBarService(this, "My Location");
        showActionBar();

        viewActionBar = getSupportActionBar().getCustomView();
        imageButtonHome = (ImageButton) viewActionBar.findViewById(R.id.home_button);
        imageButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHome();
            }
        });

        imageButtonProfile = (ImageButton) viewActionBar.findViewById(R.id.profile_button);
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile();
            }
        });

        navTabService = new NavTabService(this);
        showNavTab();

        tabHost = (TabHost) findViewById(R.id.tab_host);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                MainActivity.this.onTabChanged(tabId);
            }
        });
        mainService = new MainService(this);
    }

    @Override
    public void showActionBar() {
        actionBarService.showActionBar();
    }

    @Override
    public void showNavTab() {
        navTabService.showNavTab();
    }

    @Override
    public void setTitle(String title) {
        actionBarService.setTitle(title);
    }

    @Override
    public void showHome() {
        navTabService.showHome();
    }

    @Override
    public void showProfile() {
        navTabService.showProfile();
    }

    private void onTabChanged(String tabId) {
        switch (tabId) {
            case "TAB0":
                setTitle("My Location");
                break;
            case "TAB1":
                setTitle("Notifications");
                break;
            case "TAB2":
                setTitle("My Delivery");
                break;
            case "TAB3":
                setTitle("My Order");
                break;
            case "TAB4":
                setTitle("Settings");
                break;
            case "TAB5":
                setTitle("My Profile");
                break;
        }
    }

    private void verifyQR(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result.getContents() == null) {
            onGetInvoiceFailed();
        }
        else {
            mainService.getInvoice(result.getContents());
        }
        //mainService.getInvoice("9999000004");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MainPresenter.REQUEST_CODE_TAB) {
            if(resultCode == MainPresenter.RESULT_HOME) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showHome();
                    }
                }, 0);
            }
            else if(resultCode == MainPresenter.RESULT_PROFILE) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showProfile();
                    }
                }, 0);
            }
        }
        else {
            verifyQR(requestCode, resultCode, data);
        }
    }

    @Override
    public void onGetInvoicesVectorFailed() {

    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {

    }

    @Override
    public void onGetInvoiceSuccess(String invoiceID, Invoice invoice) {
        Intent intent = new Intent(this, ScheduleDeliveryActivity.class);
        intent.putExtra("invoiceID", invoiceID);
        intent.putExtra("invoice", invoice);
        startActivityForResult(intent, ActionBarPresenter.REQUEST_CODE_TAB);
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
}
