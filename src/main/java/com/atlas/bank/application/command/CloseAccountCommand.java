package com.atlas.bank.application.command;

import lombok.Builder;

@Builder
public record CloseAccountCommand(
        Long accountId
) {
}
