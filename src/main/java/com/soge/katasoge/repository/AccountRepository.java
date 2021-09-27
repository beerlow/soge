package com.soge.katasoge.repository;

import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Operation;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    List<Account> getAccountsByClientId(long clientId);

     Optional<Account> findById(long accountId);

    Account save(Account account);

    List<Account> getAccountsByClientIdWithBalances(Long clientId);
}
