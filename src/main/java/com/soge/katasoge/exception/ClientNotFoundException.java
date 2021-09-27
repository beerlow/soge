package com.soge.katasoge.exception;

import com.soge.katasoge.exception.code.ExceptionCode;

public class ClientNotFoundException extends FunctionalException{
    @Override
    String getCode() {
        return ExceptionCode.USER_NOT_FOUND;
    }
}
