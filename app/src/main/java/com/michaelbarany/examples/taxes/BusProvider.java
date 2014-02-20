package com.michaelbarany.examples.taxes;

import com.squareup.otto.Bus;

public final class BusProvider {
    private static final BusProvider sBusProvider = new BusProvider();

    private Bus mBus = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return sBusProvider.mBus;
    }
}
