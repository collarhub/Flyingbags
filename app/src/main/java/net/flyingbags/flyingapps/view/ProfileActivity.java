package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.ProfilePresenter;

/**
 * Created by ernest on 17. 10. 8.
 * vun ri joong (not seperated, on progress)
 */

public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.view {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = ProfileActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView textView_testname = (TextView) findViewById(R.id.textView_testname);
        Button button_logout = (Button) findViewById(R.id.button_logout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user != null){
            textView_testname.setText("Email : "+user.getEmail());
        }else{
            textView_testname.setText("Email : null");
        }

        button_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signOut();
            }
        });
    }

    private void displayProfile(){

    }
    private void signOut() {
        mAuth.signOut();
        onBackPressed();
    }

}
