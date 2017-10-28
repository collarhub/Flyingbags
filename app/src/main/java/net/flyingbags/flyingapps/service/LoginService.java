package net.flyingbags.flyingapps.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Admin;
import net.flyingbags.flyingapps.model.NewOrder;
import net.flyingbags.flyingapps.presenter.LoginPresenter;
import net.flyingbags.flyingapps.view.MainActivity;

import java.util.Vector;

/**
 * Created by User on 2017-10-07.
 * Login 실제 presenter
 */

public class LoginService implements LoginPresenter.presenter {
    private static final String TAG = LoginService.class.getSimpleName();

    private LoginPresenter.view view;

    private FirebaseAuth mAuth;

    public LoginService(LoginPresenter.view view){
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void createUser(String email, String password){ // create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //task.getResult();
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onCreateUserFailed(); // create user failed
                        }else{
                            view.onCreateUserSuccess(); // create user success, login complete.
                        }
                    }
                });
    }

    @Override
    public void signIn(String email, String password){ // login
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            view.onSignInFailed(); // login failed
                        }else {
                            view.onSignInSuccess(); // login success
                        }
                    }
                });
    }

    @Override
    public void authAdmin(){
        FirebaseDatabase.getInstance().getReference().child("admins").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Admin mAdmin = dataSnapshot.getValue(Admin.class);
                        if (mAdmin != null) {
                            //view.onAuthAdminSuccess();
                        } else {
                            //view.onGetInvoiceFailed();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //view.onGetNewOrdersFailed();
                    }
                });
    }
}
