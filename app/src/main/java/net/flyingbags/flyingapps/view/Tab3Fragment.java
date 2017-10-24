package net.flyingbags.flyingapps.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.StateArrayAdapter;
import net.flyingbags.flyingapps.etc.StateListItem;

import java.util.ArrayList;

/**
 * Created by User on 2017-10-09.
 */

public class Tab3Fragment extends Fragment {
    private ListView listView;
    private StateArrayAdapter stateArrayAdapter;
    private ArrayList<StateListItem> arrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        arrayList = new ArrayList<>();
        arrayList.add(new StateListItem());
        arrayList.add(new StateListItem());

        stateArrayAdapter = new StateArrayAdapter(getActivity(), R.layout.item_status, arrayList);

        listView = (ListView) view.findViewById(R.id.list_state);
        listView.setAdapter(stateArrayAdapter);

        return view;
    }
}
