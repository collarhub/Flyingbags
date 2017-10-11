package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.LoginPresenter;
import net.flyingbags.flyingapps.service.LoginService;

/**
 * Created by User on 2017-10-07.
 * 두번째 activity 로그인 화면
 */

public class LoginActivity extends AppCompatActivity implements LoginPresenter.view {
    private ConstraintLayout constraintLayout;  // background layout(배경 touch시 editText focus out시키는 용도)
    private LoginService loginService;          // presenter
    private EditText editTextPasswd;            // passwd 입력 editText(완료 누르면 sign in 버튼 누른효과)
    private Button buttonSignIn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginService = new LoginService((AppCompatActivity) this);

        // 배경 클릭 이벤트
        constraintLayout = (ConstraintLayout) findViewById(R.id.login_layout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusOut();
            }
        });

        // passwd 입력시 완료 버튼 이벤트
        editTextPasswd = (EditText) this.findViewById(R.id.edit_passwd);
        editTextPasswd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    signIn();
                    return true;
                }
                return false;
            }
        });

        // 로그인 버튼 클릭 이벤트
        buttonSignIn = (Button) findViewById(R.id.button_signin);
        final EditText editTextEmail = (EditText) this.findViewById(R.id.edit_email);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginService.signIn(editTextEmail.getText().toString(), editTextPasswd.getText().toString());
            }
        });
    }

    @Override
    public void focusOut() {
        loginService.focusOut();
    }

    @Override
    public void signIn() {
        loginService.signIn();
    }

    @Override
    public void onSignInFailed() {
        ((LinearLayout) this.findViewById(R.id.login_linear_forget)).setVisibility(View.VISIBLE);
    }

    @Override
    public void onSignInSuccess() {
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onCreateUserFailed() {

    }

    @Override
    public void onCreateUserSuccess() {

    }
}