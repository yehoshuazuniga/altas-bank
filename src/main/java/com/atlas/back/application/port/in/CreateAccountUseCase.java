package com.atlas.back.application.port.in;

import com.atlas.back.domain.model.account.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);

}
