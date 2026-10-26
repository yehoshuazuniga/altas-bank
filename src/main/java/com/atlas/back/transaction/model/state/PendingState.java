package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;
public record PendingState () implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.PENDING;
    }

    @Override
    public TransactionState validate() {
        return new ValidatedState();
    }

    @Override
    public TransactionState reject() {
        return new  RejectedState();
    }
}
