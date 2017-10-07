package com.example.user.flyingbags.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.user.flyingbags.R;
import com.example.user.flyingbags.presenter.LoginPresenter;
import com.example.user.flyingbags.view.MainActivity;

/**
 * Created by User on 2017-10-07.
 * Login 실제 presenter
 */

public class LoginService implements LoginPresenter.presenter {

    // 배경 터치시 editText focus out시킴
    @Override
    public void focusOut(Activity activity) {
        EditText editTextEmail = (EditText) activity.findViewById(R.id.edit_email);
        EditText editTextPasswd = (EditText) activity.findViewById(R.id.edit_passwd);
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editTextEmail.getWindowToken(), 0);
        inputMethodManager.hideSoftInputFromWindow(editTextPasswd.getWindowToken(), 0);
    }

    // 로그인 체크 하는 부분
    @Override
    public void signIn(Activity activity) {
        boolean signinChk = false;
        EditText editTextEmail = (EditText) activity.findViewById(R.id.edit_email);
        EditText editTextPasswd = (EditText) activity.findViewById(R.id.edit_passwd);


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
}
