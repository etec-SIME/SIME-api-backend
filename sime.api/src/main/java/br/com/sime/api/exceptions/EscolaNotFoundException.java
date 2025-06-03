package br.com.sime.api.exceptions;

public class EscolaNotFoundException extends RuntimeException {
    public EscolaNotFoundException(String message) {
        super(message);
    }
}
