package net.flyingbags.flyingapps.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.model.Invoice;
import net.flyingbags.flyingapps.model.Route;
import net.flyingbags.flyingapps.presenter.AdminPresenter;
import net.flyingbags.flyingapps.service.AdminService;

import java.util.Map;
import java.util.Vector;

public class AdminInvoiceRouteActivity extends AppCompatActivity implements AdminPresenter.view{
    AdminService mAdminService;
    CustomAdapter mAdapter;
    ListView mListView;

    String invoiceKey;

    Map<String, Route> routeMap;
    Vector<Route> routeVector;

    EditText editText_no;
    EditText editText_status;
    EditText editText_date;

    Button button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminService = new AdminService(this);
        setContentView(R.layout.activity_admin_invoice_route);

        Intent intent = getIntent();
        routeMap = ((Invoice) intent.getExtras().getSerializable("invoice")).getRoute();
        invoiceKey = (String) intent.getExtras().getSerializable("invoiceKey");
        routeVector = new Vector<Route>();

        mListView = (ListView) findViewById(R.id.ListView_invoice);
        mAdapter = new CustomAdapter(routeVector);
        mListView.setAdapter(mAdapter);

        for(int i=0;routeMap.containsKey("No"+i)==true;i++){
            mAdapter.addItem(routeMap.get("No"+i));
            mAdapter.notifyDataSetChanged();
        }

        editText_no = (EditText) findViewById(R.id.editText_no);
        editText_no.setText("No"+routeVector.size());
        editText_status = (EditText) findViewById(R.id.editText_status);
        editText_date = (EditText) findViewById(R.id.editText_date);

        editText_no.setFocusable(false);
        editText_no.setFocusableInTouchMode(false);
        editText_no.setClickable(false);

        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                routeMap.put(editText_no.getText().toString(), new Route(editText_status.getText().toString(), editText_date.getText().toString()));
                mAdminService.setInvoice(invoiceKey, "route", routeMap);
            }
        });
    }

    @Override
    public void onSetInvoiceFailed() {

    }

    @Override
    public void onSetInvoiceSuccess() {
        routeVector = new Vector<Route>();
        mAdapter = new CustomAdapter(routeVector);
        mListView.setAdapter(mAdapter);

        for(int i=0;routeMap.containsKey("No"+i)==true;i++){
            mAdapter.addItem(routeMap.get("No"+i));
            mAdapter.notifyDataSetChanged();
        }

        editText_no.setText("No"+routeVector.size());
        editText_status.setText("status");
        editText_date.setText("date");
    }

    @Override
    public void onGetInvoiceSuccess(final String invoice, Invoice presentInfo) {
    }

    @Override
    public void onGetInvoiceFailed() {
    }

    @Override
    public void onGetInvoicesVectorSuccess(Vector<String> invoices) {
    }

    @Override
    public void onGetInvoicesVectorFailed() {
    }

    class CustomAdapter extends BaseAdapter {
        private Vector<Route> mVector;

        public CustomAdapter(Vector<Route> routeVector){
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
                convertView = inflater.inflate(R.layout.activity_admin_invoice_route_item, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            EditText editText_no = (EditText) convertView.findViewById(R.id.editText_no);
            EditText editText_status = (EditText) convertView.findViewById(R.id.editText_status);
            EditText editText_date = (EditText) convertView.findViewById(R.id.editText_date);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final Route item = mVector.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            editText_no.setText("No"+position);
            editText_status.setText(item.getStatus());
            editText_date.setText(item.getDate());

            editText_no.setFocusable(false);
            editText_no.setFocusableInTouchMode(false);
            editText_no.setClickable(false);
            editText_status.setFocusable(false);
            editText_status.setFocusableInTouchMode(false);
            editText_status.setClickable(false);
            editText_date.setFocusable(false);
            editText_date.setFocusableInTouchMode(false);
            editText_date.setClickable(false);

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

        public void addItem(Route item){
            mVector.add(item);
        }
    }
}
