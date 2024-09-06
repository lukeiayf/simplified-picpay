package com.lucassilva.simplified_picpay.authorization;

public record Authorization(
        String status,
        Data data
) {
    public record Data(boolean authorization) {

    }

    public boolean isAuthorized() {
        return Boolean.TRUE.equals(data.authorization);
    }
}
