package net.flyingbags.flyingapps.presenter;

import android.content.Intent;

/**
 * Created by User on 2017-10-09.
 */

public class Tab0Presenter {
    public interface view {
        void showMap();
        void menuToggle();
        void scanQR();
    }

    public interface presenter {
        void menuToggle();
        void scanQR();
        void showMap();
    }
}
