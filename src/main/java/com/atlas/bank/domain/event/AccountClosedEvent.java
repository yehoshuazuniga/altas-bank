package com.atlas.bank.domain.event;

public record AccountClosedEvent(
        Long accountId,
        String accountNumber
) {
}
