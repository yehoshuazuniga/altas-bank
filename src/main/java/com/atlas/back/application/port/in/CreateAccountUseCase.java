package com.atlas.back.application.port.in;

import com.atlas.back.account.model.Account;

public interface CreateAccountUseCase {
    Account execute(Account account);

}
