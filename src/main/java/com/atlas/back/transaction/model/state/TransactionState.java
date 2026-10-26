package com.atlas.back.transaction.model.state;

import com.atlas.back.transaction.model.TransactionStatus;

public sealed interface TransactionState permits PendingState, ValidatedState,
        ExecutedState, RejectedState, ReversedState {
    TransactionStatus status();


    default TransactionState validate() {
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState execute() {
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState reject() {
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }

    default TransactionState reverse() {
        throw new IllegalStateException("No se puede ejecutar una transaccion en estado " + status());
    }



}
