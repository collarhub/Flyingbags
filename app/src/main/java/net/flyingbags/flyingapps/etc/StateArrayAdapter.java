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
import android.widget.Toast;

import net.flyingbags.flyingapps.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by User on 2017-10-23.
 */

public class StateArrayAdapter extends ArrayAdapter {
    private ArrayList<StateListItem> arrayList;
    private Context context;

    public StateArrayAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<StateListItem> arrayList) {
        super(context, resource, arrayList);
        this.context = context;
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

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        long orderDate = 0;
        long today = Calendar.getInstance().getTime().getTime();
        try {
            orderDate = dateFormat.parse(stateListItem.getOrderDate()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String estimatedTime = null;
        if((today - orderDate) / (24 * 60 * 60 * 1000) < 2) {
            estimatedTime = (2 - (today - orderDate) / (24 * 60 * 60 * 1000)) + "";
        } else {
            estimatedTime = "0";
        }

        ((TextView) convertView.findViewById(R.id.textView_state_estimated_time)).setText(estimatedTime + " day");
        ((TextView) convertView.findViewById(R.id.textView_state_order_number)).setText("#" + stateListItem.getOrderId());

        if(stateListItem.getRoute().get("No0") != null) {
            ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_ready_header)).setVisibility(View.VISIBLE);
            ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_ready_body)).setVisibility(View.VISIBLE);
            ((TextView) convertView.findViewById(R.id.textView_state_ready_date)).setText(stateListItem.getRoute().get("No0").getDate());
            if(stateListItem.getRoute().get("No1") != null) {
                ((ImageView) convertView.findViewById(R.id.imageView_state_ready_header_bottom)).setVisibility(View.VISIBLE);
                ((ImageView) convertView.findViewById(R.id.imageView_state_ready_body_stick)).setVisibility(View.VISIBLE);
                ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_confirmed_header)).setVisibility(View.VISIBLE);
                ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_confirmed_body)).setVisibility(View.VISIBLE);
                ((TextView) convertView.findViewById(R.id.textView_state_confirmed_date)).setText(stateListItem.getRoute().get("No1").getDate());
                if(stateListItem.getRoute().get("No2") != null) {
                    ((ImageView) convertView.findViewById(R.id.imageView_state_confirmed_header_bottom)).setVisibility(View.VISIBLE);
                    ((ImageView) convertView.findViewById(R.id.imageView_state_confirmed_body_stick)).setVisibility(View.VISIBLE);
                    ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_delivering_header)).setVisibility(View.VISIBLE);
                    ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_delivering_body)).setVisibility(View.VISIBLE);
                    ((TextView) convertView.findViewById(R.id.textView_state_delivering_date)).setText(stateListItem.getRoute().get("No2").getDate());
                    if(stateListItem.getRoute().get("No3") != null) {
                        ((ImageView) convertView.findViewById(R.id.imageView_state_delivering_header_bottom)).setVisibility(View.VISIBLE);
                        ((ImageView) convertView.findViewById(R.id.imageView_state_delivering_body_stick)).setVisibility(View.VISIBLE);
                        ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_arrived_header)).setVisibility(View.VISIBLE);
                        ((LinearLayout) convertView.findViewById(R.id.linearLayout_state_arrived_body)).setVisibility(View.VISIBLE);
                        ((TextView) convertView.findViewById(R.id.textView_state_arrived_date)).setText(stateListItem.getRoute().get("No3").getDate());
                    }
                }
            }
        }
        return convertView;
    }

    public void addItem(StateListItem stateListItem) {
        arrayList.add(stateListItem);
    }
}
