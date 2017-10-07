package net.flyingbags.flyingapps.service;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.Login2Presenter;
import net.flyingbags.flyingapps.presenter.LoginPresenter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * Created by ernest on 17. 10. 7.
 */

public class Login2Service implements Login2Presenter.presenter {


    private static final String TAG = LoginPresenter.class.getSimpleName();

    private Login2Presenter.view view;
    private FirebaseAuth mAuth;

    public Login2Service(Login2Presenter.view view){
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }
    //// TODO: 17. 10. 8 add user profile to database and casting view to activity
    public void createUser(String email, String password, String tempname){
        final String memail = email;
        final String mpassword = password;
        mAuth.createUserWithEmailAndPassword (email, password)
                .addOnCompleteListener((AppCompatActivity)view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {

                        }else{
                            signIn(memail, mpassword);
                        }
                    }
                });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((AppCompatActivity)view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            view.onSignInFailed();
                        }else {
                            view.showProfile();
                        }
                    }
                });
    }

    /*

    public void firstsignIn(String email, String password){
    }
    */
}
