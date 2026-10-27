package com.atlas.bank.domain.validation;

import com.atlas.bank.domain.model.transaction.TransferContext;

public interface TransferValidation {
    void validate(TransferContext ctx);
}
