package com.soge.katasoge.repository;

import com.soge.katasoge.model.Operation;

import java.util.List;

public interface OperationRepository {
    List<Operation> getOperationsByAccountId( Long accountId);

    Operation save(Operation operation);
}
