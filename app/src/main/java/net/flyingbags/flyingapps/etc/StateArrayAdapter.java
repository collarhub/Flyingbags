package net.flyingbags.flyingapps.etc;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import net.flyingbags.flyingapps.R;

import java.util.ArrayList;

/**
 * Created by User on 2017-10-23.
 */

public class StateArrayAdapter extends ArrayAdapter {
    private ArrayList<StateListItem> arrayList;

    public StateArrayAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<StateListItem> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_status, parent, false);
        }
        return convertView;
    }

    public void addItem(StateListItem stateListItem) {
        arrayList.add(stateListItem);
    }
}
