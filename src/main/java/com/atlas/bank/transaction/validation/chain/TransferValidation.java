package com.atlas.bank.transaction.validation.chain;

import com.atlas.bank.transaction.service.transfer.TransferContext;

public interface TransferValidation {
    void validate(TransferContext ctx);
}
