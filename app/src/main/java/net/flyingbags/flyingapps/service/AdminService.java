package net.flyingbags.flyingapps.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.NewOrder;
import net.flyingbags.flyingapps.presenter.AdminPresenter;

import java.util.Vector;

public class AdminService implements AdminPresenter.presenter {
    private static final String TAG = AdminService.class.getSimpleName();
    private AdminPresenter.view view;

    public AdminService(AdminPresenter.view view) {
        this.view = view;
    }

    // you can make "invoice code" by this method
    @Override
    public void generateInvoice(String invoice){
        setInvoice(invoice, new Invoice("","","","","","","","undistributed","",""));
    }

    // admin can get new orders that need to deliver.
    @Override
    public void getNewOrders(){
        FirebaseDatabase.getInstance().getReference().child("newOrders").orderByChild("status").equalTo("ready")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Vector<NewOrder> NewOrders = new Vector<NewOrder>();
                        for(DataSnapshot newOrdersSnapShot : dataSnapshot.getChildren()){
                            NewOrders.add(newOrdersSnapShot.getValue(NewOrder.class));
                        }
                        view.onGetNewOrdersSuccess(NewOrders);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onGetNewOrdersFailed();
                    }
                });
    }

    // all orders must be refreshed by admin with this method.
    @Override
    public void setInvoice(final String invoice, final Invoice contents){
        FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Invoice presentInfo = dataSnapshot.getValue(Invoice.class);
                        if(contents.getStatus() == null){
                            contents.setStatus(presentInfo.getStatus());
                        }
                        if(contents.getLocation() == null){
                            contents.setLocation(presentInfo.getLocation());
                        }
                        if(contents.getTarget() == null){
                            contents.setTarget(presentInfo.getTarget());
                        }
                        FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice).setValue(contents)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "setInvoice:onComplete:" + task.isSuccessful());
                                        if (!task.isSuccessful()) {
                                            view.onSetInvoiceFailed(); // create invoice failed
                                        }else{
                                            view.onSetInvoiceSuccess(); // create invoice success
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onSetInvoiceFailed(); // create invoice failed
                    }
                });
    }

    // get invoices array
    // // TODO: 17. 10. 16 if there is too many invoices, this method could makes error or overhead.
    @Override
    public void getInvoicesVector(){
        FirebaseDatabase.getInstance().getReference().child("invoices")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Vector<String> Invoices = new Vector<String>();
                        for(DataSnapshot newOrdersSnapShot : dataSnapshot.getChildren()){
                            Invoices.add(newOrdersSnapShot.getValue(String.class));
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
        FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Invoice presentInfo = dataSnapshot.getValue(Invoice.class);
                        view.onGetInvoiceSuccess(invoice, presentInfo);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onGetInvoiceFailed();
                    }
                });
    }
}
