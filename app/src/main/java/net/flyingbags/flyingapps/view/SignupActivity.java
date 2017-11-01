package net.flyingbags.flyingapps.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.LoginPresenter;
import net.flyingbags.flyingapps.service.LoginService;

/**
 * Created by User on 2017-11-02.
 */

public class SignupActivity extends AppCompatActivity implements LoginPresenter.view {
    private LoginService loginService;
    private Button buttonSignUp;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirm;
    private TextView textViewMessage;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        loginService = new LoginService(this);

        textViewMessage = (TextView) findViewById(R.id.textView_signup_message);
        editTextEmail = (EditText) findViewById(R.id.editText_signup_email);
        editTextPassword = (EditText) findViewById(R.id.editText_signup_password);
        editTextConfirm = (EditText) findViewById(R.id.editText_signup_confirm);
        buttonSignUp = (Button) findViewById(R.id.button_signup);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signupCheck(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextConfirm.getText().toString())) {
                    loginService.createUser(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                    progressDialog = new ProgressDialog(SignupActivity.this, R.style.AppCompatAlertDialogStyle);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progressbar_spin);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (progressDialog.isShowing()) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                builder.setMessage("Please check your network environment.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    progressDialog.dismiss();
                                                } catch (Exception e) {
                                                }
                                            }
                                        })
                                        .setCancelable(false)
                                        .create()
                                        .show();
                            }
                        }
                    }, 10000);
                }
            }
        });
    }

    private boolean signupCheck(String email, String password, String confirm) {
        if(email.equals("") || password.equals("") || confirm.equals("")) {
            if(email.equals("")) {
                textViewMessage.setText("fill out email");
                return false;
            }
            if(password.equals("")) {
                textViewMessage.setText("fill out password");
                return false;
            }
            if(confirm.equals("")) {
                textViewMessage.setText("fill out confirm");
                return false;
            }
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textViewMessage.setText("check email format");
            return false;
        }
        else if(!password.equals(confirm)) {
            textViewMessage.setText("check your password");
            return false;
        }
        else if(password.length() < 6) {
            textViewMessage.setText("password should be at least 6 characters");
            return false;
        }
        return true;
    }

    @Override
    public void onSignInFailed() {

    }

    @Override
    public void onSignInSuccess() {

    }

    @Override
    public void onCreateUserFailed(String errorMessage) {
        textViewMessage.setText("sign up failed\n" + errorMessage);
        progressDialog.dismiss();
    }

    @Override
    public void onCreateUserSuccess() {
        progressDialog.dismiss();
        finish();
    }

    @Override
    public void onAuthAdminSuccess() {

    }
}
