package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;

public class ReportUsActivity extends AppCompatActivity{

    TextView textView_EmailTitle;
    EditText editText_Email;
    TextView textView_ContentsTitle;
    EditText editText_Contents;
    Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportus);

        textView_EmailTitle = (TextView) findViewById(R.id.textView_EmailTitle);
        editText_Email = (EditText) findViewById(R.id.editText_Email);
        textView_ContentsTitle = (TextView) findViewById(R.id.textView_ContentsTitle);
        editText_Contents = (EditText) findViewById(R.id.editText_Contents);
        button_send = (Button) findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
