package net.flyingbags.flyingapps.presenter;

import android.app.Activity;

/**
 * Created by User on 2017-10-07.
 * Login presenter interface
 */

public class LoginPresenter {
    public interface view {
        void focusOut();
        void signIn();
    }

    public interface presenter {
        void focusOut();
        void signIn();
    }
}
