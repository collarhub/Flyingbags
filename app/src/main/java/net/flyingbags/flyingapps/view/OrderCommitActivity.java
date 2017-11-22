package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.ActionBarPresenter;
import net.flyingbags.flyingapps.service.ActionBarService;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 2017-10-23.
 */

public class OrderCommitActivity extends AppCompatActivity {

    private Button buttonCommit;
    private TextView textViewEstimatedMD;
    private TextView textViewEstimatedY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_commit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonCommit = (Button) findViewById(R.id.button_commit);
        buttonCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ActionBarPresenter.RESULT_HOME, null);
                finish();
            }
        });

        String estimated = getIntent().getStringExtra("estimated");
        Date date = null;

        textViewEstimatedMD = (TextView) findViewById(R.id.textView_state_estimated_m_d);
        textViewEstimatedY = (TextView) findViewById(R.id.textView_state_estimated_y);


        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        try {
            date = dateFormat.parse(estimated);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        estimated = dateFormat.format(date);
        SpannableString content = new SpannableString(estimated);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textViewEstimatedMD.setText(estimated);
    }

    @Override
    public void onBackPressed() {
        setResult(ActionBarPresenter.RESULT_HOME, null);
        finish();
    }
}
