package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.HistoricAccount;
import com.soge.katasoge.model.Operation;
import com.soge.katasoge.repository.AccountRepository;
import com.soge.katasoge.repository.OperationRepository;
import com.soge.katasoge.service.AccountService;
import com.soge.katasoge.service.BalanceService;
import com.soge.katasoge.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class BalanceServiceImpl implements BalanceService {

    private final AccountRepository accountRepository;
    private final ClientService clientService;
    private final OperationRepository operationRepository;
    private final AccountService accountService;

    BalanceServiceImpl(AccountRepository accountRepository, ClientService clientService, OperationRepository operationRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
        this.operationRepository = operationRepository;
        this.accountService = accountService;
    }
    @Override
    public List<Operation> getBalance(Long clientId, Long accountId) throws ClientNotFoundException, AccountNotFoundException, WrongAccountOwnerException {
        log.info("get balance for account: {} and client: {}", accountId, clientId);
        clientService.verifyClient(clientId);
        Account account = accountService.verifyAccount(accountId);
        accountService.checkAccountIsOwnedByClient(clientId, account);
        return getOperationsByAccountId(accountId);
    }

    @Override
    public List<Account> getAllBalances(Long clientId) throws ClientNotFoundException {
        log.info("get All balances for client: {}", clientId);
        clientService.verifyClient(clientId);
        List<Account> accounts = accountRepository.getAccountsByClientIdWithBalances(clientId);

        return accounts;
    }

    private List<Operation> getOperationsByAccountId(Long accountId) {
        return operationRepository.getOperationsByAccountId(accountId);
    }




}
