package net.flyingbags.flyingapps.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.model.User;
import net.flyingbags.flyingapps.presenter.ProfilePresenter;

public class ProfileService implements ProfilePresenter.presenter {

    ProfilePresenter.view view;

    public ProfileService(ProfilePresenter.view view) {
        this.view = view;
    }

    @Override
    public void getUserProfile() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User presentInfo = dataSnapshot.getValue(User.class);
                        if (presentInfo != null) {
                            view.onGetUserProfileSuccess(presentInfo);
                        } else {
                            view.onGetUserProfileFailed();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        view.onGetUserProfileFailed();
                    }
                });
    }


}
