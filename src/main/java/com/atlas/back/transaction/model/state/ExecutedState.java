package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;

public record ExecutedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.EXECUTED;
    }
}
