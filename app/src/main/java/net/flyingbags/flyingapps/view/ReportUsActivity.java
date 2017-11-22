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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import net.flyingbags.flyingapps.R;

import java.util.HashMap;
import java.util.Map;

public class ReportUsActivity extends AppCompatActivity{
    private static final String TAG = ReportUsActivity.class.getSimpleName();

    TextView textView_EmailTitle;
    EditText editText_Email;
    TextView textView_ContentsTitle;
    EditText editText_Contents;
    Button button_send;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportus);

        mContext = this;

        editText_Email = (EditText) findViewById(R.id.editText_Email);
        editText_Contents = (EditText) findViewById(R.id.editText_Contents);
        button_send = (Button) findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendReport(editText_Email.getText().toString(), editText_Contents.getText().toString());
            }
        });
    }

    private void sendReport(String email, String content){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/feedback/"+uid+"/subemail",email);
        childUpdates.put("/feedback/"+uid+"/content",content);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "registerInvoiceOnMyList:onComplete:" + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Unexpected Error", Toast.LENGTH_SHORT).show();
                        }else{
                            onRegisterReportSuccess(); // success
                        }
                    }
                });
    }

    private void onRegisterReportSuccess(){
        Toast.makeText(mContext, "Thanks for your opinion.", Toast.LENGTH_SHORT).show();
        this.onBackPressed();
    }
}
