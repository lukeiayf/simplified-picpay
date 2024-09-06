package com.lucassilva.simplified_picpay.authorization;

import com.lucassilva.simplified_picpay.transaction.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizerService {
    private final RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }

    public void authorize(Transaction transaction) {
        ResponseEntity<Authorization> response = restClient.get().retrieve().toEntity(Authorization.class);

        if (response.getStatusCode().isError() || !Objects.requireNonNull(response.getBody()).isAuthorized()) {
            throw new UnauthorizedTransactionException("Transaction not authorized");
        }
    }

}
