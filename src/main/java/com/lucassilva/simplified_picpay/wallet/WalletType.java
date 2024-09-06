package com.lucassilva.simplified_picpay.wallet;

public enum WalletType {
    COMMON(1), SALESPERSON(2);

    private final int value;

    private WalletType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
