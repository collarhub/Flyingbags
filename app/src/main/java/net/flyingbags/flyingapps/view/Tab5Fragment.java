package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.User;
import net.flyingbags.flyingapps.presenter.ProfilePresenter;

import java.util.regex.Pattern;

public class Tab5Fragment extends Fragment{
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = Tab5Fragment.class.getSimpleName();

    TextView textView_NameContent;
    TextView textView_EmailContent;
    TextView textView_BasicAddressContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab5_profile, container, false);

        textView_NameContent = (TextView) view.findViewById(R.id.textView_NameContent);
        textView_EmailContent = (TextView) view.findViewById(R.id.textView_EmailContent);
        textView_BasicAddressContent = (TextView) view.findViewById(R.id.textView_BasicAddressContent);

        getAddress();

        return view;
    }

    private void getAddress(){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String presentInfo = dataSnapshot.getValue(String.class);
                        if (presentInfo != null) {
                            displayProfile(presentInfo);
                            //view.onGetInvoiceSuccess(invoice, presentInfo);
                        } else {
                            displayProfile("");
                            //view.onGetInvoiceFailed();
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
            textView_NameContent.setText(user.getDisplayName());
            textView_EmailContent.setText(user.getEmail());
            textView_BasicAddressContent.setText(address);
        }else{
            textView_NameContent.setText("null");
            textView_EmailContent.setText("null");
            textView_BasicAddressContent.setText("null");
        }
    }
}
