package net.flyingbags.flyingapps.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.model.Admin;
import net.flyingbags.flyingapps.presenter.LoginPresenter;

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
                            view.onCreateUserFailed(task.getException().getMessage()); // create user failed
                        }else{
                            view.onCreateUserSuccess(); // create user success, login complete.
                        }
                    }
                });
    }

    @Override
    public void createUser(String email, String password, final String name, final String address){ // create user
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //task.getResult();
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            view.onCreateUserFailed(task.getException().getMessage()); // create user failed
                        }else{
                            view.onCreateUserSuccess(); // create user success, login complete.
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Log.d(TAG, "User profile updated. (name)");
                                    }
                                }
                            });

                            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").setValue(address)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "registerInvoiceOnMyList:onComplete:" + task.isSuccessful());
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated. (address)");
                                            }
                                        }
                                    });
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
                            authAdmin();
                            //view.onSignInSuccess(); // login success
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
                            view.onAuthAdminSuccess();
                        } else {
                            view.onSignInSuccess();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onSignInSuccess();
                    }
                });
    }
}
