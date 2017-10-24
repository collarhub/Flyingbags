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

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.LoginPresenter;
import net.flyingbags.flyingapps.view.MainActivity;

/**
 * Created by User on 2017-10-07.
 * Login 실제 presenter
 */

public class LoginService implements LoginPresenter.presenter {
    private static final String TAG = LoginService.class.getSimpleName();

    private AppCompatActivity activity;
    private LoginPresenter.view view;

    private FirebaseAuth mAuth;

    public LoginService(AppCompatActivity activity) {
        this.activity = activity;
        this.view = (LoginPresenter.view) activity;
        mAuth = FirebaseAuth.getInstance();
    }

    public LoginService(LoginPresenter.view view){
        this.view = view;
        this.activity = (AppCompatActivity) view;
        mAuth = FirebaseAuth.getInstance();
    }

    // 배경 터치시 editText focus out시킴
    @Override
    public void focusOut() {
        EditText editTextEmail = (EditText) activity.findViewById(R.id.edit_email);
        EditText editTextPasswd = (EditText) activity.findViewById(R.id.edit_passwd);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editTextEmail.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(editTextPasswd.getWindowToken(), 0);
    }

    // 로그인 체크 하는 부분
    @Override
    public void signIn() {
        boolean signinChk = false;
        EditText editTextEmail = (EditText) activity.findViewById(R.id.edit_email);
        EditText editTextPasswd = (EditText) activity.findViewById(R.id.edit_passwd);

        FirebaseAuth.getInstance().signOut();

        ///// 이 부분에서 인증 확인(변경해야함)
        if(editTextEmail.getText().toString().equals("a"))
            signinChk = true;
        /////

        // 인증 되면 다음 activity
        if(signinChk) {
            activity.startActivity(new Intent(activity, MainActivity.class));
            activity.finish();
        } else {    // 인증 안되면 forget passwd 메시지 띄움
            ((LinearLayout) activity.findViewById(R.id.login_linear_forget)).setVisibility(View.VISIBLE);
        }
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
}
