package com.soge.katasoge.model;


import lombok.Data;

@Data
public class Currency {
    private long id;
    private String currencyCode;
    private String currencyLabel;
}
