package com.alejandronieves.service;

/** La reserva no se puede hacer; el mensaje se muestra tal cual al usuario. */
public class ReservaNoValidaException extends RuntimeException {

    public ReservaNoValidaException(String mensaje) {
        super(mensaje);
    }
}
