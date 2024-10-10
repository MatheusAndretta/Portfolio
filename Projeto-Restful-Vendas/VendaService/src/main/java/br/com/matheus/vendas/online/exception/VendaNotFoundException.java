package br.com.matheus.vendas.online.exception;


public class VendaNotFoundException extends RuntimeException {
    public VendaNotFoundException(String message) {
        super(message);
    }

    public VendaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
