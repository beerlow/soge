package com.soge.katasoge.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HistoricAccount {
    private Account account;
    private List<Operation> operations;
}
