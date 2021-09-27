package com.soge.katasoge.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Account {
    private long id;
    private String bban;
    private String iban;
    private String bic;
    private Country Country;
    private BigDecimal amount;
    private Currency currency;
    private TypeOfAccount typeOfAccount;
    private Client owner;
    private List<Operation> operations;

}
