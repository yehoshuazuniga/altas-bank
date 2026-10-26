package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;

public record RejectedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REJECTED;
    }
}
