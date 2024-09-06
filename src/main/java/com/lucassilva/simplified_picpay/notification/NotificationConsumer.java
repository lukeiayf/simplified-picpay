package com.lucassilva.simplified_picpay.notification;

import com.lucassilva.simplified_picpay.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.Objects;

@Service
public class NotificationConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    private final RestClient restClient;

    public NotificationConsumer(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v1/notify")
                .build();
    }

    @KafkaListener(topics = "transaction-notification", groupId = "simplified-picpay")
    public void receiveNotification(Transaction transaction) {
        LOGGER.info("Notifying transaction {}...", transaction);

        ResponseEntity<Notification> response = null;
        try {
            response = restClient.post()
                    .retrieve()
                    .toEntity(Notification.class);
        } catch (Exception e) {
            throw new NotificationException(e.getMessage());
        }

        if (response.getStatusCode().isError()) {
            throw new NotificationException("Error sending notification");
        }

        LOGGER.info("Notification has been sent");

    }
}
