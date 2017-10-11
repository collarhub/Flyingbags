package net.flyingbags.flyingapps.presenter;

/**
 * Created by User on 2017-10-11.
 */

public class ScheduleDeliveryPresenter {
    public static final int RESULT_HOME = 1002;
    public static final int RESULT_PROFILE = 1003;
    public interface view {
        void showHome();
        void showProfile();
    }

    public interface presenter {
        void showHome();
        void showProfile();
    }
}
