package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;

public record ValidatedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.VALIDATED;
    }

    @Override
    public TransactionState reject() {
        return new  RejectedState();
    }

    @Override
    public TransactionState execute() {
        return new ExecutedState();
    }
}
