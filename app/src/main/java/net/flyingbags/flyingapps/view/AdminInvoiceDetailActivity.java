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
import net.flyingbags.flyingapps.presenter.AdminPresenter;
import net.flyingbags.flyingapps.service.AdminService;

import java.util.Vector;

public class AdminInvoiceDetailActivity extends AppCompatActivity implements AdminPresenter.view{
    AdminService mAdminService;
    CustomAdapter mAdapter;
    ListView mListView;

    String invoiceNumber;

    Button routeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminService = new AdminService(this);
        setContentView(R.layout.activity_admin_invoice_detail);

        Intent intent = getIntent();
        invoiceNumber = intent.getExtras().getString("invoice");

        mListView = (ListView) findViewById(R.id.ListView_invoice);
        mAdapter = new CustomAdapter();
        mListView.setAdapter(mAdapter);

        mAdminService.getInvoice(invoiceNumber);
    }

    @Override
    public void onSetInvoiceFailed() {

    }

    @Override
    public void onSetInvoiceSuccess() {

    }

    @Override
    public void onGetInvoiceSuccess(final String invoice, final Invoice presentInfo) {
        routeButton = (Button) findViewById(R.id.button_route);
        final Context mContext = this;
        routeButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AdminInvoiceRouteActivity.class);
                intent.putExtra("invoice", presentInfo);
                intent.putExtra("invoiceKey", invoice);
                startActivity(intent);
            }
        });

        mAdapter.addItem(new CustomListViewItem("status",presentInfo.getStatus(),true));
        mAdapter.addItem(new CustomListViewItem("location",presentInfo.getLocation(),true));
        mAdapter.addItem(new CustomListViewItem("orderDate",presentInfo.getOrderDate(),false));
        mAdapter.addItem(new CustomListViewItem("target",presentInfo.getTarget(),false));
        mAdapter.addItem(new CustomListViewItem("deliveryType",presentInfo.getDeliveryType(),false));
        mAdapter.addItem(new CustomListViewItem("departure",presentInfo.getDeparture(),false));
        mAdapter.addItem(new CustomListViewItem("minDateExpected",presentInfo.getMinDateExpected(),false));
        mAdapter.addItem(new CustomListViewItem("maxDateExpected",presentInfo.getMaxDateExpected(),false));
        mAdapter.addItem(new CustomListViewItem("packageType",presentInfo.getPackageType(),false));
        mAdapter.addItem(new CustomListViewItem("price",presentInfo.getPrice(),false));

        mAdapter.notifyDataSetChanged();
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

    class CustomListViewItem {
        private String field;
        private String content;
        private Boolean listener;

        public CustomListViewItem(String field, String content, Boolean listener){
            this.field = field;
            this.content = content;
            this.listener = listener;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Boolean getListener() {
            return listener;
        }

        public void setListener(Boolean listener) {
            this.listener = listener;
        }
    }

    class CustomAdapter extends BaseAdapter {
        private Vector<CustomListViewItem> mVector;

        public CustomAdapter(){
            mVector = new Vector<CustomListViewItem>();
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
                convertView = inflater.inflate(R.layout.activity_admin_invoice_detail_item, parent, false);
            }

            // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
            TextView TextView_field = (TextView) convertView.findViewById(R.id.TextView_field);
            final EditText editText_content = (EditText) convertView.findViewById(R.id.editText_content);
            Button button_mod = (Button) convertView.findViewById(R.id.button_mod);

            // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
            final CustomListViewItem item = mVector.get(position);

            // 아이템 내 각 위젯에 데이터 반영
            TextView_field.setText(item.getField());
            editText_content.setText(item.getContent());

            if(item.getListener()==true) {
                button_mod.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        mAdminService.setInvoice(invoiceNumber, item.getField(), editText_content.getText().toString());
                    }
                });
            }else{
                button_mod.setVisibility(View.GONE);
                editText_content.setFocusable(false);
                editText_content.setFocusableInTouchMode(false);
                editText_content.setClickable(false);
            }

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

        public void addItem(CustomListViewItem item){
            mVector.add(item);
        }
    }
}
