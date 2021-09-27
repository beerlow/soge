package com.soge.katasoge.service;

import com.soge.katasoge.exception.*;
import com.soge.katasoge.model.Account;
import com.soge.katasoge.model.Operation;

public interface WithdrawalService {

    Account withdraw(Long clientId, Long accountId, Operation operation) throws ClientNotFoundException, AccountNotFoundException, WrongAccountOwnerException, UnhandledAmountException, OperationNotYetSupportException;
}
