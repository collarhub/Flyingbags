package net.flyingbags.flyingapps.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.flyingbags.flyingapps.R;

public class ModifyPasswordActivity extends AppCompatActivity{
    private static final String TAG = ModifyPasswordActivity.class.getSimpleName();

    EditText editText_PrevPasswordContent;
    EditText editText_PasswordContent;
    EditText editText_ConfirmContent;
    Button button_Accepted;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        mContext = this;

        editText_PrevPasswordContent = (EditText) findViewById(R.id.editText_PrevPasswordContent);
        editText_PasswordContent = (EditText) findViewById(R.id.editText_PasswordContent);
        editText_ConfirmContent = (EditText) findViewById(R.id.editText_ConfirmContent);
        button_Accepted = (Button) findViewById(R.id.button_Accepted);

        button_Accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword(editText_PrevPasswordContent.getText().toString(), editText_PasswordContent.getText().toString(), editText_ConfirmContent.getText().toString());
            }
        });
    }

    private void changePassword(String prev, final String next, final String conf){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), prev);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");
                            changePasswordStep2(next, conf);
                        }else{
                            Toast.makeText(mContext, "Previous Password is WRONG.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void changePasswordStep2(String next, String conf){
        if(next.contentEquals(conf)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            user.updatePassword(next)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                                changeCompleted();
                            }else{
                                Toast.makeText(mContext, "Unexpected error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(mContext, "Confirm password is WRONG.", Toast.LENGTH_LONG).show();
        }
    }

    private void changeCompleted(){
        Toast.makeText(mContext, "Password Changed.", Toast.LENGTH_SHORT).show();
        this.onBackPressed();
    }
}
