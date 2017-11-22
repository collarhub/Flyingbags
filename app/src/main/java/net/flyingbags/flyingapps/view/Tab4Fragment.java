package net.flyingbags.flyingapps.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.flyingbags.flyingapps.R;

public class Tab4Fragment extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private static final String TAG = Tab4Fragment.class.getSimpleName();

    private TextView textView_MyProfileSettings;
    private TextView textView_ChangePassword;
    private TextView textView_ReportaProblem;
    private TextView textView_ContactUs;
    private TextView textView_TermsandConditions;
    private TextView textView_Logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4_settings, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        textView_MyProfileSettings = (TextView) view.findViewById(R.id.textView_MyProfileSettings);
        textView_ChangePassword = (TextView) view.findViewById(R.id.textView_ChangePassword);
        textView_ReportaProblem = (TextView) view.findViewById(R.id.textView_ReportaProblem);
        textView_ContactUs = (TextView) view.findViewById(R.id.textView_ContactUs);
        textView_TermsandConditions = (TextView) view.findViewById(R.id.textView_TermsandConditions);
        textView_Logout = (TextView) view.findViewById(R.id.textView_Logout);

        textView_MyProfileSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClicktextView_MyProfileSettings();
            }
        });
        textView_ChangePassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClicktextView_ChangePassword();
            }
        });
        textView_ReportaProblem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClicktextView_ReportaProblem();
            }

        });
        textView_ContactUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClicktextView_ContactUs();
            }

        });
        textView_TermsandConditions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onClicktextView_TermsandConditions();
            }

        });
        textView_Logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signOut();
            }
        });

        return view;
    }

    private void onClicktextView_MyProfileSettings(){
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, ModifyProfileActivity.class));
    }

    private void onClicktextView_ChangePassword(){
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, ModifyPasswordActivity.class));
    }

    private void onClicktextView_ReportaProblem(){
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, ReportUsActivity.class));
    }

    private void onClicktextView_ContactUs(){
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, ContactUsActivity.class));
    }

    private void onClicktextView_TermsandConditions(){
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, PolicyActivity.class));
    }

    private void signOut() {
        mAuth.signOut();
        Context baseContext= this.getActivity().getBaseContext();
        this.startActivity(new Intent(baseContext, LoginActivity.class));
        this.getActivity().finish();
    }
}
