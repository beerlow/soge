package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.*;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Operation;
import com.soge.katasoge.repository.AccountRepository;
import com.soge.katasoge.repository.OperationRepository;
import com.soge.katasoge.service.AccountService;
import com.soge.katasoge.service.ClientService;
import com.soge.katasoge.service.WithdrawalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final ClientService clientService;
    private final OperationRepository operationRepository;

    public WithdrawalServiceImpl(AccountRepository accountRepository, AccountService accountService, ClientService clientService, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public Account withdraw(Long clientId, Long accountId, Operation operation) throws ClientNotFoundException, AccountNotFoundException, WrongAccountOwnerException, UnhandledAmountException, OperationNotYetSupportException {
        clientService.verifyClient(clientId);
        Account account = accountService.verifyAccount(accountId);
        accountService.checkAccountIsOwnedByClient(clientId, account);
        if (operation.getAmount() == null || operation.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new UnhandledAmountException();
        }
        if (operation.getCurrency() != null && !operation.getCurrency().equals(account.getCurrency())) {
            throw new OperationNotYetSupportException();
        }
        operation.setExecutedDate(new Date());
        operationRepository.save(operation);
        account.setAmount(account.getAmount().subtract(operation.getAmount()));
        account = accountService.save(account);

        return account;
    }
}
