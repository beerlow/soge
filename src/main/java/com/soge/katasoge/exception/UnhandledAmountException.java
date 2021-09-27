package com.soge.katasoge.exception;

import com.soge.katasoge.exception.code.ExceptionCode;

public class UnhandledAmountException extends FunctionalException {
    @Override
    String getCode() {
        return ExceptionCode.UNHANDLED_AMOUNT_EXCEPTION;
    }
}
