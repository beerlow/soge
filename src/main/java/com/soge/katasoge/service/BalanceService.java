package com.soge.katasoge.service;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.exception.WrongAccountOwnerException;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Client;
import com.soge.katasoge.model.HistoricAccount;
import com.soge.katasoge.model.Operation;

import java.util.List;

public interface BalanceService {

      List<Account> getAllBalances(Long clientId) throws ClientNotFoundException;

      List<Operation> getBalance(Long clientId, Long account) throws ClientNotFoundException, AccountNotFoundException, WrongAccountOwnerException;
}
