package com.michaelbarany.examples.taxes;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StoreFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);
        final Spinner spinner = (Spinner) rootView.findViewById(R.id.items);
        spinner.setAdapter(
            new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, PRODUCT.values())
        );
        rootView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart.getInstance().addItem((PRODUCT) spinner.getSelectedItem());
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
