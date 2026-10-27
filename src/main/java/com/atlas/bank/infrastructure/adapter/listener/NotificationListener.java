package com.atlas.bank.infrastructure.adapter.listener;


import com.atlas.bank.domain.event.TransactionExecuteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @EventListener
    public void onTransactionExecute(TransactionExecuteEvent event) {
        log.info("Enviado comprobante de {} por ${} - Transaccion #{}",
        event.type(), event.amount(), event.transactionId());
    }

}
