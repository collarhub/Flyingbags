package net.flyingbags.flyingapps.service;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.Tab0Presenter;

import org.w3c.dom.Text;

/**
 * Created by User on 2017-10-09.
 */

public class Tab0Service implements Tab0Presenter.presenter {
    private FragmentActivity activity;
    private View view;

    public Tab0Service(FragmentActivity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    @Override
    public void menuToggle() {
        final ImageButton imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButton_menu);
        final ImageButton imageButtonScan = (ImageButton) view.findViewById(R.id.imageButton_scan);
        final TextView textViewScan = (TextView) view.findViewById(R.id.textView_scan);
        final LinearLayout linearLayoutTransparentGray = (LinearLayout) view.findViewById(R.id.transparentGray);
        ObjectAnimator objectAnimatorButton;
        ObjectAnimator objectAnimatorText;
        if(imageButtonScan.getVisibility() == View.INVISIBLE) {
            linearLayoutTransparentGray.setVisibility(View.VISIBLE);
            imageButtonMenu.setImageResource(R.drawable.x);
            imageButtonScan.setVisibility(View.VISIBLE);
            objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop(), imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorButton.setDuration(250);
            objectAnimatorButton.start();
            objectAnimatorButton.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                @Override
                public void onAnimationEnd(Animator animation) {
                    textViewScan.setVisibility(View.VISIBLE);
                    ObjectAnimator objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, 0);
                    objectAnimatorButton.setDuration(250);
                    ObjectAnimator objectAnimatorText = ObjectAnimator.ofFloat(textViewScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, 0);
                    objectAnimatorText.setDuration(250);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(objectAnimatorButton, objectAnimatorText);
                    animatorSet.start();
                }
                @Override
                public void onAnimationCancel(Animator animation) {}
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        }
        else {
            imageButtonMenu.setImageResource(R.drawable.qrcode);
            objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", 0, imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorButton.setDuration(250);
            objectAnimatorText = ObjectAnimator.ofFloat(textViewScan, "translationY", 0, imageButtonMenu.getTop() - imageButtonScan.getTop() - 25);
            objectAnimatorText.setDuration(250);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimatorButton, objectAnimatorText);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}
                @Override
                public void onAnimationEnd(Animator animation) {
                    textViewScan.setVisibility(View.INVISIBLE);
                    ObjectAnimator objectAnimatorButton = ObjectAnimator.ofFloat(imageButtonScan, "translationY", imageButtonMenu.getTop() - imageButtonScan.getTop() - 25, imageButtonMenu.getTop() - imageButtonScan.getTop());
                    objectAnimatorButton.setDuration(250);
                    objectAnimatorButton.start();
                    objectAnimatorButton.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {}
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            imageButtonScan.setVisibility(View.INVISIBLE);
                            linearLayoutTransparentGray.setVisibility(View.INVISIBLE);
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {}
                        @Override
                        public void onAnimationRepeat(Animator animation) {}
                    });
                }
                @Override
                public void onAnimationCancel(Animator animation) {}
                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
        }
    }

    @Override
    public void scanQR() {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        integrator.addExtra("PROMPT_MESSAGE", "Scan QR Code within frame");
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.initiateScan();
    }
}
