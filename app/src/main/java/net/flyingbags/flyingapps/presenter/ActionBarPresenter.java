package net.flyingbags.flyingapps.presenter;

/**
 * Created by User on 2017-10-08.
 */

public class ActionBarPresenter {
    public interface view {
        void showActionBar();
        void setTitle(String title);
    }
    public interface presenter {
        void showActionBar();
        void setTitle(String title);
    }
}
