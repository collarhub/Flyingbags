package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

/**
 * Created by User on 2017-10-10.
 */

public class MainPresenter {
    public interface view {
        void verifyQR(int requestCode, int resultCode, Intent data);
    }

    public interface presenter {
        String verifyQR(int requestCode, int resultCode, Intent data);
    }
}
