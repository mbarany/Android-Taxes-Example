package com.michaelbarany.examples.taxes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.squareup.otto.Subscribe;

public class CartFragment extends Fragment {
    private ListView mListView;
    private View mTaxView;
    private View mTotalView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView = new ListView(container.getContext());
        initView();
        return mListView;
    }

    private void initView() {
        if (null == mListView) {
            return;
        }
        mTaxView = Cart.getInstance().getTaxView(getActivity());
        mTotalView = Cart.getInstance().getTotalView(getActivity());
        mListView.addFooterView(mTaxView);
        mListView.addFooterView(mTotalView);
        mListView.setAdapter(
            new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                Cart.getInstance().getItems()
            )
        );
    }

    private void clearView() {
        if (null != mListView) {
            mListView.removeFooterView(mTaxView);
            mListView.removeFooterView(mTotalView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onCartUpdated(Cart.CartUpdatedEvent event) {
        clearView();
        initView();
    }
}
