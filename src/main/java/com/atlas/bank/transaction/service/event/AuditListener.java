package com.atlas.bank.transaction.service.event;

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
}
