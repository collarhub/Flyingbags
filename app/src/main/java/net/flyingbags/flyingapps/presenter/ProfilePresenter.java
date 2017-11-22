package net.flyingbags.flyingapps.presenter;

import net.flyingbags.flyingapps.model.User;

/**
 * Created by ernest on 17. 10. 8.
 * mi gu hyun (not implemented)
 */

public class ProfilePresenter {
    public interface view {
        void onGetUserProfileSuccess(User presentInfo);
        void onGetUserProfileFailed();
    }

    public interface presenter {
        void getUserProfile();
    }
}
