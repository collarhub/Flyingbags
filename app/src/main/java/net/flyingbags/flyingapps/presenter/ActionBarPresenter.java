package net.flyingbags.flyingapps.presenter;

/**
 * Created by User on 2017-10-08.
 */

public class ActionBarPresenter {
    public static final int REQUEST_CODE_TAB = 1001;
    public static final int RESULT_HOME = 1002;
    public static final int RESULT_PROFILE = 1003;
    public interface view {
        void showActionBar();
        void setTitle(String title);
    }
    public interface presenter {
        void showActionBar();
        void setTitle(String title);
    }
}
