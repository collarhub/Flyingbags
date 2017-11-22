package net.flyingbags.flyingapps.presenter;

import android.app.Activity;

/**
 * Created by User on 2017-10-07.
 * 실제 presenter는 사실 service 클래스들이라고 보면됨
 */

public class LoadingPresenter {
    public interface view {
        void loading();
    }

    public interface presenter {
        void loading();
    }
}
