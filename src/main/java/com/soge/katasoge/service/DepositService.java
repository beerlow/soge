package com.soge.katasoge.service;

import com.soge.katasoge.exception.AccountNotFoundException;
import com.soge.katasoge.exception.ClientNotFoundException;
import com.soge.katasoge.exception.OperationNotYetSupportException;
import com.soge.katasoge.exception.UnhandledAmountException;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Operation;

public interface DepositService {

    Account deposit(Long clientId, Long accountId, Operation operation) throws ClientNotFoundException, AccountNotFoundException, UnhandledAmountException, OperationNotYetSupportException;
}
