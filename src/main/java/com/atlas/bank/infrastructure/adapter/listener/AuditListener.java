package com.atlas.bank.infrastructure.adapter.listener;

import com.atlas.bank.domain.event.AccountClosedEvent;
import com.atlas.bank.domain.event.TransactionExecuteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuditListener {

    @EventListener
    public void onTransacionalExecuted(TransactionExecuteEvent event) {
        log.info("Registrado auditoria -{} de cuenta #{} a cuenta #{} pot ${}",
                event.type(), event.sourceAccountId(), event.tragetAccountId(), event.amount());
    }

    @EventListener
    public void onAccountClosed(AccountClosedEvent event) {
        log.info("Registrada auditoria - cierre de cuenta #{} ({})",
                event.accountId(), event.accountNumber());
    }
}
