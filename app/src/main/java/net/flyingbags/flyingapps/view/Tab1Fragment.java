package net.flyingbags.flyingapps.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Invoice;
import java.util.Vector;
import java.util.regex.Pattern;

public class Tab1Fragment extends Fragment{
    CustomAdapter mAdapter;
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1_noti, container, false);

        mListView = (ListView) view.findViewById(R.id.ListView_onDelivery);
        mAdapter = new CustomAdapter(new Vector<Notimsg>());
        mListView.setAdapter(mAdapter);

        getInvoicesVector();

        return view;
    }

    class Notimsg {
        String title;
        String timestamp;
        String content;
    }

    class CustomAdapter extends BaseAdapter {
        private Vector<Notimsg> mVector;

        public CustomAdapter(Vector<Notimsg> routeVector){
            mVector = routeVector;
        }

        @Override
        public int getCount() {
            return mVector.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            // "listview_item" Layout을 inflate하여 convertView 참조 획득.
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.fragment_tab1_noti_item, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            TextView TextView_title = (TextView) convertView.findViewById(R.id.TextView_title);
            TextView textView_timestamp = (TextView) convertView.findViewById(R.id.textView_timestamp);
            TextView textView_content = (TextView) convertView.findViewById(R.id.textView_content);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final Notimsg item = mVector.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            TextView_title.setText(item.title);
            textView_timestamp.setText(item.timestamp);
            textView_content.setText(item.content);

            return convertView;
        }

        // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
        @Override
        public Object getItem(int position) {
            return mVector.get(position);
        }

        public void addItem(Notimsg item){
            mVector.add(item);
        }
    }

    // get invoices array
    private void getInvoicesVector(){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("invoices")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Vector<String> Invoices = new Vector<String>();
                        for(DataSnapshot newOrdersSnapShot : dataSnapshot.getChildren()){
                            Invoices.add(newOrdersSnapShot.getKey());
                        }
                        onGetInvoicesVectorSuccess(Invoices);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void onGetInvoicesVectorSuccess(Vector<String> Invoices){
        if(Invoices.size()>0) {
            for (String temp : Invoices) {
                getInvoice(temp);
            }
        }else{
            Notimsg msg = new Notimsg();
            msg.title = "There is no message.";
            msg.content = "";
            msg.timestamp = "";

            mAdapter.addItem(msg);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void getInvoice(final String invoice){
        if(Pattern.matches("^[0-9]{10}",invoice)){
            FirebaseDatabase.getInstance().getReference().child("invoices").child(invoice).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Invoice presentInfo = dataSnapshot.getValue(Invoice.class);
                    if (presentInfo != null) {
                        onGetInvoiceSuccess(invoice, presentInfo);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void onGetInvoiceSuccess(String invoice, Invoice presentInfo){
        Notimsg msg = new Notimsg();
        msg.title = "null";
        msg.content = "null";
        msg.timestamp = "null";
        if(presentInfo.getStatus().equalsIgnoreCase("delivering")){
            msg.title = "Your package is coming for you.";
            msg.content = "No."+invoice+" is delivering by us.";
            msg.timestamp = "";
        }else if(presentInfo.getStatus().equalsIgnoreCase("confirmed")){
            msg.title = "We got your package.";
            msg.content = "No."+invoice+" is confirmed by us.";
            msg.timestamp = "";
        }else if(presentInfo.getStatus().equalsIgnoreCase("arrived")){
            msg.title = "You got your package.";
            msg.content = "No."+invoice+" is arrived to you.";
            msg.timestamp = "";
        }else if(presentInfo.getStatus().equalsIgnoreCase("ready")){
            msg.title = "We accepted your order.";
            msg.content = "No."+invoice+" is ready for delivery service.";
            msg.timestamp = "";
        }
        mAdapter.addItem(msg);
        mAdapter.notifyDataSetChanged();

    }

}
