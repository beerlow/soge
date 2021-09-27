package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.exception.OperationNotYetSupportException;
import com.soge.katasoge.exception.UnhandledAmountException;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Client;
import com.soge.katasoge.model.Operation;
import com.soge.katasoge.repository.AccountRepository;
import com.soge.katasoge.repository.OperationRepository;
import com.soge.katasoge.service.AccountService;
import com.soge.katasoge.service.ClientService;
import com.soge.katasoge.service.DepositService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Log4j2
public class DepositServiceImpl implements DepositService {

    private final ClientService clientService;
    private final AccountService accountService;
    private final OperationRepository operationRepository;

    public DepositServiceImpl(ClientService clientService, AccountRepository accountRepository, AccountService accountService, OperationRepository operationRepository) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public Account deposit(Long clientId, Long accountId, Operation operation) throws ClientNotFoundException, AccountNotFoundException, UnhandledAmountException, OperationNotYetSupportException {
        log.info("deposit for client: {}, account: {} and operation: {} ", clientId, accountId, operation);
        Client client = clientService.verifyClient(clientId);
        Account account = accountService.verifyAccount(accountId);
        if (operation.getAmount() == null || operation.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new UnhandledAmountException();
        }
        if(operation.getCurrency() != null && !operation.getCurrency().equals(account.getCurrency())){
            throw new OperationNotYetSupportException();
        }

        operation.setExecutedDate(new Date());
        operationRepository.save(operation);
        account.setAmount(account.getAmount().add(operation.getAmount()));
        account =   accountService.save(account);
        return account;
    }
}
