package net.flyingbags.flyingapps.etc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import net.flyingbags.flyingapps.R;

/**
 * Created by User on 2017-11-17.
 */

public class CustomPlaceAutocompleteFragment extends PlaceAutocompleteFragment{
    private Place place;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                CustomPlaceAutocompleteFragment.this.place = place;
            }

            @Override
            public void onError(Status status) {

            }
        });
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        super.onActivityResult(i, i1, intent);
        if(place != null)
            ((AppCompatEditText) getView().findViewById(R.id.place_autocomplete_search_input)).setText(place.getAddress());
    }
}
