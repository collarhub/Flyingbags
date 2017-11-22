package net.flyingbags.flyingapps.presenter;

import android.app.Activity;

/**
 * Created by User on 2017-10-07.
 */

public class LoginPresenter {
    public interface view {
        void onSignInFailed();
        void onSignInSuccess();
        void onCreateUserFailed(String errorMessage);
        void onCreateUserSuccess();

        void onAuthAdminSuccess();
    }

    public interface presenter {
        void createUser(String email, String password);
        void signIn(String email, String password);

        void authAdmin();
    }
}
