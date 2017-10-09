package net.flyingbags.flyingapps.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.presenter.Tab0Presenter;
import net.flyingbags.flyingapps.service.Tab0Service;

/**
 * Created by User on 2017-10-09.
 */

public class Tab0Fragment extends Fragment implements Tab0Presenter.view{

    private Tab0Service tab0Service;
    private ImageButton imageButtonMenu;
    private ImageButton imageButtonScan;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab0, container, false);

        tab0Service = new Tab0Service(getActivity(), view);

        imageButtonMenu = (ImageButton) view.findViewById(R.id.imageButton_menu);
        imageButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuToggle();
            }
        });
        imageButtonScan = (ImageButton) view.findViewById(R.id.imageButton_scan);
        imageButtonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR();
            }
        });
        return view;
    }

    @Override
    public void menuToggle() {
        tab0Service.menuToggle();
    }

    @Override
    public void scanQR() {
        tab0Service.scanQR();
    }
}
