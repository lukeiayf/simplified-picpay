package com.lucassilva.simplified_picpay.notification;

public record Notification(
        String status,
        Data data
) {
    public record Data(String message) {

    }
}
