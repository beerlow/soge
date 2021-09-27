package com.soge.katasoge.service;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.Account;

public interface AccountService {
    Account verifyAccount(Long accountId) throws AccountNotFoundException;

    void checkAccountIsOwnedByClient(Long clientId, Account account) throws WrongAccountOwnerException;

    Account save(Account account);
}
