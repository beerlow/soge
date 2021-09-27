package com.soge.katasoge.exception;

import com.soge.katasoge.exception.code.ExceptionCode;

public class AccountNotFoundException extends FunctionalException {

    @Override
    String getCode() {
        return ExceptionCode.ACCOUNT_NOT_FOUND;
    }

}
