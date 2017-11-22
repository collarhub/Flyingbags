package net.flyingbags.flyingapps.view;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.R;

public class ModifyProfileActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = ModifyProfileActivity.class.getSimpleName();

    EditText editText_NameContent;
    EditText editText_BasicAddressContent;
    Button button_Accepted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        editText_NameContent = (EditText) findViewById(R.id.editText_NameContent);
        editText_BasicAddressContent = (EditText) findViewById(R.id.editText_BasicAddressContent);
        button_Accepted = (Button) findViewById(R.id.button_Accepted);

        getAddress();

        button_Accepted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameAndAddress(editText_NameContent.getText().toString(), editText_BasicAddressContent.getText().toString());
            }
        });
    }

    private void setNameAndAddress(String name, String address){
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

        Toast.makeText(this, "Update Complete.", Toast.LENGTH_SHORT).show();
        this.onBackPressed();
    }

    private void getAddress(){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String presentInfo = dataSnapshot.getValue(String.class);
                        if (presentInfo != null) {
                            displayProfile(presentInfo);
                        } else {
                            displayProfile("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        displayProfile("");
                    }
                });
    }

    private void displayProfile(String address){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user != null){
            editText_NameContent.setText(user.getDisplayName());
            editText_BasicAddressContent.setText(address);
        }
    }
}
