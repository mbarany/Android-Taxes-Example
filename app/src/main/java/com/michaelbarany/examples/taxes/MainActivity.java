package com.michaelbarany.examples.taxes;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.squareup.otto.Subscribe;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                .beginTransaction()
                .add(R.id.container, new StoreFragment())
                .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                showCart();
                return true;
            case R.id.action_clear_cart:
                Cart.getInstance().clear();
                return true;
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCart() {
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.container, new CartFragment())
            .addToBackStack(null)
            .commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onCartUpdated(Cart.CartUpdatedEvent event) {
        invalidateOptionsMenu();
    }

    @Subscribe
    public void onCartOptionItemSelected(CartActionProvider.CartOptionItemSelected event) {
        showCart();
    }
}
