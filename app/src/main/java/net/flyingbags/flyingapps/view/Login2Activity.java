package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.flyingbags.flyingapps.presenter.Login2Presenter;
import net.flyingbags.flyingapps.service.Login2Service;

import net.flyingbags.flyingapps.R;


public class Login2Activity extends AppCompatActivity implements Login2Presenter.view{

    private static final String TAG = Login2Activity.class.getSimpleName();

    private Login2Service loginPresenter = new Login2Service(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        final EditText editText_id = (EditText) findViewById(R.id.editText_id);
        final EditText editText_password = (EditText) findViewById(R.id.editText_id);
        final EditText editText_testname =  (EditText) findViewById(R.id.editText_testname);

        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_newAccount = (Button) findViewById(R.id.button_newAccount);

        button_newAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginPresenter.createUser(editText_id.getText().toString(), editText_password.getText().toString(), editText_testname.getText().toString());
            }
        });

        button_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loginPresenter.signIn(editText_id.getText().toString(), editText_password.getText().toString());
            }
        });
    }

    @Override
    public void onCreateUser() {

    }

    @Override
    public void onCreateUserFailed() {
        Toast.makeText(Login2Activity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignIn() {

    }

    @Override
    public void onSignOut() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreateUserSuccess() {
    }

    @Override
    public void onSignInSuccess() {
    }

    @Override
    public void onSignInFailed() {
        Toast.makeText(Login2Activity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
    }
}
