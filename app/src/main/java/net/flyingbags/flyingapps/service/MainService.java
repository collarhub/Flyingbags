package net.flyingbags.flyingapps.service;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.NewOrder;
import net.flyingbags.flyingapps.model.Route;
import net.flyingbags.flyingapps.presenter.MainPresenter;
import net.flyingbags.flyingapps.view.ScheduleDeliveryActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

public class MainService implements MainPresenter.presenter {
    private static final String TAG = MainService.class.getSimpleName();
    private MainPresenter.view view;

    public MainService(MainPresenter.view view) {
        this.view = view;
    }

    // make new order by user
    @Override
    public void registerInvoiceOnInvoices(final String invoice, Invoice contents){
        contents.setStatus("ready");
        FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice).setValue(contents)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "registerInvoiceOnNewOrder:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onRegisterInvoiceOnNewOrderFailed(); // failed
                        }else{
                            view.onRegisterInvoiceOnNewOrderSuccess(); // success
                        }
                    }
                });
    }

    // order registered on user's list by user
    @Override
    public void registerInvoiceOnMyList(final String invoice){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("invoices").child(invoice).setValue("ready")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "registerInvoiceOnMyList:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onRegisterInvoiceOnMyListFailed(); // failed
                        }else{
                            view.onRegisterInvoiceOnMyListSuccess(); // success
                        }
                    }
                });
    }

    @Override
    public void registerInvoice(final String invoice, Invoice contents, Route route){
        contents.setStatus("ready");
        contents.addRoute(route);
        Map<String, Object> childUpdates = new HashMap<>();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        childUpdates.put("/invoices/"+invoice,contents);
        childUpdates.put("/users/"+uid+"/invoices/"+invoice,"ready");

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "registerInvoiceOnMyList:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onRegisterInvoiceFailed(); // failed
                        }else{
                            view.onRegisterInvoiceSuccess(); // success
                        }
                    }
        });

    }


    // get invoices array
    @Override
    public void getInvoicesVector(){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("invoices")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Vector<String> Invoices = new Vector<String>();
                        for(DataSnapshot newOrdersSnapShot : dataSnapshot.getChildren()){
                            Invoices.add(newOrdersSnapShot.getKey());
                        }
                        view.onGetInvoicesVectorSuccess(Invoices);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onGetInvoicesVectorFailed();
                    }
                });
    }

    // get invoice data
    @Override
    public void getInvoice(final String invoice){
        if(!Pattern.matches("^[0-9]{10}",invoice)){
            view.onGetInvoiceFailed();
        }else{
            FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Invoice presentInfo = dataSnapshot.getValue(Invoice.class);
                            if (presentInfo != null) {
                                view.onGetInvoiceSuccess(invoice, presentInfo);
                            } else {
                                view.onGetInvoiceFailed();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            view.onGetInvoiceFailed();
                        }
                    });
        }
    }
}
