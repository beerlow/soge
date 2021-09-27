package com.soge.katasoge.exception;

import com.soge.katasoge.exception.code.ExceptionCode;

public class WrongAccountOwnerException extends FunctionalException {
    @Override
    String getCode() {
        return ExceptionCode.WRONG_ACCOUNT_OWNER;
    }
}
