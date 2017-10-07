package net.flyingbags.flyingapps.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.flyingbags.flyingapps.R;

/**
 * Created by User on 2017-10-07.
 * 구현 안됨
 */

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, Login2Activity.class);
        startActivity(intent);
    }
}
