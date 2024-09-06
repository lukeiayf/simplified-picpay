package com.lucassilva.simplified_picpay.authorization;

import com.lucassilva.simplified_picpay.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizerService.class);
    private final RestClient restClient;

    public AuthorizerService(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("https://util.devi.tools/api/v2/authorize")
                .build();
    }

    public void authorize(Transaction transaction) {
        LOGGER.info("Authorizing transaction: {}", transaction);
        ResponseEntity<Authorization> response = null;
        try {
            response = restClient.get().retrieve().toEntity(Authorization.class);
        } catch (Exception e) {
            throw new UnauthorizedTransactionException(e.getMessage());
        }

        if (response.getStatusCode().isError() || !Objects.requireNonNull(response.getBody()).isAuthorized() || HttpStatus.FORBIDDEN.equals(response.getStatusCode())) {
            throw new UnauthorizedTransactionException("Transaction not authorized");
        }

        LOGGER.info("Transaction authorized:{}", response.getBody());
    }

}
