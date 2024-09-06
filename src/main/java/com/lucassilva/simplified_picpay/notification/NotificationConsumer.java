package com.lucassilva.simplified_picpay.notification;

import com.lucassilva.simplified_picpay.transaction.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class NotificationConsumer {

    private final RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v1/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "simplified-picpay")
    public void receiveNotification(Transaction transaction) {
        ResponseEntity<Notification> response = restClient.get()
                .retrieve()
                .toEntity(Notification.class);

        if (response.getStatusCode().isError() || Objects.equals(Objects.requireNonNull(response.getBody()).status(), "fail")) {
            throw new NotificationException("Error sending notification");
        }
    }
}
