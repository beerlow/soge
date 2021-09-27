package com.soge.katasoge.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Operation {
    private long id;
    private Date initiationDate;
    private Date executedDate;
    private BigDecimal amount;
    private Currency currency;
    private Source source;
}
