package net.flyingbags.flyingapps.presenter;

/**
 * Created by User on 2017-10-07.
 * Login presenter interface
 */

public class Login2Presenter {
    public interface view {
        void onCreateUser();
        void onCreateUserFailed();
        void onSignIn();
        void onSignOut();
        void onBackPressed();
        void onCreateUserSuccess();
        void onSignInSuccess();
        void onSignInFailed();

        void showProfile();
    }

    public interface presenter extends net.flyingbags.flyingapps.presenter.presenter {
    }
}
