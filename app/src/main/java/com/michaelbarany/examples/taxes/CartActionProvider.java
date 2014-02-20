package com.michaelbarany.examples.taxes;

import android.content.Context;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CartActionProvider extends ActionProvider {
    private final Context mContext;

    public CartActionProvider(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.actionbar_cart, null);
        int cartCount = Cart.getInstance().getTotalCount();
        if (cartCount > 0) {
            TextView countView = (TextView) view.findViewById(R.id.count);
            countView.setText("" + cartCount);
            countView.setVisibility(View.VISIBLE);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new CartOptionItemSelected());
            }
        });
        return view;
    }

    @Override
    public boolean onPerformDefaultAction() {
        BusProvider.getInstance().post(new CartOptionItemSelected());
        return true;
    }

    public static class CartOptionItemSelected {
    }
}
