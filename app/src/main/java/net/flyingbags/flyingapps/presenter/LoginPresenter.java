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
        void onSignInFailed();
        void onSignInSuccess();
        void onCreateUserFailed();
        void onCreateUserSuccess();
    }

    public interface presenter {
        void focusOut();
        void signIn();
        void createUser(String email, String password);
        void signIn(String email, String password);

        void authAdmin();
    }
}
