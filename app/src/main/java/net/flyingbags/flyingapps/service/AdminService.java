package net.flyingbags.flyingapps.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.flyingbags.flyingapps.presenter.AdminPresenter;

public class AdminService implements AdminPresenter.presenter {
    private static final String TAG = AdminService.class.getSimpleName();
    private AdminPresenter.view view;
    private DatabaseReference mDatabase;

    public AdminService(AdminPresenter.view view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void generateInvoice(String s){
        mDatabase.child("notUsedInvoice").child(s).setValue("undistributed")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "generateInvoice:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onGenerateInvoiceFailed(); // create invoice failed
                        }else{
                            view.onGenerateInvoiceSuccess(); // create invoice success
                        }
                    }
                });
    }
}
