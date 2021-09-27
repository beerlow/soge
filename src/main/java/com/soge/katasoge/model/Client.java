package com.soge.katasoge.model;

import lombok.Data;

@Data
public class Client {
    private long id;
    private String firstName;
    private String lastName;
    private boolean isWithdrawalAllowed;
    private boolean isDepositAllowed;
    private boolean isBalanceAllowed;
}
