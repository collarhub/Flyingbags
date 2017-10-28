package net.flyingbags.flyingapps.etc;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        StateListItem stateListItem = arrayList.get(position);
        ((TextView)convertView.findViewById(R.id.textView_state_order_number)).setText(stateListItem.getOrderId());
        LinearLayout linearLayoutReadyHeader = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_ready_header);
        LinearLayout linearLayoutReadyBody = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_ready_body);
        LinearLayout linearLayoutConfirmedHeader = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_confirmed_header);
        LinearLayout linearLayoutConfirmedBody = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_confirmed_body);
        LinearLayout linearLayoutDeliveringHeader = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_delivering_header);
        LinearLayout linearLayoutDeliveringBody = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_delivering_body);
        LinearLayout linearLayoutArrivedHeader = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_arrived_header);
        LinearLayout linearLayoutArrivedBody = (LinearLayout) convertView.findViewById(R.id.linearLayout_state_arrived_body);
        String status = stateListItem.getStatus();
        switch (status) {
            case "ready":
                linearLayoutConfirmedHeader.setVisibility(View.GONE);
                linearLayoutConfirmedBody.setVisibility(View.GONE);
                linearLayoutDeliveringHeader.setVisibility(View.GONE);
                linearLayoutDeliveringBody.setVisibility(View.GONE);
                linearLayoutArrivedHeader.setVisibility(View.GONE);
                linearLayoutArrivedBody.setVisibility(View.GONE);
                ((ImageView)convertView.findViewById(R.id.imageView_state_ready_body_stick)).setVisibility(View.INVISIBLE);
                ((ImageView)convertView.findViewById(R.id.imageView_state_ready_header_bottom)).setVisibility(View.INVISIBLE);
                break;
        }
        return convertView;
    }

    public void addItem(StateListItem stateListItem) {
        arrayList.add(stateListItem);
    }
}
