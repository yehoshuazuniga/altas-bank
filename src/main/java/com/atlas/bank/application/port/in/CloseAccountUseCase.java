package com.atlas.bank.application.port.in;

import com.atlas.bank.application.command.CloseAccountCommand;
import com.atlas.bank.domain.model.account.Account;

public interface CloseAccountUseCase {
    Account close(CloseAccountCommand command);
}
