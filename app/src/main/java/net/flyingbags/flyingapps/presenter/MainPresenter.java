package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

/**
 * Created by User on 2017-10-10.
 */

public class MainPresenter {
    public static final int REQUEST_CODE_TAB = 1001;
    public static final int RESULT_HOME = 1002;
    public static final int RESULT_PROFILE = 1003;
    public interface view {
        void verifyQR(int requestCode, int resultCode, Intent data);
    }

    public interface presenter {
        String verifyQR(int requestCode, int resultCode, Intent data);
    }
}
