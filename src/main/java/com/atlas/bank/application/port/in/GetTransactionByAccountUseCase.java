package com.atlas.bank.application.port.in;

import com.atlas.bank.application.query.GetAccountStatementQuery;
import com.atlas.bank.application.query.TransactionReadModel;
import com.atlas.bank.domain.model.transaction.Transaction;

import java.util.List;

public interface GetTransactionByAccountUseCase {
    List<TransactionReadModel> getByAccountId(GetAccountStatementQuery query);

}
