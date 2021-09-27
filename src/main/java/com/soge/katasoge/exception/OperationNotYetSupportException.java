package com.soge.katasoge.exception;

import com.soge.katasoge.exception.code.ExceptionCode;

public class OperationNotYetSupportException  extends FunctionalException {
    @Override
    String getCode() {
        return ExceptionCode.OPERATION_NOT_YES_SUPPORTED;
    }
}
