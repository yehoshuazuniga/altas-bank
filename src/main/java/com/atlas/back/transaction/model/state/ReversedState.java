package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;

public record ReversedState() implements TransactionState {
    @Override
    public TransactionStatus status() {
        return TransactionStatus.REVERSED;
    }
}
