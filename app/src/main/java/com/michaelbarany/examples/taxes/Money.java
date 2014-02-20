package com.michaelbarany.examples.taxes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private BigDecimal mAmount;

    public Money(double amount) {
        mAmount = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getAmount() {
        return mAmount;
    }

    @Override
    public String toString() {
        return getAmount().toString();
    }
}
