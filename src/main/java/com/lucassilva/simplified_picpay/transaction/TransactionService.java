package com.lucassilva.simplified_picpay.transaction;

import com.lucassilva.simplified_picpay.authorization.AuthorizerService;
import com.lucassilva.simplified_picpay.notification.NotificationService;
import com.lucassilva.simplified_picpay.wallet.Wallet;
import com.lucassilva.simplified_picpay.wallet.WalletRepository;
import com.lucassilva.simplified_picpay.wallet.WalletType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final AuthorizerService authorizerService;
    private final NotificationService notificationService;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository, AuthorizerService authorizerService, NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizerService = authorizerService;
        this.notificationService = notificationService;
    }

    @Transactional
    public Transaction create(Transaction transaction) {
        //validate
        validate(transaction);
        //create
        Transaction newTransaction = transactionRepository.save(transaction);
        //debit from wallet
        Wallet walletPayer = walletRepository.findById(transaction.payer())
                .orElseThrow(() -> new NoSuchElementException("Wallet not found for payer: " + transaction.payer()));
        Wallet walletPayee = walletRepository.findById(transaction.payee())
                .orElseThrow(() -> new NoSuchElementException("Wallet not found for payer: " + transaction.payee()));
        walletRepository.save(walletPayer.debit(transaction.value()));
        walletRepository.save(walletPayee.credit(transaction.value()));
        //call external services
        //authorize
        authorizerService.authorize(transaction);
        //notify
        notificationService.notify(transaction);

        return newTransaction;
    }

    private void validate(Transaction transaction) {
        //validation rules:
        //payer has a common wallet
        //payer has enough balance
        //payer is not the payee

        walletRepository.findById(transaction.payee())
                .map(payee -> walletRepository.findById(transaction.payer())
                        .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
                        .orElseThrow(() -> new InvalidTransactionException("Not a valid operation - " + transaction)))
                .orElseThrow(() -> new InvalidTransactionException("Not a valid operation - " + transaction));
    }

    private static boolean isTransactionValid(Transaction transaction, Wallet payer) {
        return payer.type() == WalletType.COMMON.getValue() &&
                payer.balance().compareTo(transaction.value()) >= 0 &&
                !payer.id().equals(transaction.payee());
    }

    public List<Transaction> list() {
        return transactionRepository.findAll();
    }
}
