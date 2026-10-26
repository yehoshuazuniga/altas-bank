package com.atlas.back.transaction.validation.chain;

import com.atlas.back.transaction.service.transfer.TransferContext;

public interface TransferValidation {
    void validate(TransferContext ctx);
}
