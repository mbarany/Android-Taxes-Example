package com.michaelbarany.examples.taxes;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Cart {
    private static final Cart sCart = new Cart();
    private final List<Item> mItems = new ArrayList<>();
    // Memoized total item count
    private int mItemCount = 0;

    public static Cart getInstance() {
        return sCart;
    }

    private Cart() {
    }

    public synchronized void addItem(PRODUCT product) {
        Item i = new Item(product);
        int index = mItems.indexOf(i);
        if (-1 == index) {
            mItems.add(i);
        } else {
            mItems.get(index).incrementQuantity();
        }
        mItemCount++;
        BusProvider.getInstance().post(new CartUpdatedEvent());
    }

    public synchronized void clear() {
        mItems.clear();
        mItemCount = 0;
        BusProvider.getInstance().post(new CartUpdatedEvent());
    }

    public List<Item> getItems() {
        return new ArrayList<>(mItems);
    }

    public int getTotalCount() {
        return mItemCount;
    }

    public View getTaxView(Context context) {
        TextView textView = new TextView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.cart_total__padding);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("Sales Taxes: " + getTotalTax());
        return textView;
    }

    private BigDecimal getTotalTax() {
        BigDecimal tax = BigDecimal.valueOf(0);
        for (Item i : mItems) {
            tax = tax.add(i.getSalesTax());
        }
        return tax;
    }

    public View getTotalView(Context context) {
        BigDecimal total = getTotalPrice().add(getTotalTax());
        TextView textView = new TextView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.cart_total__padding);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText("Total: " + total);
        return textView;
    }

    private BigDecimal getTotalPrice() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Item i : mItems) {
            total = total.add(i.getTotalPrice());
        }
        return total;
    }

    public static class CartUpdatedEvent {
    }

    public static class Item {
        private int mQuantity = 1;
        private final PRODUCT mProduct;

        private Item(PRODUCT product) {
            mProduct = product;
        }

        private void incrementQuantity() {
            mQuantity++;
        }

        public int getQuantity() {
            return mQuantity;
        }

        public PRODUCT getProduct() {
            return mProduct;
        }

        public BigDecimal getSalesTax() {
            return mProduct.getSalesTax().multiply(BigDecimal.valueOf(mQuantity));
        }

        public BigDecimal getTotalPrice() {
            return mProduct.getTotalPrice().multiply(BigDecimal.valueOf(mQuantity));
        }

        @Override
        public int hashCode() {
            return mProduct.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (null == o || !(o instanceof Item)) {
                return false;
            }
            return mProduct.equals(((Item) o).getProduct());
        }

        @Override
        public String toString() {
            return getQuantity() + " x " + mProduct.getName() + " @ " + mProduct.getPriceAndTax();
        }
    }
}
