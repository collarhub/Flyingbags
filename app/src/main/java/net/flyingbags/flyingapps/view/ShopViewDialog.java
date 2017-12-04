package net.flyingbags.flyingapps.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.flyingbags.flyingapps.R;
import net.flyingbags.flyingapps.etc.Shop;

/**
 * Created by User on 2017-12-04.
 */

public class ShopViewDialog extends Dialog {
    private ConstraintLayout constraintLayout;
    private ImageButton imageButtonShopViewExit;
    private Shop shop;
    private int index;
    public ShopViewDialog(@NonNull Context context, Shop shop) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.shop = shop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_shop_view);

        constraintLayout = (ConstraintLayout) findViewById(R.id.dialog_shop_view_out);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        imageButtonShopViewExit = (ImageButton) findViewById(R.id.imageButton_shop_view_exit);
        imageButtonShopViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ((ImageButton)findViewById(R.id.imageButton_shop_view_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index--;
                if(index == -1) {
                    index = shop.getImage().size() - 1;
                }
                ((ImageView) findViewById(R.id.imageView_shop_view_image)).setImageResource(shop.getImage().get(index));
            }
        });
        ((ImageButton)findViewById(R.id.imageButton_shop_view_forward)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                if(index == shop.getImage().size()) {
                    index = 0;
                }
                ((ImageView) findViewById(R.id.imageView_shop_view_image)).setImageResource(shop.getImage().get(index));
            }
        });

    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(shop.getImage().size() == 0) {
            index = -1;
            ((ImageView) findViewById(R.id.imageView_shop_view_image)).setImageResource(0);
            ((ConstraintLayout)findViewById(R.id.constraintLayout_shop_view_button)).setVisibility(View.GONE);
        }
        else {
            index = 0;
            ((ConstraintLayout)findViewById(R.id.constraintLayout_shop_view_button)).setVisibility(View.VISIBLE);
            ((ImageView) findViewById(R.id.imageView_shop_view_image)).setImageResource(shop.getImage().get(0));
            ((ConstraintLayout)findViewById(R.id.constraintLayout_shop_view_button)).bringToFront();
        }
        ((TextView)findViewById(R.id.textView_shop_view_name)).setText(shop.getName());
        ((TextView)findViewById(R.id.textView_shop_view_address)).setText(shop.getAddress());
        ((TextView)findViewById(R.id.textView_shop_view_tel)).setText(shop.getTel());
    }
}
