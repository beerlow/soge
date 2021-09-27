package com.soge.katasoge.service.impl;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.repository.AccountRepository;
import com.soge.katasoge.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account verifyAccount(Long accountId) throws AccountNotFoundException {
        if (accountId == null) {
            throw new AccountNotFoundException();
        }
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException());
    }

    @Override
    public void checkAccountIsOwnedByClient(Long clientId, Account account) throws WrongAccountOwnerException {
        if (clientId != account.getOwner().getId()) {
            throw new WrongAccountOwnerException();
        }
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

}
