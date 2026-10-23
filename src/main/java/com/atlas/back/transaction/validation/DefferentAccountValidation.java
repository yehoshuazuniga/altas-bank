package com.atlas.back.transaction.validation;

import com.atlas.back.transaction.dto.TransferRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DefferentAccountValidation implements ConstraintValidator<DifferentsAccounts, TransferRequest> {


    @Override
    public boolean isValid(TransferRequest transferRequest, ConstraintValidatorContext constrainsValidationContext) {

        if(transferRequest.getFromAccountId() == null | transferRequest.getToAccountId() == null) {
            return true;
        }

        return !transferRequest.getFromAccountId().equals(transferRequest.getToAccountId());
    }
}
