package com.atlas.back.account.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long id) {
        super("No se encontró la cuenta con ID: " + id);
    }
}

