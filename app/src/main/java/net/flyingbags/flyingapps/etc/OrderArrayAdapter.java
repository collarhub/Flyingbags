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
import java.util.List;

/**
 * Created by User on 2017-10-22.
 */

public class OrderArrayAdapter extends ArrayAdapter {
    private ArrayList<OrderListItem> arrayList;

    public OrderArrayAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<OrderListItem> arrayList) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_order, parent, false);
        }
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    public void addItem(OrderListItem orderListItem) {
        arrayList.add(orderListItem);
    }
}
