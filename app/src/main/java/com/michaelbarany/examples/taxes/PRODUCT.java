package com.michaelbarany.examples.taxes;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum PRODUCT {
    book("Book", new Money(12.49), false, false),
    music_cd("Music CD", new Money(14.99), true, false),
    chocolate_bar("Chocolate Bar", new Money(0.85), false, false),
    imported_chocolate_small("Imported Box of Chocolates", new Money(10.00), false, true),
    imported_perfume_big("Imported Bottle of Perfume", new Money(47.50), true, true),
    imported_perfume_small("Imported Bottle of Perfume", new Money(27.99), true, true),
    perfume("Bottle of Perfume", new Money(18.99), true, false),
    headache_pills("Packet of Headache Pills", new Money(9.75), false, false),
    imported_chocolate_big("Imported Box of Chocolates", new Money(11.25), false, true);

    private static final BigDecimal SALES_TAX = BigDecimal.valueOf(0.10);
    private static final BigDecimal IMPORT_TAX = BigDecimal.valueOf(0.05);

    private String mName;
    private Money mPrice;
    private boolean mTaxable;
    private boolean mImported;

    private PRODUCT(String name, Money price, boolean taxable, boolean imported) {
        mName = name;
        mPrice = price;
        mTaxable = taxable;
        mImported = imported;
    }

    @Override
    public String toString() {
        return mName + " @ " + mPrice;
    }

    public String getName() {
        return mName;
    }

    public BigDecimal getSalesTax() {
        BigDecimal tax = BigDecimal.valueOf(0);
        if (mTaxable) {
            tax = tax.add(mPrice.getAmount().multiply(SALES_TAX));
        }
        if (mImported) {
            tax = tax.add(mPrice.getAmount().multiply(IMPORT_TAX));
        }
        return round(tax);
    }

    private BigDecimal round(BigDecimal amount) {
        return amount
            .multiply(BigDecimal.valueOf(20))
            .setScale(0, BigDecimal.ROUND_CEILING)
            .divide(BigDecimal.valueOf(20))
            .setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTotalPrice() {
        return mPrice.getAmount();
    }

    public BigDecimal getPriceAndTax() {
        return getTotalPrice().add(getSalesTax()).setScale(2, RoundingMode.HALF_UP);
    }
}
